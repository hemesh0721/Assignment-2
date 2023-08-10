import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

class Expense {
    private String description;
    private double amount;
    private String category;
    private Date date;

    public Expense(String description, double amount, String category, Date date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "[" + category + "] " + description + " - $" + amount;
    }
}

class ExpenseTrackingSystemApp extends JFrame {
    private ArrayList<Expense> expenses;
    private JList<Expense> expenseList;
    private DefaultListModel<Expense> listModel;
    private JTextField descriptionField;
    private JTextField amountField;
    private JComboBox<String> categoryComboBox;
    private JButton reportButton;
    private JLabel totalExpensesLabel;

    public ExpenseTrackingSystemApp() {
        expenses = new ArrayList<>();
        listModel = new DefaultListModel<>();
        expenseList = new JList<>(listModel);
        descriptionField = new JTextField(15);
        amountField = new JTextField(8);
        categoryComboBox = new JComboBox<>(new String[]{"Food", "Transportation", "Entertainment", "Clothes","Groceries","Other"});
        reportButton = new JButton("Generate Report");
        totalExpensesLabel = new JLabel("Total expenses: $0.00");

        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });

        reportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryComboBox);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(addButton, BorderLayout.CENTER);

        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.add(reportButton, BorderLayout.CENTER);
        reportPanel.add(totalExpensesLabel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(new JScrollPane(expenseList), BorderLayout.CENTER);
        add(panel, BorderLayout.NORTH);
        add(reportPanel, BorderLayout.SOUTH);

        setTitle("Expense Tracking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addExpense() {
        String description = descriptionField.getText();
        String amountStr = amountField.getText();
        String category = (String) categoryComboBox.getSelectedItem();
        Date date = new Date();

        if (!description.isEmpty() && !amountStr.isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                Expense expense = new Expense(description, amount, category, date);
                expenses.add(expense);
                listModel.addElement(expense);
                descriptionField.setText("");
                amountField.setText("");
                updateTotalExpenses();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.");
            }
        }
    }

    private void generateReport() {
        StringBuilder report = new StringBuilder("Expense Report:\n\n");
        for (Expense expense : expenses) {
            report.append(expense).append(" (Date: ").append(expense.getDate()).append(")\n");
        }
        JOptionPane.showMessageDialog(this, report.toString(), "Expense Report", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateTotalExpenses() {
        double totalExpenses = 0;
        for (Expense expense : expenses) {
            totalExpenses += expense.getAmount();
        }
        totalExpensesLabel.setText("Total expenses: $" + String.format("%.2f", totalExpenses));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ExpenseTrackingSystemApp();
            }
        });
    }
}
