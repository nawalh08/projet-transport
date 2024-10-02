package org.example.repository;

import org.example.entite.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransportTypeRepository extends JpaRepository<TransportType, Long> {
    Optional<TransportType> findByType(String type);
}
