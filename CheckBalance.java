
import java.sql.*;

public class CheckBalance extends BankManagementSystem {

    public String execute(long id) throws SQLException {
        String sql =
            "SELECT customer_name,balance FROM accounts " +
            "WHERE account_number=?";

        try (Connection c = connect();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setLong(1, id);

            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    return "Account Number: " + id +
                        "\nCustomer: " + r.getString("customer_name") +
                        "\nBalance: Rs. " + r.getBigDecimal("balance");
                }
            }
        }
        throw new SQLException("Account not found.");
    }
}
