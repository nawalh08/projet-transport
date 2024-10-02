package org.example.repository;

import org.example.entite.Notification;
import org.example.entite.TransportLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByTransportLine(TransportLine transportLine);
    List<Notification> findByIsActiveTrue();
}
