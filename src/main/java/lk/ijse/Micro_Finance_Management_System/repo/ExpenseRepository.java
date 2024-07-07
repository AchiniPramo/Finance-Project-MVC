package lk.ijse.Micro_Finance_Management_System.repo;

import lk.ijse.Micro_Finance_Management_System.model.Customer;
import lk.ijse.Micro_Finance_Management_System.model.Expense;
import lk.ijse.Micro_Finance_Management_System.model.tm.ExpenseTm;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {
    public List<ExpenseTm> getAllExpense() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM Expense");
        List<ExpenseTm> expenseList = new ArrayList<>();

        while (resultSet.next()) {
            expenseList.add(new ExpenseTm(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            ));
        }
        return expenseList;
    }

    public boolean saveExpense(Expense expense) throws SQLException {
        return SQLUtil.sql("INSERT INTO Expense (Type, Date, Employee_Id, Amount) VALUES (?,?,?,?)", expense.getType(), expense.getDate(), expense.getEmployeeId(), expense.getAmount());

    }

    public List<Integer> getAllExpenseIds() throws SQLException {
        List<Integer> expenseIds = new ArrayList<>();

        try (ResultSet resultSet = SQLUtil.sql("SELECT Expense_Id FROM Expense")) {
            while (resultSet.next()) {
                int expenseId = resultSet.getInt("Expense_Id");
                expenseIds.add(expenseId);
            }
        }
        return expenseIds;
    }

    public boolean updateExpense(Expense expense) throws SQLException {
        return SQLUtil.sql("UPDATE Expense SET Type = ?, Date = ?, Employee_Id = ?, Amount = ? WHERE Expense_Id = ?", expense.getType(), expense.getDate(), expense.getEmployeeId(), expense.getAmount(), expense.getExpenseId());
    }

    public Expense search(int expenseId) throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM Expense WHERE Expense_Id = ?", expenseId);

        if (resultSet.next()) {
            return new Expense(
                    resultSet.getInt("Expense_Id"),
                    resultSet.getString("Type"),
                    resultSet.getDate("Date"),
                    resultSet.getString("Employee_Id"),
                    resultSet.getDouble("Amount")
            );
        }
        return null;
    }

    public boolean deleteExpense(int expenseId) throws SQLException {
        return SQLUtil.sql("DELETE FROM Expense WHERE Expense_Id = ?", expenseId);
    }
}
