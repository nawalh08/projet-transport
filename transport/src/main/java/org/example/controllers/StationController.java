package org.example.controllers;


import org.example.DtoS.StationDTO;
import org.example.DtoS.TransportLineDTO;
import org.example.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class StationController {
    @Autowired
    private StationService stationService;

    // ... autres méthodes ...

    @GetMapping("/{id}")
    public ResponseEntity<StationDTO> getStation(@PathVariable Long id) {
        StationDTO station = stationService.getStation(id);
        return ResponseEntity.ok(station);
    }

    @GetMapping
    public ResponseEntity<List<StationDTO>> getAllStations() {
        List<StationDTO> stations = stationService.getAllStations();
        return ResponseEntity.ok(stations);
    }

    @GetMapping("/{id}/lines")
    public ResponseEntity<List<TransportLineDTO>> getLines(@PathVariable Long id) {
        List<TransportLineDTO> lines = stationService.getLines(id);
        return ResponseEntity.ok(lines);
    }
}
