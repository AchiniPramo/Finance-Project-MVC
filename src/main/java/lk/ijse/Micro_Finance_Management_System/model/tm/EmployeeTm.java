package lk.ijse.Micro_Finance_Management_System.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeTm {
    private String employeeId;
    private String name;
    private String address;
    private double salary;
    private String mobileNo;
    private String email;

}
