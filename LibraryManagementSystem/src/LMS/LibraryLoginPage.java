package LMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class LibraryLoginPage extends JFrame {

    private JComboBox<String> loginType;
    private JTextField usernameField,emailField;
    private JPasswordField passwordField;
    private Connection connection;

    public LibraryLoginPage(Connection connection) {
        this.connection = connection;
        
        setTitle("LOGIN");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridLayout(5, 1));
        mainPanel.setBackground(Color.WHITE);

        JPanel rolePanel = new JPanel(new FlowLayout());
        rolePanel.add(new JLabel("Role: "));
        String[] roles = {"Librarian", "Student"};
        loginType = new JComboBox<>(roles);
        loginType.setBackground(Color.BLACK);
        loginType.setForeground(Color.WHITE); 
        rolePanel.add(loginType);
        mainPanel.add(rolePanel);

        JPanel userPanel = new JPanel(new FlowLayout());
        userPanel.add(new JLabel("Username : "));
        usernameField = new JTextField(15);
        userPanel.add(usernameField);
        mainPanel.add(userPanel);
        
        JPanel emailPanel = new JPanel(new FlowLayout());
        emailPanel.add(new JLabel("Email : "));
        emailField = new JTextField(15);
        emailPanel.add(emailField);
        mainPanel.add(emailPanel);

        JPanel passwordPanel = new JPanel(new FlowLayout());
        passwordPanel.add(new JLabel("Password : "));
        passwordField = new JPasswordField(15);
        passwordPanel.add(passwordField);
        mainPanel.add(passwordPanel);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(20, 30));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String role = (String) loginType.getSelectedItem();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LibraryLoginPage.this, "Username/password cannot be empty.", "Empty Fields", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!username.matches("[a-zA-Z0-9]+")) {
                    JOptionPane.showMessageDialog(LibraryLoginPage.this, "Username must contain only alphanumeric characters.", "Invalid Username", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int userid = authenticateUser(username, password, role, email);
                String userID = ""+userid;

                if (userid != -1) { 
                    if (role.equals("Librarian")) {
                        LibrarianMenu l = new LibrarianMenu(); 
                        l.setVisible(true);
                        dispose();
                    } else {
                        UserMenu u = new UserMenu(userID); 
                        u.setVisible(true);
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(LibraryLoginPage.this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        mainPanel.add(loginButton);
        add(mainPanel);
    }

    private int authenticateUser(String username, String password, String role, String email) {
        try {
            boolean isAdmin = role.equalsIgnoreCase("Librarian");

            String query = "SELECT UID FROM USERS WHERE USERNAME = ? AND PASSWORD = ? AND ADMIN = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setBoolean(3, isAdmin);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("UID");
            } else {
                return -1; 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1; 
        }
    }



}