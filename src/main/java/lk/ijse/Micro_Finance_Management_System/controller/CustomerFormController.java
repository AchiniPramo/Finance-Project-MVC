package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Micro_Finance_Management_System.model.tm.CustomerTm;
import lk.ijse.Micro_Finance_Management_System.repo.CustomerRepository;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;

import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colMobileNo;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<CustomerTm> tblCustomer;
    private static CustomerFormController controller;
    public CustomerFormController(){

        controller=this;
    }
    public static CustomerFormController getInstance(){

        return controller;
    }

    @FXML
    void btnCustomerManageOnAction(ActionEvent event) {
        Navigation.navigateToManageCustomerPage();
    }

    public void initialize() {
        setCellValueFactory();
        loadAllCustomer();
    }

    private void loadAllCustomer() {
        var model = new CustomerRepository();

        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();

        try {
            List<CustomerTm> customerList = model.getAllCustomer();

            for (CustomerTm customer : customerList) {
                tmList.add(new CustomerTm(
                        customer.getCustomerId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getEmail(),
                        customer.getMobileNo()

                ));
            }
            tblCustomer.setItems(tmList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMobileNo.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));
    }
}
