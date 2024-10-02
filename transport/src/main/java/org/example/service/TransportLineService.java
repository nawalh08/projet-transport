package org.example.service;

import org.example.DtoS.ScheduleDTO;
import org.example.DtoS.TransportLineDTO;
import org.example.entite.Schedule;
import org.example.entite.TransportLine;
import org.example.entite.TransportType;
import org.example.repository.ScheduleRepository;
import org.example.repository.TransportLineRepository;
import org.example.repository.TransportTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransportLineService {
    @Autowired
    private TransportLineRepository transportLineRepository;
    @Autowired
    private TransportTypeRepository transportTypeRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;


    public TransportLineDTO createTransportLine(TransportLineDTO transportLineDTO) {
        // Logique de création d'une ligne de transport
        TransportLine transportLine = new TransportLine();
        transportLine.setName(transportLineDTO.getName());
        transportLine.setOrigine(transportLineDTO.getOrigine());
        transportLine.setDestination(transportLineDTO.getDestination());

        TransportType transportType = transportTypeRepository.findByType(transportLineDTO.getTransportType())
                .orElseThrow(() -> new ExpressionException("TransportType not found with type: " + transportLineDTO.getTransportType()));
        transportLine.setTransportType(transportType);

        TransportLine savedTransportLine = transportLineRepository.save(transportLine);
        return convertToDTO(savedTransportLine);
    }

    public TransportLineDTO getTransportLine(Long id) {
        // Logique de récupération d'une ligne de transport
        TransportLine transportLine = transportLineRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("TransportLine not found with id: " + id));
        return convertToDTO(transportLine);
    }

    public List<TransportLineDTO> getAllTransportLines() {
        List<TransportLine> transportLines = transportLineRepository.findAll();
        return transportLines.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TransportLineDTO updateTransportLine(Long id, TransportLineDTO transportLineDTO) {
        // Logique de mise à jour d'une ligne de transport
        TransportLine transportLine = transportLineRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("TransportLine not found with id: " + id));

        transportLine.setName(transportLineDTO.getName());
        transportLine.setOrigine(transportLineDTO.getOrigine());
        transportLine.setDestination(transportLineDTO.getDestination());

        if (transportLineDTO.getTransportType() != null) {
            TransportType transportType = transportTypeRepository.findByType(transportLineDTO.getTransportType())
                    .orElseThrow(() -> new ExpressionException("TransportType not found with type: " + transportLineDTO.getTransportType()));
            transportLine.setTransportType(transportType);
        }

        TransportLine updatedTransportLine = transportLineRepository.save(transportLine);
        return convertToDTO(updatedTransportLine);
    }

    public void deleteTransportLine(Long id) {
        // Logique de suppression d'une ligne de transport
        if (!transportLineRepository.existsById(id)) {
            throw new ExpressionException("TransportLine not found with id: " + id);
        }
        transportLineRepository.deleteById(id);
    }

    public List<ScheduleDTO> getSchedules(Long id) {
        TransportLine transportLine = transportLineRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("TransportLine not found with id: " + id));
        List<Schedule> schedules = scheduleRepository.findByLine(transportLine);
        return schedules.stream()
                .map(this::convertToScheduleDTO)
                .collect(Collectors.toList());
    }
    private TransportLineDTO convertToDTO(TransportLine transportLine) {
        TransportLineDTO dto = new TransportLineDTO();
        dto.setId(transportLine.getId());
        dto.setName(transportLine.getName());
        dto.setTransportType(transportLine.getTransportType().getType());
        dto.setOrigine(transportLine.getOrigine());
        dto.setDestination(transportLine.getDestination());
        return dto;
    }

    private ScheduleDTO convertToScheduleDTO(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(schedule.getId());
        dto.setDay(schedule.getDay());
        dto.setTransportLineName(schedule.getLine().getName());
        dto.setDepartureTime(schedule.getDepartureTime());
        dto.setFrequency(schedule.getFrequency());
        return dto;
    }
}
