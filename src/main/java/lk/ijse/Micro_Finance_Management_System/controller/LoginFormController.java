package lk.ijse.Micro_Finance_Management_System.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Micro_Finance_Management_System.model.User;
import lk.ijse.Micro_Finance_Management_System.repo.UserRepository;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        try {
            User user = new User();
            user.setUserName(txtUsername.getText());
            user.setPassword(txtPassword.getText());

            UserRepository.checkCredentialAndLogin(user);
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR, "OOPS! Something went wrong").show();
        }
    }

    @FXML
    void linkResetOnAction(ActionEvent event) {
        Navigation.navigateToPasswordResetForm();
    }
}
