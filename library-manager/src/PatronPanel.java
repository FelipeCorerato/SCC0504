import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PatronPanel extends JPanel {
    private LibraryManager libraryManager;
    private LoanPanel loanPanel;
    private DefaultListModel<Patron> patronListModel;
    private JList<Patron> patronList;
    private JTextField nameField, contactInfoField;
    private JButton addButton, editButton, deleteButton, clearButton;

    public PatronPanel(LibraryManager libraryManager, LoanPanel loanPanel) {
        this.libraryManager = libraryManager;
        this.loanPanel = loanPanel;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Lista de patronos
        patronListModel = new DefaultListModel<>();
        patronList = new JList<>(patronListModel);
        JScrollPane scrollPane = new JScrollPane(patronList);
        add(scrollPane, BorderLayout.CENTER);

        // Formulário de entrada
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Contact Info:"), gbc);

        gbc.gridx = 1;
        contactInfoField = new JTextField(20);
        formPanel.add(contactInfoField, gbc);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Patron");
        addButton.addActionListener(e -> addPatron());
        buttonPanel.add(addButton);

        editButton = new JButton("Edit Patron");
        editButton.addActionListener(e -> editPatron());
        buttonPanel.add(editButton);

        deleteButton = new JButton("Delete Patron");
        deleteButton.addActionListener(e -> deletePatron());
        buttonPanel.add(deleteButton);

        clearButton = new JButton("Clear Selection");
        clearButton.addActionListener(e -> clearSelection());
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.SOUTH);

        updatePatronList();

        patronList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Patron selectedPatron = patronList.getSelectedValue();
                if (selectedPatron != null) {
                    nameField.setText(selectedPatron.getName());
                    contactInfoField.setText(selectedPatron.getContactInfo());
                }
            }
        });
    }

    public void updatePatronList() {
        patronListModel.clear();
        List<Patron> patrons = libraryManager.searchPatrons("");
        for (Patron patron : patrons) {
            patronListModel.addElement(patron);
        }
        loanPanel.updateComboBoxes();
    }

    private void addPatron() {
        String name = nameField.getText();
        String contactInfo = contactInfoField.getText();

        Patron patron = new Patron(name, contactInfo);
        libraryManager.addPatron(patron);
        updatePatronList();
        clearForm();
    }

    private void editPatron() {
        Patron selectedPatron = patronList.getSelectedValue();
        if (selectedPatron != null) {
            String name = nameField.getText();
            String contactInfo = contactInfoField.getText();

            Patron newPatron = new Patron(name, contactInfo);
            libraryManager.editPatron(selectedPatron, newPatron);
            updatePatronList();
            clearForm();
        }
    }

    private void deletePatron() {
        Patron selectedPatron = patronList.getSelectedValue();
        if (selectedPatron != null) {
            libraryManager.deletePatron(selectedPatron);
            updatePatronList();
            clearForm();
        }
    }

    private void clearForm() {
        nameField.setText("");
        contactInfoField.setText("");
    }

    private void clearSelection() {
        patronList.clearSelection();
        clearForm();
    }
}
