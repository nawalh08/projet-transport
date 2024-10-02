package org.example.controllers;

import org.example.DtoS.AdminDTO;
import org.example.DtoS.SubscriberDTO;
import org.example.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {
    @Autowired
    private SubscriberService subscriberService;

    @PostMapping
    public ResponseEntity<SubscriberDTO> createSubscriber(@RequestBody SubscriberDTO subscriberDTO) {
        // Logique de création d'un abonné
        SubscriberDTO subscriber = subscriberService.createSubscriber(subscriberDTO);
        return ResponseEntity.ok(subscriber);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriberDTO> getSubscriber(@PathVariable Long id) {
        // Logique de récupération d'un abonné
        SubscriberDTO subscriber = subscriberService.getSubscriber(id);
        return ResponseEntity.ok(subscriber);
    }

    @GetMapping
    public ResponseEntity<List<SubscriberDTO>> getAllSubscribers() {
        List<SubscriberDTO> subscribers = subscriberService.getAllSubscribers();
        return ResponseEntity.ok(subscribers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriberDTO> updateSubscriber(@PathVariable Long id, @RequestBody SubscriberDTO subscriberDTO) {
        // Logique de mise à jour d'un abonné
        SubscriberDTO updatedSubscriber = subscriberService.updateSubscriber(id, subscriberDTO);
        return ResponseEntity.ok(updatedSubscriber);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriber(@PathVariable Long id) {
        // Logique de suppression d'un abonné
        subscriberService.deleteSubscriber(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody AdminDTO adminDTO) {
        // Logique de connexion

       subscriberService.login("test@gmail.com", adminDTO.getPassword());
       return ResponseEntity.ok(true);
    }

    @PostMapping("/{id}/subscribe")
    public ResponseEntity<Void> subscribe(@PathVariable Long id, @RequestBody List<Long> lineIds) {
        // Logique d'abonnement
        subscriberService.subscribe(id, lineIds);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/unsubscribe/{lineId}")
    public ResponseEntity<Boolean> unsubscribe(@PathVariable Long id, @PathVariable Long lineId) {
        // Logique de désabonnement
        return null;
    }
}
