package lk.ijse.Micro_Finance_Management_System.util;



import lk.ijse.Micro_Finance_Management_System.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {
    public static <T> T sql(String sql, Object... args) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            pstm.setObject((i+1), args[i]);
        }

        if (sql.startsWith("SELECT") || sql.startsWith("select") || sql.startsWith("Select")) {
            return (T) pstm.executeQuery();
        } else {
            return (T) (Boolean) (pstm.executeUpdate() > 0);
        }
    }
}
