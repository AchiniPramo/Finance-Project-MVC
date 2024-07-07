package lk.ijse.Micro_Finance_Management_System.util;

import javafx.scene.control.Alert;
import lk.ijse.Micro_Finance_Management_System.db.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionUtil {
    private static Connection connection;

    static {
        try {
            connection = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public static void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public static void endTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public static void rollBack() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }
}
