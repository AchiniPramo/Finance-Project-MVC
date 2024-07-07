package lk.ijse.Micro_Finance_Management_System.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PenaltyTm {
    private int penaltyId;
    private String reason;
    private int loanId;
    private double amount;
    private LocalDate dateApplied;
}
