CREATE DATABASE MICRO_FINANCE_MANAGEMENT_SYSTEM;

USE MICRO_FINANCE_MANAGEMENT_SYSTEM;

CREATE TABLE Customer (
                          Customer_ID VARCHAR(12) PRIMARY KEY,
                          Name VARCHAR(50) NOT NULL,
                          Address VARCHAR(150),
                          Email VARCHAR(100),
                          Phone_Number VARCHAR(10)
);

CREATE TABLE Loan (
                      Loan_Id INT AUTO_INCREMENT PRIMARY KEY,
                      Description VARCHAR(100) NOT NULL,
                      Amount DECIMAL(10, 2) NOT NULL,
                      Duration VARCHAR(15) NOT NULL,
                      Interest_Rate DECIMAL(5, 2) NOT NULL
);

CREATE TABLE Customer_Loan (
                               CustomerLoan_Id INT AUTO_INCREMENT PRIMARY KEY,
                               Loan_Id INT,
                               Customer_Id VARCHAR(12),
                               Date_Issued DATE,
                               Date_Due DATE,
                               Payment_Status VARCHAR(50),
                               Total_Amount_To_Pay DECIMAL (10,2),
                               FOREIGN KEY (Loan_Id) REFERENCES Loan(Loan_Id) ON UPDATE CASCADE ON DELETE CASCADE,
                               FOREIGN KEY (Customer_Id) REFERENCES Customer(Customer_Id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Collateral (
                            Collateral_Id VARCHAR(12) PRIMARY KEY,
                            Name VARCHAR(50) NOT NULL,
                            Address VARCHAR(150),
                            Phone_Number VARCHAR(10)
);

CREATE TABLE Collateral_Loan (
                                 Collateral_Id VARCHAR(12),
                                 Loan_Id INT,
                                 PRIMARY KEY (Collateral_Id, Loan_Id),
                                 FOREIGN KEY (Collateral_Id) REFERENCES Collateral(Collateral_Id) ON UPDATE CASCADE ON DELETE CASCADE,
                                 FOREIGN KEY (Loan_Id) REFERENCES Loan(Loan_Id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Penalty (
                         Penalty_Id INT AUTO_INCREMENT PRIMARY KEY,
                         Reason VARCHAR(150),
                         Loan_Id INT,
                         Amount DECIMAL(10, 2),
                         Date_Applied DATE,
                         FOREIGN KEY (Loan_Id) REFERENCES Loan(Loan_Id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Payment (
                         Payment_Id INT AUTO_INCREMENT PRIMARY KEY,
                         Payment_Date DATE,
                         Loan_Id INT,
                         Amount DECIMAL(10, 2),
                         FOREIGN KEY (Loan_Id) REFERENCES Loan(Loan_Id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE ProfitLoss (
                            Activity_Id INT AUTO_INCREMENT PRIMARY KEY,
                            Loss DECIMAL(10, 2),
                            Profit DECIMAL(10, 2),
                            Date DATE
);

CREATE TABLE Transaction (
                             Transaction_Id INT AUTO_INCREMENT PRIMARY KEY,
                             Type VARCHAR(50),
                             Date DATE,
                             Loan_Id INT,
                             Activity_Id INT,
                             Amount DECIMAL(10, 2),
                             FOREIGN KEY (Loan_Id) REFERENCES Loan(Loan_Id) ON UPDATE CASCADE ON DELETE CASCADE,
                             FOREIGN KEY (Activity_Id) REFERENCES ProfitLoss(Activity_Id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Employee (
                          Employee_Id VARCHAR(12) PRIMARY KEY,
                          Name VARCHAR(100),
                          Address VARCHAR(150),
                          Salary DECIMAL(10, 2),
                          Phone_Number VARCHAR(10),
                          Email VARCHAR(100)
);

CREATE TABLE Assignment (
                            Assignment_Id INT AUTO_INCREMENT PRIMARY KEY,
                            Task VARCHAR(150),
                            Employee_Id VARCHAR(12),
                            Status VARCHAR(50),
                            Customer_Id VARCHAR(12),
                            Date_Assigned DATE,
                            FOREIGN KEY (Employee_Id) REFERENCES Employee(Employee_Id) ON UPDATE CASCADE ON DELETE CASCADE,
                            FOREIGN KEY (Customer_Id) REFERENCES Customer(Customer_Id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Expense (
                         Expense_Id INT AUTO_INCREMENT PRIMARY KEY,
                         Type VARCHAR(150),
                         Date DATE,
                         Employee_Id VARCHAR(12),
                         Amount DECIMAL(10, 2),
                         FOREIGN KEY (Employee_Id) REFERENCES Employee(Employee_Id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE User (
                      User_ID VARCHAR(12) PRIMARY KEY,
                      Name VARCHAR(50) NOT NULL,
                      Password VARCHAR(50) NOT NULL
);

INSERT INTO User (User_ID, Name, Password)
VALUES ('2132', 'Admin', '@1234');

DELIMITER $$
CREATE TRIGGER add_penalty_trigger AFTER INSERT ON Customer_Loan
    FOR EACH ROW
BEGIN
    DECLARE due_date DATE;
    DECLARE penalty_amount DECIMAL(10, 2);
    DECLARE days_late INT;

    SELECT Date_Due INTO due_date FROM Customer_Loan WHERE Loan_Id = NEW.Loan_Id;

    SET days_late = DATEDIFF(CURRENT_DATE(), due_date);

    IF days_late > 0 THEN
        SET penalty_amount = NEW.Total_Amount_To_Pay * (days_late * 0.0025);

    INSERT INTO Penalty (Loan_Id, Amount, Date_Applied, Reason) VALUES (NEW.Loan_Id, penalty_amount, CURRENT_DATE(), 'Overdue Penalty');
END IF;
END$$
DELIMITER ;

