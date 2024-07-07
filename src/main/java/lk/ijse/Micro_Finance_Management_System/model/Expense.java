package lk.ijse.Micro_Finance_Management_System.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Expense {
    private int expenseId;
    private String type;
    private Date date;
    private String employeeId;
    private double amount;

    public Expense(String type, Date date, String employeeId, double amount) {
        this.type = type;
        this.date = date;
        this.employeeId = employeeId;
        this.amount = amount;
    }
}
