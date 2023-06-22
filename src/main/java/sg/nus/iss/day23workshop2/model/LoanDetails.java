package sg.nus.iss.day23workshop2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDetails {
    private int id;
    private int loanId;
    private int videoId;
    
}
