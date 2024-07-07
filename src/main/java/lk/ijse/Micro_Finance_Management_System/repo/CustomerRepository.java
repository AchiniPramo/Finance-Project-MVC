package lk.ijse.Micro_Finance_Management_System.repo;

import lk.ijse.Micro_Finance_Management_System.model.Customer;
import lk.ijse.Micro_Finance_Management_System.model.tm.CustomerTm;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    public static int getCustomerCount() throws SQLException {
        //return to the count of the customers
        ResultSet resultSet = SQLUtil.sql("SELECT COUNT(*) FROM Customer");
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }
    public static List<CustomerTm> getAllCustomer() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT Customer_Id, Name, Address, Email, Phone_Number FROM Customer");
        List<CustomerTm> customerList = new ArrayList<>();

        while (resultSet.next()) {
            customerList.add(new CustomerTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            ));
        }
        return customerList;
    }

    public Customer search(String customerId) throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT  Customer_Id, Name, Address, Email, Phone_Number FROM Customer WHERE Customer_Id = ?", customerId);

        if (resultSet.next()) {
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

    public boolean saveCustomer(Customer customer) throws SQLException {
        return SQLUtil.sql("INSERT INTO Customer (Customer_Id, Name, Address, Email, Phone_Number) VALUES (?,?,?,?,?)", customer.getCustomerId(), customer.getName(), customer.getAddress(), customer.getEmail(), customer.getMobileNo());

    }

    public boolean deleteCustomer(String customerId) throws SQLException {
        return SQLUtil.sql("DELETE FROM Customer WHERE Customer_Id = ?", customerId);
    }

    public boolean updateCustomer(Customer customer) throws SQLException {
        return SQLUtil.sql("UPDATE Customer SET Name = ?, Address = ?, Email = ?, Phone_Number = ? WHERE Customer_Id = ?", customer.getName(), customer.getAddress(), customer.getEmail(),customer.getMobileNo(), customer.getCustomerId());
    }

    public List<String> getAllCustomerIds() throws SQLException {
        List<String> customerIds = new ArrayList<>();

        try (ResultSet resultSet = SQLUtil.sql("SELECT Customer_ID FROM Customer")) {
            while (resultSet.next()) {
                String customerId = resultSet.getString("Customer_ID");
                customerIds.add(customerId);
            }
        }
        return customerIds;
    }
}
