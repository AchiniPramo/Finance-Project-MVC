package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Micro_Finance_Management_System.model.tm.PaymentTm;
import lk.ijse.Micro_Finance_Management_System.repo.PaymentRepository;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;

import java.sql.SQLException;
import java.util.List;

public class PaymentFormController {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colLoanId;

    @FXML
    private TableColumn<?, ?> colPaymentDate;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableView<PaymentTm> tblPayment;

    private static PaymentFormController controller;
    public PaymentFormController(){

        controller=this;
    }
    public static PaymentFormController getInstance(){

        return controller;
    }

    @FXML
    void btnManagePaymentOnAction(ActionEvent event) {
        Navigation.navigateToManagePaymentPage();
    }

    public void initialize() {
        setCellValueFactory();
        loadAllPayment();
    }

    private void loadAllPayment() {
        var model = new PaymentRepository();

        ObservableList<PaymentTm> tmList = FXCollections.observableArrayList();

        try {
            List<PaymentTm> paymentList = model.getAllPayment();

            for (PaymentTm payment : paymentList) {
                tmList.add(new PaymentTm(
                        payment.getPaymentId(),
                        payment.getPaymentDate(),
                        payment.getLoanId(),
                        payment.getAmount()

                ));
            }
            tblPayment.setItems(tmList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colLoanId.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }
}
