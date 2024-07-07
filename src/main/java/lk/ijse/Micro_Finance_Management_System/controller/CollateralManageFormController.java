package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.Micro_Finance_Management_System.model.Collateral;
import lk.ijse.Micro_Finance_Management_System.model.Customer;
import lk.ijse.Micro_Finance_Management_System.repo.CollateralRepository;
import lk.ijse.Micro_Finance_Management_System.repo.CustomerRepository;
import lk.ijse.Micro_Finance_Management_System.repo.LoanRepository;
import lk.ijse.Micro_Finance_Management_System.util.Regex;

import java.sql.SQLException;
import java.util.List;

public class CollateralManageFormController {

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCollateralId;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String collateralId = txtCollateralId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String phoneNumber = txtContact.getText();

        if (isValid()) {

            var collateral = new Collateral(collateralId, name, address, phoneNumber);
            var model = new CollateralRepository();

            try {
                boolean isSaved = model.saveCollateral(collateral);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Collateral Saved Successfully!").show();
                    clearFields();
                    CollateralFormController.getInstance().initialize();
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
        String collateralId = txtCollateralId.getText();
        var model = new CollateralRepository();

        try {
            Collateral collateral = model.search(collateralId);

            if (collateral != null) {
                fillFields(collateral);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Collateral Not Found!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String collateralId = txtCollateralId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String phoneNumber = txtContact.getText();

        if (isValid()) {

        var collateral = new Collateral(collateralId, name, address, phoneNumber);
        var model = new CollateralRepository();

        try {
            boolean isUpdated = model.updateCollateral(collateral);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Collateral Update Successfully!").show();
                clearFields();
                CollateralFormController.getInstance().initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input Detected. Please check!").show();
        }
    }

    private void clearFields() {
        txtCollateralId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
    }

    private void fillFields(Collateral collateral) {
        txtCollateralId.setText(String.valueOf(collateral.getCollateralId()));
        txtName.setText(String.valueOf(collateral.getName()));
        txtAddress.setText(String.valueOf(collateral.getAddress()));
        txtContact.setText(String.valueOf(collateral.getPhoneNumber()));
    }

    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Address,txtAddress);
    }

    @FXML
    void txtCollateralIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Id,txtCollateralId);
    }

    @FXML
    void txtCollateralNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name,txtName);
    }

    @FXML
    void txtContactNoOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Contact,txtContact);
    }

    public boolean isValid() {
        boolean isIdValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Id, txtCollateralId);
        boolean isNameValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name, txtName);
        boolean isAddressValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Address, txtAddress);
        boolean isContactValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Contact, txtContact);
        return isIdValid && isNameValid && isContactValid && isAddressValid;
    }
}

