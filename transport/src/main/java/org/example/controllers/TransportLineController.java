package org.example.controllers;

import org.example.DtoS.ScheduleDTO;
import org.example.DtoS.TransportLineDTO;
import org.example.service.TransportLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transportLines")
public class TransportLineController {
    @Autowired
    private TransportLineService transportLineService;

    // ... autres méthodes ...

    @GetMapping("/{id}")
    public ResponseEntity<TransportLineDTO> getTransportLine(@PathVariable Long id) {
        TransportLineDTO transportLine = transportLineService.getTransportLine(id);
        return ResponseEntity.ok(transportLine);
    }

    @GetMapping
    public ResponseEntity<List<TransportLineDTO>> getAllTransportLines() {
        List<TransportLineDTO> transportLines = transportLineService.getAllTransportLines();
        return ResponseEntity.ok(transportLines);
    }

    @GetMapping("/{id}/schedules")
    public ResponseEntity<List<ScheduleDTO>> getSchedules(@PathVariable Long id) {
        List<ScheduleDTO> schedules = transportLineService.getSchedules(id);
        return ResponseEntity.ok(schedules);
    }


}
