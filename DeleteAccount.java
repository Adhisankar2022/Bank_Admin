
import java.sql.*;
import javax.swing.*;

public class DeleteAccount extends BankManagementSystem {

    public boolean execute(long accountNumber) {
        String sql = "DELETE FROM accounts WHERE account_number = ?";

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, accountNumber);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                return true;
            }

            throw new SQLException("Account number not found.");

        } catch (SQLException e) {
            throw new RuntimeException(
                "Could not delete account: " + e.getMessage(), e
            );
        }
    }
}
