package lk.ijse.Micro_Finance_Management_System.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private String customerId;
    private String name;
    private String address;
    private String email;
    private String mobileNo;

}
