package org.example.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Date createdAt;
    private Date expiredAt;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "transport_line_id")
    private TransportLine transportLine;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Admin author;

    // Getters, setters, et autres méthodes

    public void sendNotification() {
        // Logique d'envoi de notification
    }

    public void toggleIsActive() {
        // Logique de basculement de l'état actif
    }
}
