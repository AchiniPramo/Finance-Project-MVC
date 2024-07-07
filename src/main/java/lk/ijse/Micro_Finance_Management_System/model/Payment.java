package lk.ijse.Micro_Finance_Management_System.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {
    private int paymentId;
    private Date paymentDate;
    private int loanId;
    private double amount;

    public Payment(Date date, int loanId, double amount) {
        this.paymentDate = date;
        this.loanId = loanId;
        this.amount = amount;
    }
}
