package org.example.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransportLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "transport_type_id")
    private TransportType transportType;

    @OneToMany(mappedBy = "transportLine")
    private List<Schedule> schedules;

    private String origine;
    private String destination;

    @OneToMany(mappedBy = "transportLine")
    private List<TransportLineStation> stations;


}
