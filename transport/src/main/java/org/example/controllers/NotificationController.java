package org.example.controllers;


import org.example.DtoS.NotificationDTO;
import org.example.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {
        // Logique de création d'une notification
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable Long id) {
        // Logique de récupération d'une notification
        NotificationDTO notification = notificationService.getNotification(id);
        return ResponseEntity.ok(notification);
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/transportLine/{transportLineId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByTransportLine(@PathVariable Long transportLineId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByTransportLine(transportLineId);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(@PathVariable Long id){
        NotificationDTO notificationDTO = notificationService.getNotification(id);
        notificationDTO.setId(id);
        return null;
    }
}
