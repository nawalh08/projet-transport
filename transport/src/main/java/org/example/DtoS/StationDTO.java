package org.example.DtoS;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StationDTO {
    private Long id;
    private String name;
    private Date scheduleTime;
    private List<String> lineNames;

}
