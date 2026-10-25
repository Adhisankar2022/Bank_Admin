
import javax.swing.JFrame;
import java.sql.*;
import java.math.BigDecimal;

public class BankManagementSystem extends JFrame {

    protected static final String URL =
        "jdbc:mysql://localhost:3306/bankdb";
    protected static final String USER = "root";
    protected static final String PASSWORD = "Admin@123";

    public BankManagementSystem() {
        super("Bank Management System");
    }

    protected Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    protected BigDecimal amount(String value, boolean zeroAllowed) {
        BigDecimal a = new BigDecimal(value.trim());

        if (a.scale() > 2 || a.precision() > 15 ||
            a.signum() < 0 ||
            (!zeroAllowed && a.signum() == 0)) {
            throw new IllegalArgumentException("Invalid amount.");
        }
        return a;
    }

    protected long accountId(String value) {
        long id = Long.parseLong(value.trim());
        if (id <= 0) {
            throw new IllegalArgumentException(
                "Account number must be positive."
            );
        }
        return id;
    }

    protected void requireAccount(Connection c, long id)
            throws SQLException {
        try (PreparedStatement p = c.prepareStatement(
                "SELECT account_number FROM accounts WHERE account_number=?")) {
            p.setLong(1, id);
            try (ResultSet r = p.executeQuery()) {
                if (!r.next()) {
                    throw new SQLException("Account not found: " + id);
                }
            }
        }
    }
}
