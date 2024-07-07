package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.Micro_Finance_Management_System.model.tm.ExpenseTm;
import lk.ijse.Micro_Finance_Management_System.repo.ExpenseRepository;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ExpenseFormController {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colExpenseId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableView<ExpenseTm> tblExpenses;

    private static ExpenseFormController controller;
    public ExpenseFormController(){

        controller=this;
    }
    public static ExpenseFormController getInstance(){
        return controller;
    }

    public void initialize() {
        setCellValueFactory();
        loadAllExpenses();
    }

    private void loadAllExpenses() {
        var model = new ExpenseRepository();

        ObservableList<ExpenseTm> tmList = FXCollections.observableArrayList();

        try {
            List<ExpenseTm> expenseList = model.getAllExpense();

            for (ExpenseTm expense : expenseList) {
                tmList.add(new ExpenseTm(
                        expense.getExpenseId(),
                        expense.getType(),
                        expense.getDate(),
                        expense.getEmployeeId(),
                        expense.getAmount()
                ));
            }
            tblExpenses.setItems(tmList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colExpenseId.setCellValueFactory(new PropertyValueFactory<>("expenseId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

    }

    public void btnManageExpenseOnAction(ActionEvent event) {
        Navigation.navigateToManageExpensePage();
    }

    private static void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Navigation Error");
        alert.showAndWait();
    }
}
