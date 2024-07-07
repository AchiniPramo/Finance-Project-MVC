package lk.ijse.Micro_Finance_Management_System.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanTm {
    private int loanId;
    private String description;
    private double amount;
    private String duration;
    private double interestRate;
}
