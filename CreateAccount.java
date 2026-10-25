
import java.sql.*;
import java.math.BigDecimal;

public class CreateAccount extends BankManagementSystem {

    public long create(String name, BigDecimal opening)
            throws SQLException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Enter customer name.");
        }
        amount(opening.toPlainString(), true);

        String sql =
            "INSERT INTO accounts(customer_name,balance) VALUES(?,?)";

        try (Connection c = connect();
             PreparedStatement p = c.prepareStatement(
                 sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1, name.trim());
            p.setBigDecimal(2, opening);
            p.executeUpdate();

            try (ResultSet r = p.getGeneratedKeys()) {
                if (r.next()) return r.getLong(1);
            }
        }
        throw new SQLException("Could not generate account number.");
    }
}
