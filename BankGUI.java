
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;

public class BankGUI extends BankManagementSystem {

    private final Color sidebarBlue = new Color(99, 148, 255);
    private final Color cardBlue = new Color(94, 91, 255);
    private final Color background = new Color(239, 239, 239);
    private final Color dark = new Color(45, 50, 65);

    private final CreateAccount creator = new CreateAccount();
    private final Deposit depositor = new Deposit();
    private final Withdraw withdrawer = new Withdraw();
    private final CheckBalance checker = new CheckBalance();
    private final Transfer transferService = new Transfer();
    private final ViewAccounts viewer = new ViewAccounts();

    private JPanel content;
    private CardLayout cardLayout;

    private JLabel accountCount;
    private JLabel balanceTotal;
    private JLabel status;

    private JTextField nameField;
    private JTextField accountField;
    private JTextField amountField;
    private JTextField receiverField;

    private JTextArea resultArea;
    private void showDashboard() {
    cardLayout.show(content, "Home");
    refreshStats();
    }

    public BankGUI() {
        setTitle("Bank_Management_System");
        setSize(1050, 650);
        setMinimumSize(new Dimension(850, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);

        cardLayout = new CardLayout();
        content = new JPanel(cardLayout);
        content.setBackground(background);

        content.add(createDashboard(), "Home");
        content.add(createAccountsPage(), "Accounts");

        add(content, BorderLayout.CENTER);

        status = new JLabel("  Ready");
        status.setBorder(new EmptyBorder(8, 10, 8, 10));
        add(status, BorderLayout.SOUTH);

        refreshStats();
    }

    // ---------------- SIDEBAR ----------------

    private JPanel createSidebar() {
        JPanel side = new JPanel(new BorderLayout());
        side.setPreferredSize(new Dimension(220, 600));
        side.setBackground(sidebarBlue);

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(new EmptyBorder(22, 5, 15, 5));

        JLabel logo = new JLabel("🏦");
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bank = new JLabel("Bank_Management Projec");
        bank.setFont(new Font("Arial", Font.BOLD, 17));
        bank.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel menu = new JLabel("Menu List");
        menu.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        menu.setBorder(new EmptyBorder(20, 10, 10, 10));
        menu.setAlignmentX(Component.CENTER_ALIGNMENT);

        top.add(logo);
        top.add(bank);
        top.add(menu);
        side.add(top, BorderLayout.NORTH);

        JPanel nav = new JPanel(new GridLayout(0, 1));
        nav.setOpaque(false);

        addNav(nav, "🏠    Home", this::showDashboard);
        addNav(nav, "👥    Create Account", this::showCreate);
        addNav(nav, "💵    Deposit Money", this::showDeposit);
        addNav(nav, "💸    Withdraw Money", this::showWithdraw);
        addNav(nav, "🔄    Transfer Funds", this::showTransfer);
        addNav(nav, "📊    Check Balance", this::showBalance);
        addNav(nav, "📋    View Accounts", this::showAccounts);
        addNav(nav, "🗑️    Delete Account", this::showDelete);

        side.add(nav, BorderLayout.CENTER);

        JButton exit = new JButton("⏻    Exit");
        exit.setBackground(sidebarBlue);
        exit.setFocusPainted(false);
        exit.setBorder(new EmptyBorder(15, 15, 20, 10));
        exit.setHorizontalAlignment(SwingConstants.LEFT);
        exit.addActionListener(e -> dispose());

        side.add(exit, BorderLayout.SOUTH);
        return side;
    }

    private void addNav(JPanel panel, String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBackground(sidebarBlue);
        button.setForeground(dark);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(14, 12, 14, 5));
        button.addActionListener(e -> action.run());
        panel.add(button);
    }

    // ---------------- DASHBOARD ----------------

    private JPanel createDashboard() {
        JPanel page = new JPanel(new BorderLayout(15, 20));
        page.setBackground(background);
        page.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel("Dashboard");
        heading.setFont(new Font("Arial", Font.BOLD, 21));
        page.add(heading, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JPanel cards = new JPanel(new GridLayout(1, 2, 15, 15));
        cards.setOpaque(false);
        cards.setMaximumSize(new Dimension(800, 130));

        accountCount = new JLabel("0");
        balanceTotal = new JLabel("Rs. 0.00");

        cards.add(createCard("👥", "Total Accounts", accountCount));
        cards.add(createCard("💰", "Total Bank Balance", balanceTotal));

        center.add(cards);
        center.add(Box.createVerticalStrut(45));

        JLabel operations = new JLabel("Banking Operations");
        operations.setFont(new Font("Arial", Font.BOLD, 18));
        operations.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(operations);
        center.add(Box.createVerticalStrut(15));

        JPanel buttons = new JPanel(new GridLayout(2, 3, 12, 15));
        buttons.setOpaque(false);
        buttons.setBorder(new EmptyBorder(15, 10, 15, 10));

        addAction(buttons, "Create Account", this::showCreate);
        addAction(buttons, "Deposit", this::showDeposit);
        addAction(buttons, "Withdraw", this::showWithdraw);
        addAction(buttons, "Transfer", this::showTransfer);
        addAction(buttons, "Check Balance", this::showBalance);
        addAction(buttons, "View Accounts", this::showAccounts);

        center.add(buttons);
        page.add(center, BorderLayout.CENTER);

        return page;
    }

    private JPanel createCard(
            String icon, String title, JLabel value) {

        JPanel card = new JPanel(new BorderLayout(8, 8));
        card.setBackground(cardBlue);
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel image = new JLabel(icon);
        image.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

        value.setForeground(Color.WHITE);
        value.setFont(new Font("Arial", Font.BOLD, 20));
        value.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel label = new JLabel(title, SwingConstants.RIGHT);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 13));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(image, BorderLayout.WEST);
        top.add(value, BorderLayout.EAST);

        card.add(top, BorderLayout.CENTER);
        card.add(label, BorderLayout.SOUTH);

        return card;
    }

    private void addAction(
            JPanel panel, String text, Runnable action) {

        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(e -> action.run());
        panel.add(button);
    }

    // ---------------- CONTENT PAGES ----------------

    private JPanel createAccountsPage() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(background);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Account Management");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(title, BorderLayout.NORTH);
        return panel;
    }

    private void displayPage(String title, JPanel form) {
        JPanel page = new JPanel(new BorderLayout(15, 15));
        page.setBackground(background);
        page.setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Arial", Font.BOLD, 23));

        JPanel formHolder = new JPanel(new BorderLayout());
        formHolder.setBackground(background);
        formHolder.add(form, BorderLayout.NORTH);

        resultArea = new JTextArea(8, 35);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setBorder(
            BorderFactory.createTitledBorder("Operation Result")
        );

        JPanel center = new JPanel(new BorderLayout(15, 20));
        center.setOpaque(false);
        center.add(formHolder, BorderLayout.NORTH);
        center.add(resultScroll, BorderLayout.CENTER);

        page.add(heading, BorderLayout.NORTH);
        page.add(center, BorderLayout.CENTER);

        content.add(page, title);
        cardLayout.show(content, title);
        content.revalidate();
        content.repaint();
    }

    private JTextField field() {
        return new JTextField(20);
    }

    private JPanel formPanel(String... labels) {
        JPanel form = new JPanel(new GridLayout(
            labels.length, 2, 12, 12
        ));
        form.setBackground(background);
        form.setBorder(new EmptyBorder(15, 0, 15, 0));

        for (String label : labels) {
            form.add(new JLabel(label));
            form.add(field());
        }

        return form;
    }

    private JButton actionButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setBackground(new Color(45, 105, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(e -> action.run());
        return button;
    }

    // ---------------- CREATE ACCOUNT ----------------

    private void showCreate() {
        nameField = field();
        amountField = field();
        amountField.setText("0.00");

        JPanel form = new JPanel(new GridLayout(2, 2, 12, 12));
        form.setBackground(background);

        form.add(new JLabel("Customer Name:"));
        form.add(nameField);
        form.add(new JLabel("Opening Deposit:"));
        form.add(amountField);

        JButton submit = actionButton(
            "Create Account", this::createAccount
        );

        form.add(new JLabel(""));
        form.add(submit);

        displayPage("Create Account", form);
    }

    private void createAccount() {
        try {
            BigDecimal opening = amount(amountField.getText(), true);
            long id = creator.create(nameField.getText(), opening);

            resultArea.setText(
                "ACCOUNT CREATED SUCCESSFULLY\n\n" +
                "Customer: " + nameField.getText() +
                "\nAccount Number: " + id +
                "\nOpening Balance: Rs. " + opening
            );

            status.setText("  Account created successfully.");
            refreshStats();

        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    // ---------------- DEPOSIT ----------------

    private void showDeposit() {
        accountField = field();
        amountField = field();

        JPanel form = new JPanel(new GridLayout(2, 2, 12, 12));
        form.setBackground(background);

        form.add(new JLabel("Account Number:"));
        form.add(accountField);
        form.add(new JLabel("Deposit Amount:"));
        form.add(amountField);

        JButton submit = actionButton("Deposit", this::doDeposit);

        JPanel wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setBackground(background);
        wrapper.add(form, BorderLayout.CENTER);
        wrapper.add(submit, BorderLayout.SOUTH);

        displayPage("Deposit Money", wrapper);
    }

    private void doDeposit() {
        try {
            long id = accountId(accountField.getText());
            depositor.execute(id, amount(amountField.getText(), false));

            resultArea.setText(
                "DEPOSIT SUCCESSFUL\n\n" + checker.execute(id)
            );

            status.setText("  Deposit completed.");
            refreshStats();

        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    // ---------------- WITHDRAW ----------------

    private void showWithdraw() {
        accountField = field();
        amountField = field();

        JPanel form = new JPanel(new GridLayout(2, 2, 12, 12));
        form.setBackground(background);

        form.add(new JLabel("Account Number:"));
        form.add(accountField);
        form.add(new JLabel("Withdrawal Amount:"));
        form.add(amountField);

        JButton submit = actionButton("Withdraw", this::doWithdraw);

        JPanel wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setBackground(background);
        wrapper.add(form, BorderLayout.CENTER);
        wrapper.add(submit, BorderLayout.SOUTH);

        displayPage("Withdraw Money", wrapper);
    }

    private void doWithdraw() {
        try {
            long id = accountId(accountField.getText());
            withdrawer.execute(id, amount(amountField.getText(), false));

            resultArea.setText(
                "WITHDRAWAL SUCCESSFUL\n\n" + checker.execute(id)
            );

            status.setText("  Withdrawal completed.");
            refreshStats();

        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    // ---------------- CHECK BALANCE ----------------

    private void showBalance() {
        accountField = field();

        JPanel form = new JPanel(new GridLayout(1, 2, 12, 12));
        form.setBackground(background);

        form.add(new JLabel("Account Number:"));
        form.add(accountField);

        JButton submit = actionButton(
            "Check Balance", this::doBalance
        );

        JPanel wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setBackground(background);
        wrapper.add(form, BorderLayout.NORTH);
        wrapper.add(submit, BorderLayout.CENTER);

        displayPage("Check Balance", wrapper);
    }

    private void doBalance() {
        try {
            resultArea.setText(
                checker.execute(accountId(accountField.getText()))
            );
            status.setText("  Balance retrieved.");

        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    // ---------------- TRANSFER ----------------

    private void showTransfer() {
        accountField = field();
        receiverField = field();
        amountField = field();

        JPanel form = new JPanel(new GridLayout(3, 2, 12, 12));
        form.setBackground(background);

        form.add(new JLabel("Sender Account:"));
        form.add(accountField);
        form.add(new JLabel("Receiver Account:"));
        form.add(receiverField);
        form.add(new JLabel("Transfer Amount:"));
        form.add(amountField);

        JButton submit = actionButton("Transfer", this::doTransfer);

        JPanel wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setBackground(background);
        wrapper.add(form, BorderLayout.CENTER);
        wrapper.add(submit, BorderLayout.SOUTH);

        displayPage("Transfer Funds", wrapper);
    }

    private void doTransfer() {
        try {
            long from = accountId(accountField.getText());
            long to = accountId(receiverField.getText());

            transferService.execute(
                from, to, amount(amountField.getText(), false)
            );

            resultArea.setText(
                "TRANSFER SUCCESSFUL\n\nSENDER\n" +
                checker.execute(from) +
                "\n\nRECEIVER\n" + checker.execute(to)
            );

            status.setText("  Transfer completed.");
            refreshStats();

        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    // ---------------- VIEW ACCOUNTS ----------------

    private void showAccounts() {
        JPanel page = new JPanel(new BorderLayout(12, 12));
        page.setBackground(background);
        page.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("All Bank Accounts");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JTextArea accounts = new JTextArea();
        accounts.setEditable(false);
        accounts.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JButton refresh = actionButton("Refresh Accounts", () -> {
            try {
                accounts.setText(viewer.execute());
                status.setText("  Account list refreshed.");
            } catch (Exception e) {
                accounts.setText("Error: " + e.getMessage());
            }
        });

        try {
            accounts.setText(viewer.execute());
        } catch (Exception e) {
            accounts.setText("Error: " + e.getMessage());
        }

        page.add(title, BorderLayout.NORTH);
        page.add(new JScrollPane(accounts), BorderLayout.CENTER);
        page.add(refresh, BorderLayout.SOUTH);

        content.add(page, "ViewAccounts");
        cardLayout.show(content, "ViewAccounts");
    }

    // ---------------- DASHBOARD STATS ----------------

    private void refreshStats() {
        if (accountCount == null || balanceTotal == null) return;

        try (Connection c = connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(
                 "SELECT COUNT(*), COALESCE(SUM(balance),0) FROM accounts"
             )) {

            if (r.next()) {
                accountCount.setText(String.valueOf(r.getLong(1)));
                balanceTotal.setText(
                    "Rs. " + r.getBigDecimal(2).toPlainString()
                );
            }

        } catch (Exception e) {
            accountCount.setText("--");
            balanceTotal.setText("--");
        }
    }
    
    private void showDelete() {
            JPanel panel = new JPanel(new java.awt.GridBagLayout());
            panel.setBackground(Color.WHITE);
        
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
        
            JLabel title = new JLabel("Delete Bank Account");
            title.setFont(new Font("Arial", Font.BOLD, 24));
        
            JLabel accountLabel = new JLabel("Account Number:");
            JTextField accountField = new JTextField(20);
        
            JButton deleteButton = new JButton("Delete Account");
        
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            panel.add(title, gbc);
        
            gbc.gridy++;
            gbc.gridwidth = 1;
            panel.add(accountLabel, gbc);
        
            gbc.gridx = 1;
            panel.add(accountField, gbc);
        
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            panel.add(deleteButton, gbc);
        
            deleteButton.addActionListener(e -> {
                try {
                    long accountNumber =
                        accountId(accountField.getText().trim());
        
                    int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Permanently delete account " + accountNumber + "?",
                        "Confirm Account Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );
        
                    if (confirm != JOptionPane.YES_OPTION) {
                        return;
                    }
        
                    new DeleteAccount().execute(accountNumber);
        
                    JOptionPane.showMessageDialog(
                        this, "Account deleted successfully!"
                    );
        
                    accountField.setText("");
                    refreshStats();
        
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Deletion Failed",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            });
        
            displayPage("Delete Account", panel);
    }   

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BankGUI().setVisible(true);
        });
    }
}
