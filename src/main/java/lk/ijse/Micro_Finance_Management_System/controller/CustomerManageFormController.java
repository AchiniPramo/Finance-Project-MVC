package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.Micro_Finance_Management_System.model.Customer;
import lk.ijse.Micro_Finance_Management_System.repo.CustomerRepository;
import lk.ijse.Micro_Finance_Management_System.util.Regex;

import javax.mail.Address;
import java.sql.SQLException;

public class CustomerManageFormController {

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();
        var model = new CustomerRepository();

        try{
            var customerRepository = new CustomerRepository();
            Customer customer = model.search(customerId);
            if(customer != null) {
                boolean isDeleted = customerRepository.deleteCustomer(customerId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Delete Successfully!").show();
                    clearFields();
                    CustomerFormController.getInstance().initialize();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found!").show();
                clearFields();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String phoneNumber = txtContactNo.getText();

        if (isValid()) {

        var customer = new Customer(customerId, name, address, email, phoneNumber);
        var model = new CustomerRepository();

        try {
            boolean isSaved = model.saveCustomer(customer);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved Successfully!").show();
                clearFields();
                CustomerFormController.getInstance().initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input Detected. Please check!").show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();
        var model = new CustomerRepository();

        try {
            Customer customer = model.search(customerId);

            if (customer != null) {
                fillFields(customer);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Not Found!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String mobileNo = txtContactNo.getText();

        if(isValid()) {

            var customer = new Customer(customerId, name, address, email, mobileNo);
            var model = new CustomerRepository();

            try {
                boolean isUpdated = model.updateCustomer(customer);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Update Successfully!").show();
                    clearFields();
                    CustomerFormController.getInstance().initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            } else{
                new Alert(Alert.AlertType.ERROR, "Invalid input Detected. Please check!").show();
            }
    }

    private void fillFields(Customer customer) {
        txtCustomerId.setText(customer.getCustomerId());
        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        txtEmail.setText(customer.getEmail());
        txtContactNo.setText(customer.getMobileNo());
    }

    private void clearFields() {
        txtCustomerId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtContactNo.setText("");
    }
    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Address,txtAddress);
    }

    @FXML
    void txtContactNoONKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Contact,txtContactNo);
    }

    @FXML
    void txtCustomerIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Id,txtCustomerId);
    }

    @FXML
    void txtCustomerNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name,txtName);
    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Email,txtEmail);
    }
    public boolean isValid() {
        boolean isIdValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Id, txtCustomerId);
        boolean isNameValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name, txtName);
        boolean isAddressValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Address,txtAddress);
        boolean isEmailValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Email, txtEmail);
        boolean isContactValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Contact, txtContactNo);

        return isIdValid && isNameValid && isEmailValid && isContactValid && isAddressValid;
    }
}

