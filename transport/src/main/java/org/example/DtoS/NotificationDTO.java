package org.example.DtoS;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private String description;
    private Date createdAt;
    private Date expiredAt;
    private boolean isActive;
    private String transportLineName;
    private String authorName;
    private Long authorId;
    private Long transportLineId;

}
