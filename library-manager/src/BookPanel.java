import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BookPanel extends JPanel {
    private LibraryManager libraryManager;
    private LoanPanel loanPanel;
    private DefaultListModel<Book> bookListModel;
    private JList<Book> bookList;
    private JTextField titleField, authorField, isbnField, categoryField;
    private JButton addButton, editButton, deleteButton, clearButton;

    public BookPanel(LibraryManager libraryManager, LoanPanel loanPanel) {
        this.libraryManager = libraryManager;
        this.loanPanel = loanPanel;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Lista de livros
        bookListModel = new DefaultListModel<>();
        bookList = new JList<>(bookListModel);
        JScrollPane scrollPane = new JScrollPane(bookList);
        add(scrollPane, BorderLayout.CENTER);

        // Formulário de entrada
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Title:"), gbc);

        gbc.gridx = 1;
        titleField = new JTextField(20);
        formPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Author:"), gbc);

        gbc.gridx = 1;
        authorField = new JTextField(20);
        formPanel.add(authorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("ISBN:"), gbc);

        gbc.gridx = 1;
        isbnField = new JTextField(20);
        formPanel.add(isbnField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Category:"), gbc);

        gbc.gridx = 1;
        categoryField = new JTextField(20);
        formPanel.add(categoryField, gbc);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Book");
        addButton.addActionListener(e -> addBook());
        buttonPanel.add(addButton);

        editButton = new JButton("Edit Book");
        editButton.addActionListener(e -> editBook());
        buttonPanel.add(editButton);

        deleteButton = new JButton("Delete Book");
        deleteButton.addActionListener(e -> deleteBook());
        buttonPanel.add(deleteButton);

        clearButton = new JButton("Clear Selection");
        clearButton.addActionListener(e -> clearSelection());
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.SOUTH);

        updateBookList();

        bookList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Book selectedBook = bookList.getSelectedValue();
                if (selectedBook != null) {
                    titleField.setText(selectedBook.getTitle());
                    authorField.setText(selectedBook.getAuthor());
                    isbnField.setText(selectedBook.getIsbn());
                    categoryField.setText(selectedBook.getCategory());
                }
            }
        });
    }

    public void updateBookList() {
        bookListModel.clear();
        List<Book> books = libraryManager.searchBooks("");
        for (Book book : books) {
            bookListModel.addElement(book);
        }
        loanPanel.updateComboBoxes();
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        String category = categoryField.getText();

        Book book = new Book(title, author, isbn, category);
        libraryManager.addBook(book);
        updateBookList();
        clearForm();
    }

    private void editBook() {
        Book selectedBook = bookList.getSelectedValue();
        if (selectedBook != null) {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            String category = categoryField.getText();

            Book newBook = new Book(title, author, isbn, category);
            libraryManager.editBook(selectedBook, newBook);
            updateBookList();
            clearForm();
        }
    }

    private void deleteBook() {
        Book selectedBook = bookList.getSelectedValue();
        if (selectedBook != null) {
            libraryManager.deleteBook(selectedBook);
            updateBookList();
            clearForm();
        }
    }

    private void clearForm() {
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
        categoryField.setText("");
    }

    private void clearSelection() {
        bookList.clearSelection();
        clearForm();
    }
}
