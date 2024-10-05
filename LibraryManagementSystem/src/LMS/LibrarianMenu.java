package LMS;

import javax.swing.*;

import net.proteanit.sql.DbUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class LibrarianMenu extends JFrame {

    DataBaseManagement db = new DataBaseManagement();

    private final Color backgroundColor = Color.BLACK;
    private final Color foregroundColor = Color.WHITE;

    public LibrarianMenu() {
        setTitle("Library Management");
        setSize(500, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(foregroundColor);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(foregroundColor);

        JButton createButton = new JButton("Create/Reset");
        createButton.setBackground(backgroundColor);
        createButton.setForeground(foregroundColor);
        createButton.setPreferredSize(new Dimension(200, 70));
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                db.create();
                JOptionPane.showMessageDialog(null, "Database Created/Reset");
            }
        });

        JButton viewBooksButton = new JButton("View Books");
        viewBooksButton.setBackground(backgroundColor);
        viewBooksButton.setForeground(foregroundColor);
        viewBooksButton.setPreferredSize(new Dimension(200, 70));
        viewBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("Books List");
                Connection connection = db.connect();
                String sql = "select * from books";
                try {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate("USE librarymanagementsystem");
                    ResultSet rs = stmt.executeQuery(sql);
                    JTable bookList = new JTable();
                    bookList.setModel(DbUtils.resultSetToTableModel(rs));
                    JScrollPane scrollPane = new JScrollPane(bookList);
                    f.add(scrollPane);
                    f.setSize(800, 400);
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton viewUsersButton = new JButton("View Users");
        viewUsersButton.setBackground(backgroundColor);
        viewUsersButton.setForeground(foregroundColor);
        viewUsersButton.setPreferredSize(new Dimension(200, 70));
        viewUsersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("Users List");
                Connection connection = db.connect();
                String sql = "select * from users";
                try {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate("USE librarymanagementsystem");
                    ResultSet rs = stmt.executeQuery(sql);
                    JTable userList = new JTable();
                    userList.setModel(DbUtils.resultSetToTableModel(rs));
                    JScrollPane scrollPane = new JScrollPane(userList);
                    f.add(scrollPane);
                    f.setSize(800, 400);
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton viewIssuedBooksButton = new JButton("View Issued Books");
        viewIssuedBooksButton.setBackground(backgroundColor);
        viewIssuedBooksButton.setForeground(foregroundColor);
        viewIssuedBooksButton.setPreferredSize(new Dimension(200, 70));
        viewIssuedBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("Issued Books List");
                Connection connection = db.connect();
                String sql = "select * from issued";
                try {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate("USE librarymanagementsystem");
                    ResultSet rs = stmt.executeQuery(sql);
                    JTable bookList = new JTable();
                    bookList.setModel(DbUtils.resultSetToTableModel(rs));

                    JScrollPane scrollPane = new JScrollPane(bookList);

                    f.add(scrollPane);
                    f.setSize(800, 400);
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                } catch (SQLException e2) {
                    JOptionPane.showMessageDialog(null, e2);
                }
            }
        });

        JButton addUserButton = new JButton("Add User");
        addUserButton.setBackground(backgroundColor);
        addUserButton.setForeground(foregroundColor);
        addUserButton.setPreferredSize(new Dimension(200, 70));
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame g = new JFrame("Enter User Details");
                g.setBackground(foregroundColor);

                JLabel username, password, email;
                username = new JLabel("Username");
                username.setBounds(30, 15, 100, 30);

                password = new JLabel("Password");
                password.setBounds(30, 50, 100, 30);

                email = new JLabel("Email");
                email.setBounds(30, 85, 100, 30);

                JTextField usernameText = new JTextField();
                usernameText.setBounds(110, 15, 200, 30);

                JPasswordField passwordText = new JPasswordField();
                passwordText.setBounds(110, 50, 200, 30);

                JTextField emailText = new JTextField();
                emailText.setBounds(110, 85, 200, 30);

                JRadioButton a = new JRadioButton("Admin");
                a.setBounds(55, 120, 200, 30);

                JRadioButton u = new JRadioButton("User");
                u.setBounds(130, 120, 200, 30);

                ButtonGroup bg = new ButtonGroup();
                bg.add(a);
                bg.add(u);

                JButton createButton = new JButton("Create");
                createButton.setBackground(backgroundColor);
                createButton.setForeground(foregroundColor);
                createButton.setBounds(130, 170, 80, 25);
                createButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        String username = usernameText.getText();
                        String password = passwordText.getText();
                        String email = emailText.getText();

                        Boolean admin = false;

                        if (a.isSelected()) {
                            admin = true;
                        }

                        Connection connection = db.connect();

                        try {
                            Statement stmt = connection.createStatement();
                            stmt.executeUpdate("USE librarymanagementsystem");
                            stmt.executeUpdate("INSERT INTO USERS(USERNAME,PASSWORD,ADMIN,EMAIL) VALUES ('" + username + "','" + password + "'," + admin + ",'" + email + "')");
                            JOptionPane.showMessageDialog(null, "User added!");
                            g.dispose();
                        } catch (SQLException e3) {
                            JOptionPane.showMessageDialog(null, e3);
                        }
                    }
                });
                g.add(createButton);
                g.add(a);
                g.add(u);
                g.add(username);
                g.add(password);
                g.add(email);
                g.add(usernameText);
                g.add(passwordText);
                g.add(emailText);
                g.setSize(350, 250);
                g.setLayout(null);
                g.setVisible(true);
                g.setLocationRelativeTo(null);
            }
        });

        JButton addBookButton = new JButton("Add Book");
        addBookButton.setBackground(backgroundColor);
        addBookButton.setForeground(foregroundColor);
        addBookButton.setPreferredSize(new Dimension(200, 70));
        addBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame g = new JFrame("Enter Book Details");
                g.setBackground(foregroundColor);

                JLabel l1, l2, l3;
                l1 = new JLabel("Book Name");
                l1.setBounds(30, 15, 100, 30);

                l2 = new JLabel("Genre");
                l2.setBounds(30, 53, 100, 30);

                l3 = new JLabel("Price");
                l3.setBounds(30, 90, 100, 30);

                JTextField bookName = new JTextField();
                bookName.setBounds(110, 15, 200, 30);

                JTextField bookGenre = new JTextField();
                bookGenre.setBounds(110, 53, 200, 30);

                JTextField bookPrice = new JTextField();
                bookPrice.setBounds(110, 90, 200, 30);

                JButton createButton = new JButton("Submit");
                createButton.setBackground(backgroundColor);
                createButton.setForeground(foregroundColor);
                createButton.setBounds(130, 130, 80, 25);
                createButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String bname = bookName.getText();
                        String genre = bookGenre.getText();
                        String price = bookPrice.getText();

                        int priceInt = Integer.parseInt(price);

                        Connection connection = db.connect();

                        try {
                            Statement stmt = connection.createStatement();
                            stmt.executeUpdate("USE librarymanagementsystem");
                            stmt.executeUpdate("INSERT INTO BOOKS(BNAME,GENRE,PRICE) VALUES ('" + bname + "','" + genre + "'," + priceInt + ")");
                            JOptionPane.showMessageDialog(null, "Book added!");
                            g.dispose();

                        } catch (SQLException e4) {
                            JOptionPane.showMessageDialog(null, e4);
                        }
                    }
                });
                g.add(l3);
                g.add(createButton);
                g.add(l1);
                g.add(l2);
                g.add(bookName);
                g.add(bookGenre);
                g.add(bookPrice);
                g.setSize(350, 200);
                g.setLayout(null);
                g.setVisible(true);
                g.setLocationRelativeTo(null);
            }
        });

        JButton issueBookButton = new JButton("Issue Book");
        issueBookButton.setBackground(backgroundColor);
        issueBookButton.setForeground(foregroundColor);
        issueBookButton.setPreferredSize(new Dimension(200, 70));
        issueBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame g = new JFrame("Enter Details");
                g.setBackground(foregroundColor);

                JLabel l1, l2, l3;
                l1 = new JLabel("Book Name");
                l1.setBounds(30, 15, 100, 30);

                l2 = new JLabel("User ID(UID)");
                l2.setBounds(30, 53, 100, 30);

                l3 = new JLabel("Issue Date(DD-MM-YYYY)");
                l3.setBounds(30, 90, 150, 30);

                JTextField bookName = new JTextField();
                bookName.setBounds(180, 15, 200, 30);

                JTextField userID = new JTextField();
                userID.setBounds(180, 53, 200, 30);

                JTextField issueDate = new JTextField();
                issueDate.setBounds(180, 90, 200, 30);

                JButton createButton = new JButton("Submit");
                createButton.setBackground(backgroundColor);
                createButton.setForeground(foregroundColor);
                createButton.setBounds(130, 170, 80, 25);
                createButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String bname = bookName.getText();
                        String uid = userID.getText();
                        String issueDateStr = issueDate.getText();

                        Connection connection = db.connect();

                        try {
                            PreparedStatement pstmt = connection.prepareStatement("SELECT BID, BNAME, GENRE, PRICE FROM books WHERE BNAME = ?");
                            pstmt.setString(1, bname);
                            ResultSet rs = pstmt.executeQuery();
                            if (rs.next()) {
                                String bid = rs.getString("BID");
                                String genre = rs.getString("GENRE");
                                String bookName = rs.getString("BNAME");
                                int price = rs.getInt("PRICE");

                                PreparedStatement insertStmt = connection.prepareStatement("INSERT INTO ISSUED(UID, BNAME, GENRE, ISSUED_DATE, PRICE) VALUES (?, ?, ?, ?, ?)");
                                insertStmt.setString(1, uid);
                                insertStmt.setString(2, bookName);
                                insertStmt.setString(3, genre);
                                insertStmt.setString(4, issueDateStr);
                                insertStmt.setInt(5, price);
                                insertStmt.executeUpdate();

                                PreparedStatement deleteStmt = connection.prepareStatement("DELETE FROM books WHERE BID = ?");
                                deleteStmt.setString(1, bid);
                                deleteStmt.executeUpdate();

                                JOptionPane.showMessageDialog(null, "Book Issued!");
                                g.dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Book not found!");
                            }
                        } catch (SQLException e1) {
                            JOptionPane.showMessageDialog(null, e1);
                        }
                    }
                });
                g.add(l1);
                g.add(l2);
                g.add(l3);
                g.add(createButton);
                g.add(bookName);
                g.add(userID);
                g.add(issueDate);
                g.setSize(400, 250);
                g.setLayout(null);
                g.setVisible(true);
                g.setLocationRelativeTo(null);
            }
        });

        JButton returnBookButton = new JButton("Return Book");
        returnBookButton.setBackground(backgroundColor);
        returnBookButton.setForeground(foregroundColor);
        returnBookButton.setPreferredSize(new Dimension(200, 70));
        returnBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame g = new JFrame("Enter Details");
                g.setBackground(foregroundColor);

                JLabel l1, l2;
                l1 = new JLabel("Issue ID(IID)");
                l1.setBounds(30, 15, 100, 30);

                l2 = new JLabel("Return Date(DD-MM-YYYY)");
                l2.setBounds(30, 50, 150, 30);

                JTextField F_iid = new JTextField();
                F_iid.setBounds(110, 15, 200, 30);

                JTextField F_return = new JTextField();
                F_return.setBounds(180, 50, 130, 30);

                JButton returnButton = new JButton("Return");
                returnButton.setBackground(backgroundColor);
                returnButton.setForeground(foregroundColor);
                returnButton.setBounds(130, 170, 80, 25);
                returnButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String iid = F_iid.getText();
                        String return_date = F_return.getText();

                        Connection connection = db.connect();
                        ResultSet rs = null;

                        try {
                            Statement stmt = connection.createStatement();
                            stmt.executeUpdate("USE librarymanagementsystem");
                            String issuedDate = null;
                            String bookName = null;
                            String genre = null;
                            double price = 0;

                            rs = stmt.executeQuery("SELECT ISSUED_DATE, BNAME, GENRE, PRICE FROM ISSUED WHERE IID=" + iid);
                            if (rs.next()) {
                                issuedDate = rs.getString("ISSUED_DATE");
                                bookName = rs.getString("BNAME");
                                genre = rs.getString("GENRE");
                                price = rs.getDouble("PRICE");

                                stmt.executeUpdate("UPDATE ISSUED SET RETURN_DATE='" + return_date + "' WHERE IID=" + iid);

                                PreparedStatement insertStmt = connection.prepareStatement("INSERT INTO books(BNAME, GENRE, PRICE) VALUES (?, ?, ?)");
                                insertStmt.setString(1, bookName);
                                insertStmt.setString(2, genre);
                                insertStmt.setDouble(3, price);
                                insertStmt.executeUpdate();

                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                try {
                                    java.util.Date issuedDateParsed = format.parse(issuedDate);
                                    java.util.Date returnDateParsed = format.parse(return_date);

                                    long differenceMillis = returnDateParsed.getTime() - issuedDateParsed.getTime();
                                    long daysDifference = TimeUnit.DAYS.convert(differenceMillis, TimeUnit.MILLISECONDS);

                                    if (daysDifference > 30) {
                                        double finePerDay = 5.0;
                                        double fineAmount = (daysDifference - 30) * finePerDay;
                                        JOptionPane.showMessageDialog(null, "Book Returned! Fine amount: " + fineAmount);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Book Returned!");
                                    }
                                    g.dispose();
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(null, "Error parsing dates!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid Issue ID!");
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        } finally {
                            try {
                                if (rs != null) {
                                    rs.close();
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
                g.add(l1);
                g.add(l2);
                g.add(returnButton);
                g.add(F_iid);
                g.add(F_return);
                g.setSize(350, 250);
                g.setLayout(null);
                g.setVisible(true);
                g.setLocationRelativeTo(null);
            }
        });

        JButton logoutButton = new JButton("Log Out");
        logoutButton.setBackground(backgroundColor);
        logoutButton.setForeground(foregroundColor);
        logoutButton.setPreferredSize(new Dimension(200, 70));
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(createButton);
        buttonPanel.add(viewBooksButton);
        buttonPanel.add(viewUsersButton);
        buttonPanel.add(returnBookButton);
        buttonPanel.add(issueBookButton);
        buttonPanel.add(addBookButton);
        buttonPanel.add(addUserButton);
        buttonPanel.add(viewIssuedBooksButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel);
    }

}
