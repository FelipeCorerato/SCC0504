import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoanPanel extends JPanel {
    private LibraryManager libraryManager;
    private DefaultListModel<Loan> loanListModel;
    private JList<Loan> loanList;
    private JComboBox<Book> bookComboBox;
    private JComboBox<Patron> patronComboBox;
    private JButton checkOutButton, checkInButton, clearButton;

    public LoanPanel(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Lista de empréstimos
        loanListModel = new DefaultListModel<>();
        loanList = new JList<>(loanListModel);
        JScrollPane scrollPane = new JScrollPane(loanList);
        add(scrollPane, BorderLayout.CENTER);

        // Formulário de entrada
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Book:"), gbc);

        gbc.gridx = 1;
        bookComboBox = new JComboBox<>();
        formPanel.add(bookComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Patron:"), gbc);

        gbc.gridx = 1;
        patronComboBox = new JComboBox<>();
        formPanel.add(patronComboBox, gbc);

        JPanel buttonPanel = new JPanel();
        checkOutButton = new JButton("Check Out Book");
        checkOutButton.addActionListener(e -> checkOutBook());
        buttonPanel.add(checkOutButton);

        checkInButton = new JButton("Check In Book");
        checkInButton.addActionListener(e -> checkInBook());
        buttonPanel.add(checkInButton);

        clearButton = new JButton("Clear Selection");
        clearButton.addActionListener(e -> clearSelection());
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.SOUTH);

        updateComboBoxes();
        updateLoanList();

        loanList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Loan selectedLoan = loanList.getSelectedValue();
                if (selectedLoan != null) {
                    bookComboBox.setSelectedItem(selectedLoan.getBook());
                    patronComboBox.setSelectedItem(selectedLoan.getPatron());
                }
            }
        });
    }

    public void updateLoanList() {
        loanListModel.clear();
        List<Loan> loans = libraryManager.getOverdueLoans();
        for (Loan loan : loans) {
            loanListModel.addElement(loan);
        }
    }

    public void updateComboBoxes() {
        bookComboBox.removeAllItems();
        patronComboBox.removeAllItems();

        List<Book> books = libraryManager.searchBooks("");
        for (Book book : books) {
            bookComboBox.addItem(book);
        }

        List<Patron> patrons = libraryManager.searchPatrons("");
        for (Patron patron : patrons) {
            patronComboBox.addItem(patron);
        }
    }

    private void checkOutBook() {
        Book book = (Book) bookComboBox.getSelectedItem();
        Patron patron = (Patron) patronComboBox.getSelectedItem();

        if (book != null && patron != null) {
            libraryManager.checkOutBook(book, patron);
            updateLoanList();
        }
    }

    private void checkInBook() {
        Loan selectedLoan = loanList.getSelectedValue();
        if (selectedLoan != null && !selectedLoan.isReturned()) {
            libraryManager.checkInBook(selectedLoan.getBook());
            updateLoanList();
        }
    }

    private void clearSelection() {
        loanList.clearSelection();
        bookComboBox.setSelectedItem(null);
        patronComboBox.setSelectedItem(null);
    }
}
