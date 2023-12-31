package sg.nus.iss.day23workshop2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private Integer id;
    private String title;
    private String synopsis;
    private Integer availableCount;

}
