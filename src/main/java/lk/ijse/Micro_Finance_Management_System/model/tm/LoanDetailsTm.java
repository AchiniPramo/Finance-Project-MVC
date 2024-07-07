package lk.ijse.Micro_Finance_Management_System.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanDetailsTm {
    private String customerId;
    private String customerName;
    private int loanId;
    private double amount;
    private String collateralName;
    private Date issue;
    private Date due;
    private String status;
    private double totalDue;
}
