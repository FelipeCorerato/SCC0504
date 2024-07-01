package main.java.view;

import main.java.model.Book;
import main.java.model.LibraryManager;
import main.java.model.Loan;
import main.java.model.Patron;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LoanPanel extends JPanel {
    private LibraryManager libraryManager;
    private DefaultTableModel loanTableModel;
    private JTable loanTable;
    private JComboBox<Book> bookComboBox;
    private JComboBox<Patron> patronComboBox;

    public LoanPanel(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Tabela de empréstimos
        String[] columnNames = {"Book Title", "Patron Name", "Loan Date", "Due Date", "Returned"};
        loanTableModel = new DefaultTableModel(columnNames, 0);
        loanTable = new JTable(loanTableModel);
        JScrollPane scrollPane = new JScrollPane(loanTable);
        add(scrollPane, BorderLayout.CENTER);

        // Formulário de entrada
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        bookComboBox = new JComboBox<>();
        patronComboBox = new JComboBox<>();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Book:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        formPanel.add(bookComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Patron:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        formPanel.add(patronComboBox, gbc);

        JButton addButton = new JButton("Perform Loan");
        addButton.addActionListener(e -> {
            Book book = (Book) bookComboBox.getSelectedItem();
            Patron patron = (Patron) patronComboBox.getSelectedItem();
            libraryManager.performLoan(book, patron);
            updateLoanTable();
            updateComboBoxes();
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        formPanel.add(addButton, gbc);

        JButton returnButton = new JButton("Return Loan");
        returnButton.addActionListener(e -> returnLoan());

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        formPanel.add(returnButton, gbc);

        add(formPanel, BorderLayout.SOUTH);

        updateLoanTable();
        updateComboBoxes();
    }

    public void updateLoanTable() {
        loanTableModel.setRowCount(0);
        List<Loan> loans = libraryManager.getLoans();

        for (Loan loan : loans) {
            Object[] rowData = {
                    loan.getBook().getTitle(),
                    loan.getPatron().getName(),
                    loan.getLoanDate(),
                    loan.getDueDate(),
                    loan.isReturned() ? "Yes" : "No"
            };
            loanTableModel.addRow(rowData);
        }
    }

    public void updateComboBoxes() {
        bookComboBox.removeAllItems();
        patronComboBox.removeAllItems();

        List<Book> books = libraryManager.getBooks();
        List<Patron> patrons = libraryManager.getPatrons();
        List<Loan> loans = libraryManager.getLoans();

        for (Book book : books) {
            if (book.isAvailable()) {
                bookComboBox.addItem(book);
            }
        }

        for (Patron patron : patrons) {
            boolean hasOpenLoan = loans.stream().anyMatch(loan -> loan.getPatron().equals(patron) && !loan.isReturned());
            if (!hasOpenLoan) {
                patronComboBox.addItem(patron);
            }
        }
    }

    private void returnLoan() {
        int selectedRow = loanTable.getSelectedRow();
        if (selectedRow != -1) {
            String bookTitle = (String) loanTableModel.getValueAt(selectedRow, 0);
            Loan loanToReturn = null;
            List<Loan> loans = libraryManager.getLoans();
            for (Loan loan : loans) {
                if (loan.getBook().getTitle().equals(bookTitle) && !loan.isReturned()) {
                    loanToReturn = loan;
                    break;
                }
            }
            if (loanToReturn != null) {
                libraryManager.returnLoan(loanToReturn);
                updateLoanTable();
                updateComboBoxes();
            }
        }
    }
}