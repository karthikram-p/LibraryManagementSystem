package LMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LibrarySignUpPage extends JFrame {

    private JComboBox<String> loginType;
    private JTextField usernameText, emailText;
    private JPasswordField passwordField;
    boolean admin;

    private Connection connection;

    public LibrarySignUpPage(Connection connection) {
        this.connection = connection;

        setTitle("Sign Up");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel logintype = new JLabel("Login Type:");
        logintype.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(logintype, gbc);

        String[] roles = {"Librarian", "Student"};
        loginType = new JComboBox<>(roles);
        loginType.setBackground(Color.BLACK);
        loginType.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(loginType, gbc);

        JLabel username = new JLabel("User Name:");
        username.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(username, gbc);

        usernameText = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(usernameText, gbc);

        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        centerPanel.add(password, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        centerPanel.add(passwordField, gbc);
        
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 4;
        centerPanel.add(emailLabel, gbc);

        emailText = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        centerPanel.add(emailText, gbc);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(Color.BLACK); 
        signUpButton.setForeground(Color.WHITE);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String logintype = (String) loginType.getSelectedItem();
                String username = usernameText.getText();
                String password = new String(passwordField.getPassword());
                String email = emailText.getText();
                
                if(logintype.equals("Librarian")) {
                	admin = true;
                }
                else {
                	admin = false;
                }
                
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LibrarySignUpPage.this, "Username/password cannot be empty.", "Empty Fields", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!username.matches("[a-zA-Z0-9]*")) {
                    JOptionPane.showMessageDialog(LibrarySignUpPage.this, "Username must contain only alphanumeric characters.", "Invalid Username", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(LibrarySignUpPage.this, "Invalid email format.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                try {
                    Statement stmt = connection.createStatement();
                    String insertQuery = "INSERT INTO USERS (USERNAME, PASSWORD, ADMIN, EMAIL) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    preparedStatement.setBoolean(3, admin);
                    preparedStatement.setString(4, email);

                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(LibrarySignUpPage.this, "User registered successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    LibraryLoginPage login = new LibraryLoginPage(connection);
                    login.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(LibrarySignUpPage.this, "Error registering user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    dispose();
                }
            }
        });
        bottomPanel.add(signUpButton, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}
