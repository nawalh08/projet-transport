package org.example.repository;

import org.example.entite.Subscriber;
import org.example.entite.TransportLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findByEmail(String email);

    List<Subscriber> findBySubscribedLines(TransportLine transportLine);
}
