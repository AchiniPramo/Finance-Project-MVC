package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.Micro_Finance_Management_System.model.Customer;
import lk.ijse.Micro_Finance_Management_System.model.Expense;
import lk.ijse.Micro_Finance_Management_System.repo.CustomerRepository;
import lk.ijse.Micro_Finance_Management_System.repo.EmployeeRepository;
import lk.ijse.Micro_Finance_Management_System.repo.ExpenseRepository;
import lk.ijse.Micro_Finance_Management_System.util.Regex;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ExpenseManageFormController {

    @FXML
    private ComboBox<String> cmbEmployeeId;

    @FXML
    private ComboBox<Integer> cmbExpenseId;

    @FXML
    private DatePicker dtpDate;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtType;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int expenseId = cmbExpenseId.getValue();
        var model = new ExpenseRepository();

        try{
            var expenseRepository = new ExpenseRepository();
            Expense expense = model.search(expenseId);
            if(expense != null) {
                boolean isDeleted = expenseRepository.deleteExpense(expenseId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Expense Delete Successfully!").show();
                    clearFields();
                    ExpenseFormController.getInstance().initialize();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Expense Not Found!").show();
                clearFields();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String type = String.valueOf(txtType.getText());
        Date date = Date.valueOf(dtpDate.getValue());
        String employeeId = cmbEmployeeId.getValue();
        double amount = Double.parseDouble(txtAmount.getText());

        if (isValid()){
        var expense = new Expense(type, date, employeeId, amount);
        var model = new ExpenseRepository();

        try {
            boolean isSaved = model.saveExpense(expense);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Expense Saved Successfully!").show();
                clearFields();
                ExpenseFormController.getInstance().initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    } else{
        new Alert(Alert.AlertType.ERROR, "Invalid input Detected. Please check!").show();
    }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        int expenseId = cmbExpenseId.getValue();
        var model = new ExpenseRepository();

        try {
            Expense expense = model.search(expenseId);

            if (expense != null) {
                fillFields(expense);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Expense Not Found!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String type = String.valueOf(txtType.getText());
        Date date = Date.valueOf(dtpDate.getValue());
        String employeeId = cmbEmployeeId.getValue();
        double amount = Double.parseDouble(txtAmount.getText());

        if (isValid()){
        var expense = new Expense(type, date, employeeId, amount);
        var model = new ExpenseRepository();

        try {
            boolean isUpdated = model.updateExpense(expense);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Expense Update Successfully!").show();
                clearFields();
                ExpenseFormController.getInstance().initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        } else{
            new Alert(Alert.AlertType.ERROR, "Invalid input Detected. Please check!").show();
        }
    }

    private void clearFields() {
        cmbExpenseId.getSelectionModel().clearSelection();
        txtType.setText("");
        dtpDate.setValue(null);
        cmbEmployeeId.getSelectionModel().clearSelection();
        txtAmount.setText("");
    }

    private void fillFields(Expense expense) {
        cmbEmployeeId.setValue(expense.getEmployeeId());
        cmbExpenseId.setValue(expense.getExpenseId());
        txtType.setText(expense.getType());
        txtAmount.setText(String.valueOf(expense.getAmount()));
        dtpDate.setValue(expense.getDate().toLocalDate());
    }

    public void initialize() {
        var employeeRepository = new EmployeeRepository();
        var expenseRepository = new ExpenseRepository();

        try {
            List<String> employeeIds = employeeRepository.getAllEmployeeIds();
            cmbEmployeeId.getItems().addAll(employeeIds);

            List<Integer> expenseIds = expenseRepository.getAllExpenseIds();
            cmbExpenseId.getItems().addAll(expenseIds);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void txtAmountOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Salary,txtAmount);

    }

    @FXML
    void txtTypeOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name,txtType);

    }

    @FXML
    void txtDateOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Date,dtpDate.getEditor());

    }
    public boolean isValid(){
        boolean isTypeValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name, txtType);
        boolean isAmountValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Salary, txtAmount);
        boolean isDateValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Date, dtpDate.getEditor());

        return isTypeValid && isAmountValid && isDateValid;
    }
}
