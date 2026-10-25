
import java.sql.*;
import java.math.BigDecimal;

public class Transfer extends BankManagementSystem {

    public void execute(long from, long to, BigDecimal value)
            throws SQLException {
        amount(value.toPlainString(), false);

        if (from == to)
            throw new IllegalArgumentException(
                "Choose different accounts."
            );

        try (Connection c = connect()) {
            c.setAutoCommit(false);

            try {
                long first = Math.min(from, to);
                long second = Math.max(from, to);

                requireAccount(c, first);
                requireAccount(c, second);

                for (long id : new long[]{first, second}) {
                    try (PreparedStatement p = c.prepareStatement(
                        "SELECT account_number FROM accounts " +
                        "WHERE account_number=? FOR UPDATE")) {
                        p.setLong(1, id);
                        try (ResultSet r = p.executeQuery()) {
                            if (!r.next())
                                throw new SQLException("Account not found.");
                        }
                    }
                }

                try (PreparedStatement p = c.prepareStatement(
                    "UPDATE accounts SET balance=balance-? " +
                    "WHERE account_number=? AND balance>=?")) {
                    p.setBigDecimal(1, value);
                    p.setLong(2, from);
                    p.setBigDecimal(3, value);

                    if (p.executeUpdate() != 1)
                        throw new SQLException("Insufficient balance.");
                }

                try (PreparedStatement p = c.prepareStatement(
                    "UPDATE accounts SET balance=balance+? " +
                    "WHERE account_number=?")) {
                    p.setBigDecimal(1, value);
                    p.setLong(2, to);
                    p.executeUpdate();
                }

                c.commit();

            } catch (SQLException | RuntimeException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }
}
