package org.example.repository;

import org.example.entite.TransportLine;
import org.example.entite.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportLineRepository extends JpaRepository<TransportLine, Long> {
    List<TransportLine> findByTransportType(TransportType transportType);
}
