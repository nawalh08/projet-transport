package org.example.DtoS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportLineDTO {
    private Long id;
    private String name;
    private String transportType;
    private String origine;
    private String destination;
}
