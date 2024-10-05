package LMS;

import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserMenu extends JFrame {
	
	DataBaseManagement db = new DataBaseManagement();

    private final String userID;
    private final Color backgroundColor = Color.BLACK;
    private final Color foregroundColor = Color.WHITE; 

    public UserMenu(String userID) {
        this.userID = userID;
        setTitle("User Menu");
        setSize(300, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(foregroundColor);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(foregroundColor);

        JButton viewAvailableBooksButton = new JButton("View Available Books");
        viewAvailableBooksButton.setBackground(backgroundColor); 
        viewAvailableBooksButton.setForeground(foregroundColor); 
        viewAvailableBooksButton.setPreferredSize(new Dimension(200, 70));
        viewAvailableBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("Available Books");
                f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                Connection connection = db.connect();
                String sql = "SELECT * FROM books";
                try {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate("USE librarymanagementsystem");
                    ResultSet rs = stmt.executeQuery(sql);
                    JTable bookList = new JTable();
                    bookList.setModel(DbUtils.resultSetToTableModel(rs));
                    JScrollPane scrollPane = new JScrollPane(bookList);
                    f.add(scrollPane);
                    f.setSize(600, 400);
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
                JFrame f = new JFrame("Issued Books");
                f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                Connection connection = db.connect();
                String sql = "SELECT BNAME, ISSUED_DATE, RETURN_DATE FROM issued WHERE UID = ?";
                try {
                    PreparedStatement pstmt = connection.prepareStatement(sql);
                    pstmt.setString(1, userID);
                    ResultSet rs = pstmt.executeQuery();
                    JTable issuedBooksTable = new JTable();
                    issuedBooksTable.setModel(DbUtils.resultSetToTableModel(rs));
                    JScrollPane scrollPane = new JScrollPane(issuedBooksTable);
                    f.add(scrollPane);
                    f.setSize(500, 300);
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        
        JButton logoutButton = new JButton("Log Out");
        logoutButton.setBackground(backgroundColor);
        logoutButton.setForeground(foregroundColor);
        logoutButton.setPreferredSize(new Dimension(200,70));
        logoutButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        buttonPanel.add(viewAvailableBooksButton);
        buttonPanel.add(viewIssuedBooksButton);
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.CENTER);
    }
}
