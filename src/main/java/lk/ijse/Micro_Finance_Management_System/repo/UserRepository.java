package lk.ijse.Micro_Finance_Management_System.repo;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.Micro_Finance_Management_System.db.DbConnection;
import lk.ijse.Micro_Finance_Management_System.model.User;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserRepository {
    public static void checkCredentialAndLogin (User user) throws SQLException, IOException {
        String userName = user.getUserName();
        String password = user.getPassword();
        String sql = "SELECT Name, Password FROM User WHERE Name = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm  = connection.prepareStatement(sql);
        pstm.setObject(1, userName);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String dbPw = resultSet.getString(2);

            if(dbPw.equals(password)) {
                Navigation.navigateToDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "Password is incorrect!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "User Name not found!").show();
        }
    }

    public static void resetPassword(User user, TextField txtNewPassword) throws SQLException {
        String userId = user.getUserId();
        String userName = user.getUserName();
        String newPassword = txtNewPassword.getText();

        // Update the user's password in the database
        String updateSql = "UPDATE User SET Password = ? WHERE Name = ? AND User_ID = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement updateStatement = connection.prepareStatement(updateSql);
        updateStatement.setString(1, newPassword);
        updateStatement.setString(2, userName);
        updateStatement.setString(3, userId);
        int rowsAffected = updateStatement.executeUpdate();

        if (rowsAffected > 0) {
            // Password updated successfully, notify the user
            new Alert(Alert.AlertType.INFORMATION, "Password reset successfully.").show();
            txtNewPassword.clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to reset password. User not found.").show();
        }
    }
}
