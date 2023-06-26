package sg.nus.iss.day23workshop2.model;

import java.sql.Date;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    public int id;
    public int customerId;

    @FutureOrPresent(message = "Must be present or future date")
    public Date loanDate;

    @Future(message = "Must be future date")
    public Date returnDate;

}
