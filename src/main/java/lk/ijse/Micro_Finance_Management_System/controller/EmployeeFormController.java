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
import lk.ijse.Micro_Finance_Management_System.model.tm.EmployeeTm;
import lk.ijse.Micro_Finance_Management_System.repo.EmployeeRepository;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {
    @FXML
    private Pane bodyPane;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colMobileNo;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    private static EmployeeFormController controller;
    public EmployeeFormController(){
        controller=this;
    }
    public static EmployeeFormController getInstance(){
        return controller;
    }

    @FXML
    void btnEmployeeManageOnAction(ActionEvent event) {
        Navigation.navigateToManageEmployeePage();
    }

    @FXML
    void btnExpenseOnAction(ActionEvent event) {
        bodyPane.getChildren().clear();
        try {
            bodyPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/expense_form.fxml")));
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    public void initialize() {
        setCellValueFactory();
        loadAllEmployees();
    }

    public void loadAllEmployees() {
        var model = new EmployeeRepository();

        ObservableList<EmployeeTm> tmList = FXCollections.observableArrayList();

        try {
            List<EmployeeTm> employeeList = model.getAllEmployee();

            for (EmployeeTm employee : employeeList) {
                tmList.add(new EmployeeTm(
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getAddress(),
                        employee.getSalary(),
                        employee.getMobileNo(),
                        employee.getEmail()

                ));
            }
            tblEmployee.setItems(tmList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colMobileNo.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Navigation Error");
        alert.showAndWait();
    }
}
