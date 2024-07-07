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
import lk.ijse.Micro_Finance_Management_System.model.tm.LoanDetailsTm;
import lk.ijse.Micro_Finance_Management_System.repo.LoanRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FinancialFormController {

    @FXML
    private Pane bodyPane;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colCollateral;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDueDate;

    @FXML
    private TableColumn<?, ?> colIssueDate;

    @FXML
    private TableColumn<?, ?> colLoanId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTotalDue;

    @FXML
    private TableView<LoanDetailsTm> tblLoanDetail;

    @FXML
    void btnPlaceLoanOnAction(ActionEvent event) {
        bodyPane.getChildren().clear();
        try {
            bodyPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/loan_form.fxml")));
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Navigation Error");
        alert.showAndWait();
    }

    public void initialize() {
        setCellValueFactory();
        loadAllDetails();
    }

    private void loadAllDetails() {
        var model = new LoanRepository();

        ObservableList<LoanDetailsTm> tmList = FXCollections.observableArrayList();

        try {
            List<LoanDetailsTm> detailsList = model.getAllDetails();

            for (LoanDetailsTm detail : detailsList) {
                tmList.add(new LoanDetailsTm(
                        detail.getCustomerId(),
                        detail.getCustomerName(),
                        detail.getLoanId(),
                        detail.getAmount(),
                        detail.getCollateralName(),
                        detail.getIssue(),
                        detail.getDue(),
                        detail.getStatus(),
                        detail.getTotalDue()
                ));
            }
            tblLoanDetail.setItems(tmList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new  PropertyValueFactory<>("customerName"));
        colLoanId.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colCollateral.setCellValueFactory(new PropertyValueFactory<>("collateralName"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("issue"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("due"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTotalDue.setCellValueFactory(new PropertyValueFactory<>("totalDue"));
    }
}
