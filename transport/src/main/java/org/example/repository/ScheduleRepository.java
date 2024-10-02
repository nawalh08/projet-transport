package org.example.repository;

import org.example.entite.Schedule;
import org.example.entite.TransportLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByLine(TransportLine line);
}
