package lk.ijse.Micro_Finance_Management_System.repo;

import lk.ijse.Micro_Finance_Management_System.model.Employee;
import lk.ijse.Micro_Finance_Management_System.model.tm.EmployeeTm;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    public static int getEmployeeCount() throws SQLException {
        //return to the count of the employee
        ResultSet resultSet = SQLUtil.sql("SELECT COUNT(*) FROM Employee");
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    public static List<EmployeeTm> getAllEmployee() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM Employee");
        List<EmployeeTm> employeeList = new ArrayList<>();

        while (resultSet.next()) {
            employeeList.add(new EmployeeTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return employeeList;
    }

    public static Employee search(String employeeId) throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT  Employee_Id, Name, Address, Salary, Phone_Number, Email FROM Employee WHERE Employee_Id = ?", employeeId);

        if (resultSet.next()) {
            return new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return null;
    }

    public boolean deleteEmployee(String employeeId) throws SQLException {
        return SQLUtil.sql("DELETE FROM Employee WHERE Employee_Id = ?", employeeId);
    }

    public boolean saveEmployee(Employee employee) throws SQLException {
        return SQLUtil.sql("INSERT INTO Employee (Employee_Id, Name, Address, Salary, Phone_Number, Email) VALUES (?,?,?,?,?,?)", employee.getEmployeeId(), employee.getName(), employee.getAddress(), employee.getSalary(), employee.getPhoneNumber(), employee.getEmail());
    }

    public boolean updateEmployee(Employee employee) throws SQLException {
        return SQLUtil.sql("UPDATE Employee SET Name = ?, Address = ?, Salary = ?, Phone_Number = ?, Email = ? WHERE Employee_Id = ?", employee.getName(), employee.getAddress(), employee.getSalary(), employee.getPhoneNumber(), employee.getEmail(), employee.getEmployeeId());
    }

    public List<String> getAllEmployeeIds() throws SQLException {
        List<String> employeeIds = new ArrayList<>();

        try (ResultSet resultSet = SQLUtil.sql("SELECT Employee_Id FROM Employee")) {
            while (resultSet.next()) {
                String employeeId = resultSet.getString("Employee_Id");
                employeeIds.add(employeeId);
            }
        }
        return employeeIds;
    }
}

