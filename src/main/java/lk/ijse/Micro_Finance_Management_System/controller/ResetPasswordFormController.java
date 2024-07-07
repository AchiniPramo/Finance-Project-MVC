package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.Micro_Finance_Management_System.model.User;
import lk.ijse.Micro_Finance_Management_System.repo.UserRepository;
import lk.ijse.Micro_Finance_Management_System.service.EmailService;
import lk.ijse.Micro_Finance_Management_System.util.GenarateCode;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;

import java.sql.SQLException;

public class ResetPasswordFormController {

    @FXML
    private Button btnReset;

    @FXML
    private TextField txtOTPCode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUsername;

    private String otpCode;

    @FXML
    void btnGetOTPOnAction(ActionEvent event) {
        // Generate the verification code
        otpCode = GenarateCode.genarateCode();

        boolean emailSent = EmailService.sendCodeByEmail(otpCode);

        if (emailSent) {
            new Alert(Alert.AlertType.INFORMATION, "Verification code sent successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to send verification code. Please try again.").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        String enteredCode = txtOTPCode.getText();

        if (enteredCode.equals(otpCode)) {
            try {

                User user = new User();
                user.setUserId(txtUserId.getText());
                user.setUserName(txtUsername.getText());

                UserRepository.resetPassword(user, txtPassword);

                txtUserId.clear();
                txtUsername.clear();
                txtPassword.clear();
                txtOTPCode.clear();

                new Alert(Alert.AlertType.INFORMATION, "Password reset successful.").show();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "OOPS! Something went wrong").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid verification code.").show();
        }
    }

    @FXML
    void linkGoBackLoginOnAction(ActionEvent event) {
        Navigation.navigateToLoginForm();
    }

}
