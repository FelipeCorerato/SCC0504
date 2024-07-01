package main.java.view;

import main.java.model.Book;
import main.java.model.LibraryManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookPanel extends JPanel {
    private LibraryManager libraryManager;
    private DefaultTableModel bookTableModel;
    private JTable bookTable;
    private LoanPanel loanPanel;

    public BookPanel(LibraryManager libraryManager, LoanPanel loanPanel) {
        this.libraryManager = libraryManager;
        this.loanPanel = loanPanel;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Tabela de livros
        String[] columnNames = {"Title", "Author", "ISBN", "Category", "Available"};
        bookTableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(bookTableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        // Formulário de entrada
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField titleField = new JTextField(20);
        JTextField authorField = new JTextField(20);
        JTextField isbnField = new JTextField(20);
        JTextField categoryField = new JTextField(20);

        // Ícones de ajuda
        HelpTooltip titleHelp = new HelpTooltip("Enter the title of the book");
        HelpTooltip authorHelp = new HelpTooltip("Enter the author's name");
        HelpTooltip isbnHelp = new HelpTooltip("Enter the ISBN of the book (e.g., 9783161484100)");
        HelpTooltip categoryHelp = new HelpTooltip("Enter the category of the book (e.g., Fiction, Science)");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(titleField, gbc);
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        formPanel.add(titleHelp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(authorField, gbc);
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        formPanel.add(authorHelp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(isbnField, gbc);
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        formPanel.add(isbnHelp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(categoryField, gbc);
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        formPanel.add(categoryHelp, gbc);

        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            String category = categoryField.getText();

            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isbn.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "ISBN must contain only numbers", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Book book = new Book(title, author, isbn, category, true);
                libraryManager.addBook(book);
                updateBookTable();
                loanPanel.updateComboBoxes();
                saveData();  // Salva os dados após a alteração

                // Limpar os campos após adicionar o livro
                titleField.setText("");
                authorField.setText("");
                isbnField.setText("");
                categoryField.setText("");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        formPanel.add(addButton, gbc);

        JButton deleteButton = new JButton("Delete Book");
        deleteButton.addActionListener(e -> deleteBook());

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        formPanel.add(deleteButton, gbc);

        add(formPanel, BorderLayout.SOUTH);

        updateBookTable();
    }

    public void updateBookTable() {
        bookTableModel.setRowCount(0);
        List<Book> books = libraryManager.getBooks();

        for (Book book : books) {
            Object[] rowData = {
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.getCategory(),
                    book.isAvailable() ? "Yes" : "No"
            };
            bookTableModel.addRow(rowData);
        }
    }

    private void deleteBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow != -1) {
            String isbn = (String) bookTableModel.getValueAt(selectedRow, 2);
            Book bookToDelete = null;
            List<Book> books = libraryManager.getBooks();
            for (Book book : books) {
                if (book.getIsbn().equals(isbn)) {
                    bookToDelete = book;
                    break;
                }
            }
            if (bookToDelete != null) {
                libraryManager.deleteBook(bookToDelete);
                updateBookTable();
                loanPanel.updateComboBoxes();
                saveData();  // Salva os dados após a alteração
            }
        }
    }

    private void saveData() {
        libraryManager.saveData();
    }
}