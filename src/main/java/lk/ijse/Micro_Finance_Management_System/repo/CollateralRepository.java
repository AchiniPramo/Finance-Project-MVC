package lk.ijse.Micro_Finance_Management_System.repo;

import lk.ijse.Micro_Finance_Management_System.model.Collateral;
import lk.ijse.Micro_Finance_Management_System.model.Employee;
import lk.ijse.Micro_Finance_Management_System.model.tm.CollateralTm;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static lk.ijse.Micro_Finance_Management_System.util.SQLUtil.sql;

public class CollateralRepository {
    public List<CollateralTm> getAllCollateral() throws SQLException {
        ResultSet resultSet = sql("SELECT * FROM Collateral");
        List<CollateralTm> collateralList = new ArrayList<>();

        while (resultSet.next()) {
            collateralList.add(new CollateralTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return collateralList;
    }

    public boolean saveCollateral(Collateral collateral) throws SQLException {
        return sql("INSERT INTO Collateral (Collateral_Id, Name, Address, Phone_Number) VALUES (?,?,?,?)", collateral.getCollateralId(), collateral.getName(), collateral.getAddress(), collateral.getPhoneNumber());
    }

    public Collateral search(String collateralId) throws SQLException {

        ResultSet resultSet = sql("SELECT  Collateral_Id, Name, Address, Phone_Number FROM Collateral WHERE Collateral_Id = ?", collateralId);

        if (resultSet.next()) {
            return new Collateral(
                    resultSet.getString("Collateral_Id"),
                    resultSet.getString("Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Phone_Number")
            );
        }
        return null;
    }

    public boolean updateCollateral(Collateral collateral) throws SQLException {
        return sql("UPDATE Collateral SET Name = ?, Address = ?, Phone_Number = ? WHERE Collateral_Id = ?", collateral.getName(), collateral.getAddress(), collateral.getPhoneNumber(), collateral.getCollateralId());
    }

    public boolean updateCollateralLoan(String collateralId, int loanId) throws SQLException {
        return SQLUtil.sql("INSERT INTO Collateral_Loan (Collateral_Id, Loan_Id) SELECT ?, ? FROM DUAL WHERE EXISTS (SELECT 1 FROM Loan WHERE Loan_Id = ?)", collateralId, loanId, loanId);
    }
}

