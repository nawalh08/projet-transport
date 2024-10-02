package org.example.service;

import org.example.DtoS.SubscriberDTO;
import org.example.entite.Subscriber;
import org.example.entite.TransportLine;
import org.example.repository.SubscriberRepository;
import org.example.repository.TransportLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriberService {
    @Autowired
    private SubscriberRepository subscriberRepository;
    @Autowired
    private TransportLineRepository transportLineRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public SubscriberDTO createSubscriber(SubscriberDTO subscriberDTO) {
        // Logique de création d'un abonné
        Subscriber subscriber = new Subscriber();
        subscriber.setFirstName(subscriberDTO.getFirstName());
        subscriber.setLastName(subscriberDTO.getLastName());
        subscriber.setEmail(subscriberDTO.getEmail());
        subscriber.setPassword(passwordEncoder.encode(subscriberDTO.getPassword()));

        Subscriber savedSubscriber = subscriberRepository.save(subscriber);
        return convertToDTO(savedSubscriber);
    }

    public SubscriberDTO getSubscriber(Long id) {
        Subscriber subscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Subscriber not found with id: " + id));
        return convertToDTO(subscriber);
    }

    public SubscriberDTO updateSubscriber(Long id, SubscriberDTO subscriberDTO) {
        Subscriber subscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Subscriber not found with id: " + id));

        subscriber.setFirstName(subscriberDTO.getFirstName());
        subscriber.setLastName(subscriberDTO.getLastName());
        subscriber.setEmail(subscriberDTO.getEmail());
        if (subscriberDTO.getPassword() != null) {
            subscriber.setPassword(passwordEncoder.encode(subscriberDTO.getPassword()));
        }

        Subscriber updatedSubscriber = subscriberRepository.save(subscriber);
        return convertToDTO(updatedSubscriber);
    }


    public void deleteSubscriber(Long id) {
        if (!subscriberRepository.existsById(id)) {
            throw new ExpressionException("Subscriber not found with id: " + id);
        }
        subscriberRepository.deleteById(id);
    }

    public boolean login(String email, String password) {
        Subscriber subscriber = subscriberRepository.findByEmail(email)
                .orElseThrow(() -> new ExpressionException("Subscriber not found with email: " + email));
        return passwordEncoder.matches(password, subscriber.getPassword());
    }

    public void subscribe(Long subscriberId, List<Long> lineIds) {
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new ExpressionException("Subscriber not found with id: " + subscriberId));

        List<TransportLine> lines = transportLineRepository.findAllById(lineIds);
        subscriber.getSubscribedLines().addAll(lines);

        subscriberRepository.save(subscriber);
    }

    public boolean unsubscribe(Long subscriberId, Long lineId) {
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new ExpressionException("Subscriber not found with id: " + subscriberId));

        TransportLine line = transportLineRepository.findById(lineId)
                .orElseThrow(() -> new ExpressionException("TransportLine not found with id: " + lineId));

        boolean removed = subscriber.getSubscribedLines().remove(line);
        if (removed) {
            subscriberRepository.save(subscriber);
        }
        return removed;
    public List<SubscriberDTO> getAllSubscribers() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        return subscribers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    private SubscriberDTO convertToDTO(Subscriber subscriber) {
        SubscriberDTO dto = new SubscriberDTO();
        dto.setId(subscriber.getId());
        dto.setFirstName(subscriber.getFirstName());
        dto.setLastName(subscriber.getLastName());
        dto.setEmail(subscriber.getEmail());
        return dto;
}
