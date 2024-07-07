package lk.ijse.Micro_Finance_Management_System.repo;

import javafx.scene.control.Alert;
import lk.ijse.Micro_Finance_Management_System.model.Loan;
import lk.ijse.Micro_Finance_Management_System.model.Penalty;
import lk.ijse.Micro_Finance_Management_System.model.tm.LoanDetailsTm;
import lk.ijse.Micro_Finance_Management_System.model.tm.LoanTm;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;
import lk.ijse.Micro_Finance_Management_System.util.TransactionUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository {
    public static int getLoanCount() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT COUNT(*) FROM Loan");
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    public boolean placeLoan(Loan loan, String customerId) throws SQLException {
        try {
            TransactionUtil.startTransaction();

            boolean isLoanInserted = insertLoan(loan);
            if (!isLoanInserted) {
                TransactionUtil.rollBack();
                return false;
            }

            int loanId = getInsertedLoanId();

            double totalAmountToPay = calculateTotalAmountToPay(loan.getAmount(), loan.getInterestRate(), Integer.parseInt(loan.getDuration()));

            boolean isOverdue = checkIfOverdue(loanId);

            if (isOverdue) {
                //double penaltyAmount = calculatePenaltyAmount(totalAmountToPay, loanId);
                //addPenaltyIfOverdue(loanId, penaltyAmount);
                //totalAmountToPay += penaltyAmount;
            }

            LocalDate currentDate = LocalDate.now();
            LocalDate dueDate = currentDate.plusDays(Integer.parseInt(loan.getDuration()));
            String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedDueDate = dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


            // Insert Customer_Loan table
            boolean isCustomerLoanInserted = insertCustomerLoan(loanId, customerId, formattedCurrentDate, formattedDueDate, totalAmountToPay);
            if (isCustomerLoanInserted) {
                TransactionUtil.endTransaction();
                return true;
            } else {
                TransactionUtil.rollBack();
                return false;
            }
        } catch (SQLException e) {
            TransactionUtil.rollBack();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    /*private double calculatePenaltyAmount(double totalAmountToPay, int loanId) throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT Date_Due FROM Customer_Loan WHERE Loan_Id = ?", loanId);
        if (resultSet.next()) {
            LocalDate dueDate = resultSet.getDate("Date_Due").toLocalDate();
            long daysDifference = LocalDate.now().toEpochDay() - dueDate.toEpochDay();
            double penaltyRate = 0.0025; // 0.25%
            double penaltyAmount = totalAmountToPay * penaltyRate * daysDifference;
            return penaltyAmount;
        }
        return 0.0;
    }*/

    public static int getInsertedLoanId() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT LAST_INSERT_ID()");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;
    }

    private double calculateTotalAmountToPay(double loanAmount, double monthlyInterestRate, int durationInDays) {
        double monthlyInterest = monthlyInterestRate / 100.0;
        return loanAmount * (1 + (monthlyInterest * (durationInDays / 30.0)));
    }

    private static boolean insertLoan(Loan loan) throws SQLException {
        return SQLUtil.sql("INSERT INTO Loan (Description, Amount, Duration, Interest_Rate) VALUES (?, ?, ?, ?)", loan.getDescription(), loan.getAmount(), loan.getDuration(), loan.getInterestRate());
    }

    private static boolean insertCustomerLoan(int loanId, String customerId, String dateIssued, String dateDue, double totalAmountToPay) throws SQLException {
        return SQLUtil.sql("INSERT INTO Customer_Loan (Loan_Id, Customer_Id, Date_Issued, Date_Due, Payment_Status, Total_Amount_To_Pay) VALUES (?, ?, ?, ?, 'Placed', ?)", loanId, customerId, dateIssued, dateDue, totalAmountToPay);
    }

    /*private boolean addPenaltyIfOverdue(int loanId, double penaltyAmount) {
        PenaltyRepository penaltyRepository = new PenaltyRepository();
        Penalty penalty = new Penalty(0, "Overdue Penalty", loanId, penaltyAmount, LocalDate.now());
        return penaltyRepository.addPenalty(penalty);
    }*/

    private boolean checkIfOverdue(int loanId) throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT Date_Due FROM Customer_Loan WHERE Loan_Id = ?", loanId);
        if (resultSet.next()) {
            LocalDate dueDate = resultSet.getDate("Date_Due").toLocalDate();
            return LocalDate.now().isAfter(dueDate);
        }
        return false;
    }

    public List<LoanTm> getAllLoan() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM Loan");
        List<LoanTm> loanList = new ArrayList<>();

        while (resultSet.next()) {
            loanList.add(new LoanTm(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            ));
        }
        return loanList;
    }

    public List<Integer> getAllLoanIds() throws SQLException {
        List<Integer> loanIds = new ArrayList<>();

        try (ResultSet resultSet = SQLUtil.sql("SELECT Loan_Id FROM Loan")) {
            while (resultSet.next()) {
                Integer loanId = resultSet.getInt("Loan_Id");
                loanIds.add(loanId);
            }
        }
        return loanIds;
    }

    public List<LoanDetailsTm> getAllDetails() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT \n" +
                "    cl.Customer_Id AS CustomerId,\n" +
                "    c.Name AS CustomerName,\n" +
                "    cl.Loan_Id AS LoanId,\n" +
                "    l.Amount AS LoanAmount,\n" +
                "    coll.Name AS CollateralName,\n" +
                "    cl.Date_Issued AS IssueDate,\n" +
                "    cl.Date_Due AS DueDate,\n" +
                "    cl.Payment_Status AS LoanStatus,\n" +
                "    cl.Total_Amount_To_Pay AS TotalAmountToPay\n" +
                "FROM \n" +
                "    Customer_Loan cl\n" +
                "JOIN \n" +
                "    Customer c ON cl.Customer_Id = c.Customer_ID\n" +
                "JOIN \n" +
                "    Loan l ON cl.Loan_Id = l.Loan_Id\n" +
                "LEFT JOIN \n" +
                "    Collateral_Loan cln ON l.Loan_Id = cln.Loan_Id\n" +
                "LEFT JOIN \n" +
                "    Collateral coll ON cln.Collateral_Id = coll.Collateral_Id;\n");
        List<LoanDetailsTm> detailsList = new ArrayList<>();

        while (resultSet.next()) {
            detailsList.add(new LoanDetailsTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5),
                    resultSet.getDate(6),
                    resultSet.getDate(7),
                    resultSet.getString(8),
                    resultSet.getDouble(9)
            ));
        }
        return detailsList;
    }
}
