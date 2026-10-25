#  Sky Bank Limited — Bank Management System

A desktop-based Bank Management System developed using **Java Swing, JDBC, and MySQL**. The application provides a graphical interface for managing customer accounts and performing basic banking operations.

## ✨ Features

- **Dashboard** — View the total number of accounts and combined account balance.
- **Create Account** — Register customers with an automatically generated account number.
- **Deposit Money** — Deposit funds into an existing account.
- **Withdraw Money** — Withdraw funds from an account.
- **Transfer Money** — Transfer funds between two accounts.
- **Check Balance** — View the balance of a selected account.
- **View Accounts** — Display account details stored in the database.
- **Delete Account** — Delete an existing account after confirmation.
- **Account Selection** — Select accounts from dropdowns displaying account numbers, customer names, and balances.
- **Database Integration** — Store and retrieve account information using MySQL and JDBC.
- **Graphical User Interface** — Perform operations through a single Java Swing window.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Application logic and object-oriented programming |
| Java Swing | Graphical user interface |
| JDBC | Database connectivity |
| MySQL | Persistent account storage |
| BlueJ | Java development environment |
| Git and GitHub | Version control and project hosting |

## 📁 Project Structure

```text
Sky-Bank-Limited/
├── BankManagementSystem.java
├── BankGUI.java
├── CreateAccount.java
├── Deposit.java
├── Withdraw.java
├── Transfer.java
├── CheckBalance.java
├── ViewAccounts.java
├── DeleteAccount.java
├── README.md
└── database/
    └── setup.sql
```

*The `database/setup.sql` file is optional; create it if you want to include the SQL setup script in your repository.*

## ⚙️ Requirements

Before running the project, install:

- Java Development Kit (JDK)
- MySQL Server
- MySQL Connector/J (JDBC driver)
- BlueJ or another Java IDE

## 🗄️ Database Setup

### 1. Create the database

Open MySQL Workbench or the MySQL command line and run:

```sql
CREATE DATABASE bankdb;
USE bankdb;
```

### 2. Create the accounts table

```sql
CREATE TABLE accounts (
    account_number BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00
);
```

### 3. Configure the database connection

In `BankManagementSystem.java`, configure the JDBC connection:

```java
protected static final String URL =
    "jdbc:mysql://localhost:3306/bankdb";

protected static final String USER = "root";

protected static final String PASSWORD =
    "YOUR_PASSWORD";
```

Replace `YOUR_PASSWORD` with your local MySQL password. **Do not commit real database credentials to GitHub.** Prefer environment variables or a local configuration file excluded from version control.

Ensure MySQL Connector/J is added to the project's classpath.

## ▶️ How to Run

1. Start your MySQL server.
2. Create the `bankdb` database and `accounts` table.
3. Configure the database connection.
4. Open the project in BlueJ.
5. Compile `BankManagementSystem.java` first.
6. Compile all the operation classes.
7. Compile `BankGUI.java`.
8. Run the `main` method in `BankGUI.java`.

The Sky Bank Limited application should open in a desktop window.

## 🧩 Object-Oriented Design

The project uses inheritance to share database connection and validation functionality through `BankManagementSystem.java`.

Separate classes handle individual operations:

- `CreateAccount` — Account creation
- `Deposit` — Deposits
- `Withdraw` — Withdrawals
- `Transfer` — Account-to-account transfers
- `CheckBalance` — Balance enquiries
- `ViewAccounts` — Account listing
- `DeleteAccount` — Account deletion
- `BankGUI` — Swing interface and navigation

## 🔐 Security and Limitations

- This is an educational project intended to demonstrate Java GUI development and database integration.
- Account deletion is permanent unless a separate backup or recovery mechanism is implemented.
- The application is not a production-ready banking system.
- Real banking software requires authentication, authorization, audit logs, secure credential management, transaction controls, and additional safeguards.

## 🚀 Future Enhancements

- User login and authentication
- Customer and administrator roles
- Transaction history
- Printable account statements
- Search and filter accounts
- Improved validation and error handling
- Account recovery and audit logs
- Enhanced security and password management

## 👨‍💻 Author
- Adhi Sankar A
- Aleena Afsal
- Alfio Shaji

**Project:** Sky Bank Limited — Bank Management System  
**Language:** Java  
**Database:** MySQL  
**Interface:** Java Swing

## 📄 License

This project is available for educational and learning purposes. Add a license file if you intend to distribute it under a specific open-source license.
