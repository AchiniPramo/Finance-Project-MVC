package lk.ijse.Micro_Finance_Management_System.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentTm {
    private int paymentId;
    private Date paymentDate;
    private int loanId;
    private double amount;
}
