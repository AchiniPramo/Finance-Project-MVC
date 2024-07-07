package lk.ijse.Micro_Finance_Management_System.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Loan {
    private int loanId;
    private String description;
    private double amount;
    private String duration;
    private double interestRate;

    public Loan(String description, double amount, String duration, double interestRate) {
        this.description = description;
        this.amount = amount;
        this.duration = duration;
        this.interestRate = interestRate;
    }
}
