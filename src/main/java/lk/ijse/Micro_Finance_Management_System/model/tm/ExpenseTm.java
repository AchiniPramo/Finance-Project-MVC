package lk.ijse.Micro_Finance_Management_System.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseTm {
    private int expenseId;
    private String type;
    private Date date;
    private String employeeId;
    private double amount;
}
