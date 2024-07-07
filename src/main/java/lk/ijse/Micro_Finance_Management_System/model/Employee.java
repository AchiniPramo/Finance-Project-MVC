package lk.ijse.Micro_Finance_Management_System.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {
    private String employeeId;
    private String name;
    private String address;
    private double salary;
    private String phoneNumber;
    private String email;
}
