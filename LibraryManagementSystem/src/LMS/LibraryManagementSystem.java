package LMS;

import java.awt.*;
import javax.swing.*;
import java.sql.*;


public class LibraryManagementSystem extends JFrame {
	
    public LibraryManagementSystem() {
        setTitle("Library Management System");
        setSize(900,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        
        ImageIcon icon = new ImageIcon("C:\\Users\\karth\\eclipse-workspace\\LibraryManagementSystem\\src\\LMS\\logo.png");
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(70, 70,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage); 

        JLabel imageLabel = new JLabel(icon);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(imageLabel, gbc);
        
        JLabel heading = new JLabel("LIBRARY MANAGEMENT SYSTEM");
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 40));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        centerPanel.add(heading, gbc);

        
        JLabel label = new JLabel("Login/SignUp :");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 1;
        gbc.gridy = 3;
        centerPanel.add(label, gbc);
        
        JButton existingUser = new JButton("Existing User - Login");
        existingUser.setBounds(50, 50, 200, 50);
        existingUser.setFont(new Font("Arial", Font.BOLD, 14));
        existingUser.setFocusPainted(false);
        existingUser.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        existingUser.setBackground(Color.BLACK); 
        existingUser.setForeground(Color.WHITE);
        existingUser.addActionListener(e -> {
            LibraryLoginPage loginPage = new LibraryLoginPage(connect());
            loginPage.setVisible(true);
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        
        centerPanel.add(existingUser, gbc);
        
        JButton newUser = new JButton("New User - SignUp");
        newUser.setBounds(50, 50, 200, 50);
        newUser.setFont(new Font("Arial", Font.BOLD, 14));
        newUser.setFocusPainted(false);
        newUser.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        newUser.setBackground(Color.BLACK); 
        newUser.setForeground(Color.WHITE);
        newUser.addActionListener(e -> {
            LibrarySignUpPage signUpPage = new LibrarySignUpPage(connect());
            signUpPage.setVisible(true);
        });
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        
        centerPanel.add(newUser, gbc);
        
        mainPanel.add(centerPanel,BorderLayout.CENTER);
        
        add(mainPanel);
    }

    public static Connection connect() {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/";
            String dbName = "LibraryManagementSystem";
            String username = "root";
            String password = "root";
            
            Connection con = DriverManager.getConnection(url, username, password);
            
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            
            con = DriverManager.getConnection(url + dbName, username, password);
            
            initializeDatabase(con);
            
            return con;
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    private static void initializeDatabase(Connection con) throws SQLException {
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS USERS(UID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, USERNAME VARCHAR(30), PASSWORD VARCHAR(30), ADMIN BOOLEAN, EMAIL VARCHAR(30))");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ISSUED(IID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, UID INT, BNAME VARCHAR(50), GENRE VARCHAR(20), RETURN_DATE VARCHAR(20), ISSUED_DATE VARCHAR(30), PRICE INT)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS BOOKS(BID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, BNAME VARCHAR(50), GENRE VARCHAR(20), PRICE INT)");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Connection connection = connect();
                if (connection != null) {
                    new LibraryManagementSystem().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to connect to the database. Exiting...", "Database Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
        });
    }
}
