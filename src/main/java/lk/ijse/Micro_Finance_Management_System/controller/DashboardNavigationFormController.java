package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashboardNavigationFormController {

    @FXML
    private Pane bodyPane;

    @FXML
    private Button btnDashboard;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private void initialize() {
        updateTime();
        startClock();
        loadDefaultView();
    }

    private void updateTime() {
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();

        lblTime.setText(time.format(DateTimeFormatter.ofPattern("hh:mm a")));
        lblDate.setText(date.format(DateTimeFormatter.ofPattern("EEEE(dd)-MMMM-yyyy")));
    }

    private void startClock() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateTime();
            }
        };
        timer.start();
    }

    private void loadDefaultView() {
        bodyPane.getChildren().clear();
        try {
            bodyPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/main_board_form.fxml")));
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    @FXML
    void btnCollateralOnAction(ActionEvent event) {
        bodyPane.getChildren().clear();
        try {
            bodyPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/collateral_form.fxml")));
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        bodyPane.getChildren().clear();
        try {
            bodyPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml")));
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        loadDefaultView();
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {
        bodyPane.getChildren().clear();
        try {
            bodyPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml")));
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    @FXML
    void btnFinancialOnAction(ActionEvent event) {
        bodyPane.getChildren().clear();
        try {
            bodyPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/financial_form.fxml")));
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    @FXML
    void btnLoanOnAction(ActionEvent event) {
        bodyPane.getChildren().clear();
        try {
            bodyPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/loan_form.fxml")));
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        bodyPane.getChildren().clear();
        try {
            bodyPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/payment_form.fxml")));
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    @FXML
    void btnSignOutOnAction(ActionEvent event) {
        Navigation.navigateToLoginForm();
    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Navigation Error");
        alert.showAndWait();
    }
}
