import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LibraryApp extends JFrame {
    private LibraryManager libraryManager;
    private AuthManager authManager;
    private User loggedInUser;
    private BookPanel bookPanel;
    private PatronPanel patronPanel;
    private LoanPanel loanPanel;

    public LibraryApp() {
        setLookAndFeel();  // Chame este método antes de inicializar a UI
        libraryManager = new LibraryManager();
        authManager = new AuthManager();
        initializeUI();
        loadData();
    }

    private void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Se Nimbus não estiver disponível, use o padrão.
        }
    }

    private void initializeUI() {
        setTitle("Library System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Autenticação
        if (!authenticateUser()) {
            System.exit(0);
        }

        // Configurando o layout principal
        setLayout(new BorderLayout());

        // Painel de navegação
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new GridLayout(1, 6));

        JButton bookButton = new JButton("Books");
        JButton patronButton = new JButton("Patrons");
        JButton loanButton = new JButton("Loans");
        JButton addUserButton = new JButton("Add User");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        navigationPanel.add(bookButton);
        navigationPanel.add(patronButton);
        navigationPanel.add(loanButton);
        navigationPanel.add(addUserButton);
        navigationPanel.add(saveButton);
        navigationPanel.add(loadButton);

        add(navigationPanel, BorderLayout.NORTH);

        // Painéis de conteúdo
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());

        loanPanel = new LoanPanel(libraryManager);
        bookPanel = new BookPanel(libraryManager, loanPanel); // Passa a referência do LoanPanel para o BookPanel
        patronPanel = new PatronPanel(libraryManager, loanPanel); // Passa a referência do LoanPanel para o PatronPanel

        contentPanel.add(bookPanel, "Books");
        contentPanel.add(patronPanel, "Patrons");
        contentPanel.add(loanPanel, "Loans");

        add(contentPanel, BorderLayout.CENTER);

        // Event listeners para navegação
        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();

        bookButton.addActionListener(e -> cardLayout.show(contentPanel, "Books"));
        patronButton.addActionListener(e -> cardLayout.show(contentPanel, "Patrons"));
        loanButton.addActionListener(e -> cardLayout.show(contentPanel, "Loans"));

        addUserButton.addActionListener(e -> {
            if (loggedInUser != null && "admin".equals(loggedInUser.getRole())) {
                addUserInterface();
            } else {
                JOptionPane.showMessageDialog(this, "Only admin users can add new users.", "Access Denied", JOptionPane.WARNING_MESSAGE);
            }
        });

        saveButton.addActionListener(e -> saveData());
        loadButton.addActionListener(e -> loadData());
    }

    private boolean authenticateUser() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo ou título
        JLabel titleLabel = new JLabel("Library System Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(60, 60, 60));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 20, 5);
        loginPanel.add(titleLabel, gbc);

        // Campos de entrada
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        loginPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        JTextField usernameField = new JTextField(20);
        loginPanel.add(usernameField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        loginPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        loginPanel.add(passwordField, gbc);

        // Botão de login
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 20, 5);
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(60, 120, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginPanel.add(loginButton, gbc);

        // Adiciona ação ao botão de login
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            loggedInUser = authManager.authenticate(username, password);
            if (loggedInUser != null) {
                JOptionPane.showMessageDialog(this, "Login successful", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                loginPanel.getTopLevelAncestor().setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Exibe o painel de login em uma janela separada
        JDialog dialog = new JDialog(this, "Login", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(loginPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return loggedInUser != null;
    }

    private void addUserInterface() {
        JPanel addUserPanel = new JPanel(new GridLayout(4, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleComboBox = new JComboBox<>(new String[] { "librarian", "admin" });

        addUserPanel.add(new JLabel("New Username:"));
        addUserPanel.add(usernameField);
        addUserPanel.add(new JLabel("New Password:"));
        addUserPanel.add(passwordField);
        addUserPanel.add(new JLabel("Role:"));
        addUserPanel.add(roleComboBox);

        int result = JOptionPane.showConfirmDialog(this, addUserPanel, "Add User", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            authManager.addUser(username, password, role);
            JOptionPane.showMessageDialog(this, "User added successfully");
        }
    }

    private void saveData() {
        try {
            libraryManager.saveData();
            JOptionPane.showMessageDialog(this, "Data saved successfully");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() {
        try {
            libraryManager.loadData();
            bookPanel.updateBookList();
            patronPanel.updatePatronList();
            loanPanel.updateLoanList();
            loanPanel.updateComboBoxes();
            JOptionPane.showMessageDialog(this, "Data loaded successfully");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryApp app = new LibraryApp();
            app.setVisible(true);
        });
    }
}
