
import java.sql.*;
import java.math.BigDecimal;

public class Withdraw extends BankManagementSystem {

    public void execute(long id, BigDecimal value)
            throws SQLException {
        amount(value.toPlainString(), false);

        String sql =
            "UPDATE accounts SET balance=balance-? " +
            "WHERE account_number=? AND balance>=?";

        try (Connection c = connect();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setBigDecimal(1, value);
            p.setLong(2, id);
            p.setBigDecimal(3, value);

            if (p.executeUpdate() != 1)
                throw new SQLException(
                    "Account not found or insufficient balance."
                );
        }
    }
}
