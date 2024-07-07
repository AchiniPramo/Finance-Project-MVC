package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.Micro_Finance_Management_System.model.tm.LoanTm;
import lk.ijse.Micro_Finance_Management_System.repo.LoanRepository;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoanFormController {
    @FXML
    private Pane bodyPane;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colInterestRate;

    @FXML
    private TableColumn<?, ?> colLoanId;

    @FXML
    private TableView<LoanTm> tblLoan;

    private static LoanFormController controller;
    public LoanFormController(){
        controller=this;
    }
    public static LoanFormController getInstance(){
        return controller;
    }

    @FXML
    void btnPlaceLoanFormOnAction(ActionEvent event) throws SQLException {
        Navigation.navigateToPlaceLoanPage();
    }

    public void btnViewPenaltyOnAction(ActionEvent event) {
        bodyPane.getChildren().clear();
        try {
            bodyPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/penalty_form.fxml")));
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    public void initialize() {
        setCellValueFactory();
        loadAllLoan();
    }

    private void loadAllLoan() {
        var model = new LoanRepository();

        ObservableList<LoanTm> tmList = FXCollections.observableArrayList();

        try {
            List<LoanTm> loanList = model.getAllLoan();

            for (LoanTm loan : loanList) {
                tmList.add(new LoanTm(
                        loan.getLoanId(),
                        loan.getDescription(),
                        loan.getAmount(),
                        loan.getDuration(),
                        loan.getInterestRate()

                ));
            }
            tblLoan.setItems(tmList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colLoanId.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colInterestRate.setCellValueFactory(new PropertyValueFactory<>("interestRate"));
    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Navigation Error");
        alert.showAndWait();
    }
}
