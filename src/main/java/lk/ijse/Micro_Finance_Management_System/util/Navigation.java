package lk.ijse.Micro_Finance_Management_System.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lk.ijse.Micro_Finance_Management_System.controller.EmployeeManageFormController;
import lombok.Setter;

import java.io.IOException;

public class Navigation {

    @Setter
    private static Stage primaryStage;

    public static void navigateToPasswordResetForm() {
        try {
            AnchorPane rootNode = FXMLLoader.load(Navigation.class.getResource("/view/resetpassword_form.fxml"));
            Scene scene = new Scene(rootNode);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Password Reset Form");
            primaryStage.show();
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    public static void navigateToDashboard() {
        try {
            // Load both FXML files into their respective Parent objects
            Parent mainFormNode = FXMLLoader.load(Navigation.class.getResource("/view/dashboard_form.fxml"));

            // Create a BorderPane to hold both forms
            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(mainFormNode); // Set mainform at the center or choose another position

            // Set up the scene with the borderPane containing both nodes
            Scene scene = new Scene(borderPane);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Dashboard Form");
            primaryStage.show();
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    public static void navigateToLoginForm() {
        try {
            AnchorPane rootNode = FXMLLoader.load(Navigation.class.getResource("/view/login_form.fxml"));
            Scene scene = new Scene(rootNode);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Page");
            primaryStage.show();
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    public static void navigateToManageCustomerPage() {
        try {
            Parent rootNode = FXMLLoader.load(Navigation.class.getResource("/view/customer_manage_form.fxml"));
            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Manage Customer Form");
            stage.show();
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    public static void navigateToManageCollateral() {
        try {
            Parent rootNode = FXMLLoader.load(Navigation.class.getResource("/view/collateral_manage_form.fxml"));
            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Manage Collateral Form");
            stage.show();
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    public static void navigateToManageEmployeePage() {
        try {
            Parent rootNode = FXMLLoader.load(Navigation.class.getResource("/view/employee_manage_form.fxml"));
            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Manage Employee Form");
            stage.show();
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    public static void navigateToManageExpensePage() {
        try {
            Parent rootNode = FXMLLoader.load(Navigation.class.getResource("/view/expense_manage_form.fxml"));
            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Manage Expense Form");
            stage.show();
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    public static void navigateToPlaceLoanPage() {
        try {
            Parent rootNode = FXMLLoader.load(Navigation.class.getResource("/view/loan_approval_form.fxml"));
            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Loan Approval Form");
            stage.show();
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    public static void navigateToManagePaymentPage() {
        try {
            Parent rootNode = FXMLLoader.load(Navigation.class.getResource("/view/payment_manage_form.fxml"));
            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Manage Payment Form");
            stage.show();
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    private static void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR,"Navigation Error");
        alert.showAndWait();
    }
}
