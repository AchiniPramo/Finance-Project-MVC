package lk.ijse.Micro_Finance_Management_System.repo;

import lk.ijse.Micro_Finance_Management_System.model.Payment;
import lk.ijse.Micro_Finance_Management_System.model.tm.PaymentTm;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;
import lk.ijse.Micro_Finance_Management_System.util.TransactionUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    public static List<PaymentTm> getAllPayment() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM Payment");
        List<PaymentTm> paymentList = new ArrayList<>();

        while (resultSet.next()) {
            paymentList.add(new PaymentTm(
                    resultSet.getInt(1),
                    resultSet.getDate(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            ));
        }
        return paymentList;
    }

    public boolean savePayment(Payment payment) throws SQLException {
        try {
            TransactionUtil.startTransaction(); // Start transaction

            // Insert Payment table
            String insertPaymentSQL = "INSERT INTO Payment (Payment_Date, Loan_Id, Amount) VALUES (?, ?, ?)";
            boolean isPaymentInserted = SQLUtil.<Boolean>sql(insertPaymentSQL, payment.getPaymentDate(), payment.getLoanId(), payment.getAmount());

            // Update Total_Amount_To_Pay in Customer_Loan table
            String updateCustomerLoanSQL = "UPDATE Customer_Loan SET Total_Amount_To_Pay = Total_Amount_To_Pay - ? WHERE Loan_Id = ?";
            boolean isCustomerLoanUpdated = SQLUtil.<Boolean>sql(updateCustomerLoanSQL, payment.getAmount(), payment.getLoanId());

            String checkAmountSQL = "SELECT Total_Amount_To_Pay FROM Customer_Loan WHERE Loan_Id = ?";
            ResultSet resultSet = SQLUtil.sql(checkAmountSQL, payment.getLoanId());
            if (resultSet.next() && resultSet.getDouble("Total_Amount_To_Pay") == 0) {
                String updateStatusSQL = "UPDATE Customer_Loan SET Payment_Status = 'Loan Closed' WHERE Loan_Id = ?";
                boolean isStatusUpdated = SQLUtil.<Boolean>sql(updateStatusSQL, payment.getLoanId());
                if (!isStatusUpdated) {
                    TransactionUtil.rollBack();
                    return false;
                }
            }

            if (isPaymentInserted && isCustomerLoanUpdated) {
                TransactionUtil.endTransaction();
                return true;
            } else {
                TransactionUtil.rollBack();
                return false;
            }
        } catch (SQLException e) {
            TransactionUtil.rollBack(); 
            throw e;
        }
    }

    public List<Integer> getAllPaymentIds() throws SQLException {
        List<Integer> paymentIds = new ArrayList<>();

        try (ResultSet resultSet = SQLUtil.sql("SELECT Payment_Id FROM Payment")) {
            while (resultSet.next()) {
                Integer driverId = resultSet.getInt("Payment_Id");
                paymentIds.add(driverId);
            }
        }
        return paymentIds;
    }

    public Payment searchById(int paymentId) throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT Payment_Date, Loan_Id, Amount FROM Payment WHERE Payment_Id = ?", paymentId);
        if (resultSet.next()) {
            return new Payment(
                    resultSet.getDate("Payment_Date"),
                    resultSet.getInt("Loan_Id"),
                    resultSet.getDouble("Amount")
            );
        }
        return null;
    }

}