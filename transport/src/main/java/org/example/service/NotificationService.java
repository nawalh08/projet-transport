package org.example.service;

import org.example.DtoS.NotificationDTO;
import org.example.entite.Admin;
import org.example.entite.Notification;
import org.example.entite.Subscriber;
import org.example.entite.TransportLine;
import org.example.repository.AdminRepository;
import org.example.repository.NotificationRepository;
import org.example.repository.SubscriberRepository;
import org.example.repository.TransportLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private TransportLineRepository transportLineRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private SubscriberRepository subscriberRepository;


    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        // Logique de création d'une notification
        Notification notification = new Notification();
        notification.setDescription(notificationDTO.getDescription());
        notification.setCreatedAt(new Date());
        notification.setExpiredAt(notificationDTO.getExpiredAt());
        notification.setActive(true);

        TransportLine transportLine = transportLineRepository.findById(notificationDTO.getTransportLineId())
                .orElseThrow(() -> new ExpressionException("TransportLine not found with id: " + notificationDTO.getTransportLineId()));
        notification.setTransportLine(transportLine);

        Admin author = adminRepository.findById(notificationDTO.getAuthorId())
                .orElseThrow(() -> new ExpressionException("Admin not found with id: " + notificationDTO.getAuthorId()));
        notification.setAuthor(author);

        Notification savedNotification = notificationRepository.save(notification);
        return convertToDTO(savedNotification);


    }

    public NotificationDTO getNotification(Long id) {
        // Logique de récupération d'une notification
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Notification not found with id: " + id));
        return convertToDTO(notification);
    }

    public List<NotificationDTO> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<NotificationDTO> getNotificationsByTransportLine(Long transportLineId) {
        TransportLine transportLine = transportLineRepository.findById(transportLineId)
                .orElseThrow(() -> new ExpressionException("TransportLine not found with id: " + transportLineId));
        List<Notification> notifications = notificationRepository.findByTransportLine(transportLine);
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public NotificationDTO updateNotification(Long id, NotificationDTO notificationDTO) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Notification not found with id: " + id));

        notification.setDescription(notificationDTO.getDescription());
        notification.setExpiredAt(notificationDTO.getExpiredAt());

        if (notificationDTO.getTransportLineId() != null) {
            TransportLine transportLine = transportLineRepository.findById(notificationDTO.getTransportLineId())
                    .orElseThrow(() -> new ExpressionException("TransportLine not found with id: " + notificationDTO.getTransportLineId()));
            notification.setTransportLine(transportLine);
        }

        Notification updatedNotification = notificationRepository.save(notification);
        return convertToDTO(updatedNotification);
    }

    public void deleteNotification(Long id) {
        // Logique de suppression d'une notification
            if (!notificationRepository.existsById(id)) {
                throw new ExpressionException("Notification not found with id: " + id);
            }
            notificationRepository.deleteById(id);

    }

    public void sendNotification(Long id) {
        // Logique d'envoi de notification
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Notification not found with id: " + id));

        List<Subscriber> subscribers = subscriberRepository.findBySubscribedLines(notification.getTransportLine());

        // Logic to send notification to subscribers (e.g., email, push notification)
        for (Subscriber subscriber : subscribers) {
            // Send notification to subscriber
            System.out.println("Sending notification to: " + subscriber.getEmail());
        }
    }

    public void toggleNotificationActive(Long id) {
        // Logique de basculement de l'état actif d'une notification
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Notification not found with id: " + id));

        notification.setActive(!notification.isActive());
        notificationRepository.save(notification);
    }

    public List<NotificationDTO> getActiveNotifications() {
        // Logique de récupération des notifications actives
        List<Notification> notifications = notificationRepository.findAll();
        return null;
    }


    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setDescription(notification.getDescription());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setExpiredAt(notification.getExpiredAt());
        dto.setActive(notification.isActive());
        dto.setTransportLineName(notification.getTransportLine().getName());
        dto.setAuthorName(notification.getAuthor().getFirstName() + " " + notification.getAuthor().getLastName());
        return dto;
    }
}
