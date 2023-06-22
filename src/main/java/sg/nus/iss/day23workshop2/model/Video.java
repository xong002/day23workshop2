package sg.nus.iss.day23workshop2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private int id;
    private String title;
    private String synopsis;
    private int availableCount;

}
