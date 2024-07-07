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
import lk.ijse.Micro_Finance_Management_System.model.tm.PenaltyTm;
import lk.ijse.Micro_Finance_Management_System.repo.PenaltyRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PenaltyFormController {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDateApplied;

    @FXML
    private TableColumn<?, ?> colLoanId;

    @FXML
    private TableColumn<?, ?> colPenaltyId;

    @FXML
    private TableColumn<?, ?> colReason;

    @FXML
    private TableView<PenaltyTm> tblPenalty;


    public void initialize() {
        loadAllPenalty();
        setCellValueFactory();
    }

    private void loadAllPenalty() {
        var model = new PenaltyRepository();

        ObservableList<PenaltyTm> tmList = FXCollections.observableArrayList();

        try {
            List<PenaltyTm> penaltyList = model.getAllPenalty();

            for (PenaltyTm penalty : penaltyList) {
                tmList.add(new PenaltyTm(
                        penalty.getPenaltyId(),
                        penalty.getReason(),
                        penalty.getLoanId(),
                        penalty.getAmount(),
                        penalty.getDateApplied()
                ));
            }
            tblPenalty.setItems(tmList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colPenaltyId.setCellValueFactory(new PropertyValueFactory<>("penaltyId"));
        colReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        colLoanId.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDateApplied.setCellValueFactory(new PropertyValueFactory<>("dateApplied"));

    }

    private static void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Navigation Error");
        alert.showAndWait();
    }

}
