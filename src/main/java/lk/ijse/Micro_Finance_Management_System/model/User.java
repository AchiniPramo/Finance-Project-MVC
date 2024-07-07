package lk.ijse.Micro_Finance_Management_System.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private String userId;

    private String userName;

    private String password;
}