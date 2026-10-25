
import java.sql.*;

public class ViewAccounts extends BankManagementSystem {

    public String execute() throws SQLException {
        StringBuilder result = new StringBuilder();

        String sql =
            "SELECT account_number,customer_name,balance " +
            "FROM accounts ORDER BY account_number";

        try (Connection c = connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {
            while (r.next()) {
                result.append("Account: ")
                    .append(r.getLong("account_number"))
                    .append("\nName: ")
                    .append(r.getString("customer_name"))
                    .append("\nBalance: Rs. ")
                    .append(r.getBigDecimal("balance"))
                    .append("\n--------------------------\n");
            }
        }

        return result.length() == 0
            ? "No accounts found."
            : result.toString();
    }
}
