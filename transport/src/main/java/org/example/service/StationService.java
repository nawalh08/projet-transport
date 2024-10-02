package org.example.service;

import org.example.DtoS.StationDTO;
import org.example.DtoS.TransportLineDTO;
import org.example.entite.Station;
import org.example.entite.TransportLine;
import org.example.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private TransportLineStationRepository transportLineStationRepository;

    public StationDTO createStation(StationDTO stationDTO) {
        // Logique de création d'une station
        Station station = new Station();
        station.setName(stationDTO.getName());
        station.setScheduleTime(stationDTO.getScheduleTime());

        Station savedStation = stationRepository.save(station);
        return convertToDTO(savedStation);
    }

    public StationDTO getStation(Long id) {
        // Logique de récupération d'une station
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Station not found with id: " + id));
        return convertToDTO(station);
    }

    public List<StationDTO> getAllStations() {
        List<Station> stations = stationRepository.findAll();
        return stations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StationDTO updateStation(Long id, StationDTO stationDTO) {
        // Logique de mise à jour d'une station
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Station not found with id: " + id));

        station.setName(stationDTO.getName());
        station.setScheduleTime(stationDTO.getScheduleTime());

        Station updatedStation = stationRepository.save(station);
        return convertToDTO(updatedStation);
    }

    public void deleteStation(Long id) {
        // Logique de suppression d'une station
        if (!stationRepository.existsById(id)) {
            throw new ExpressionException("Station not found with id: " + id);
        }
        stationRepository.deleteById(id);
    }

    public List<TransportLineDTO> getLines(Long id) {
        // Logique de récupération des lignes passant par une station
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Station not found with id: " + id));
        return station.getLines().stream()
                .map(tls -> tls.getTransportLine())
                .map(this::convertToTransportLineDTO)
                .collect(Collectors.toList());
    }

    public void showInMap(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Station not found with id: " + id));

        // Logic to show the station on a map (e.g., return coordinates)
        System.out.println("Showing station on map: " + station.getName());
    }

    private StationDTO convertToDTO(Station station) {
        StationDTO dto = new StationDTO();
        dto.setId(station.getId());
        dto.setName(station.getName());
        dto.setScheduleTime(station.getScheduleTime());
        dto.setLineNames(station.getLines().stream()
                .map(tls -> tls.getTransportLine().getName())
                .collect(Collectors.toList()));
        return dto;
    }

    private TransportLineDTO convertToTransportLineDTO(TransportLine transportLine) {
        TransportLineDTO dto = new TransportLineDTO();
        dto.setId(transportLine.getId());
        dto.setName(transportLine.getName());
        dto.setTransportType(transportLine.getTransportType().getType());
        dto.setOrigine(transportLine.getOrigine());
        dto.setDestination(transportLine.getDestination());
        return dto;
    }
}
