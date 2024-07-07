package lk.ijse.Micro_Finance_Management_System.repo;

import lk.ijse.Micro_Finance_Management_System.model.tm.PenaltyTm;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PenaltyRepository {
    public List<PenaltyTm> getAllPenalty() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM Penalty");
        List<PenaltyTm> penaltyList = new ArrayList<>();

        while (resultSet.next()) {
            penaltyList.add(new PenaltyTm(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDate(5).toLocalDate()
            ));
        }
        return penaltyList;
    }

}

