package LMS;

import java.sql.*;

public class DataBaseManagement {

    public static Connection connect() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/LibraryManagementSystem", "root", "root");
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void create() {
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS LibraryManagementSystem");
            stmt.executeUpdate("USE LibraryManagementSystem");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS USERS (USERNAME VARCHAR(30), PASSWORD VARCHAR(30), ADMIN BOOLEAN, EMAIL VARCHAR(30))");

            stmt.executeUpdate("INSERT INTO USERS (USERNAME, PASSWORD, ADMIN, EMAIL) VALUES ('admin', 'admin', TRUE, 'admin@gmail.com')");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS BOOKS (BID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, BNAME VARCHAR(50), GENRE VARCHAR(20), PRICE INT)");

            stmt.executeUpdate("INSERT INTO BOOKS (BNAME, GENRE, PRICE) VALUES ('War and Peace', 'Mystery', 200), ('The Guest Book', 'Fiction', 300), ('The Perfect Murder', 'Mystery', 150), ('Accidental Presidents', 'Biography', 250), ('The Wicked King', 'Fiction', 350)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ISSUED (IID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, UID INT, BNAME VARCHAR(50), GENRE VARCHAR(20), RETURN_DATE VARCHAR(20), ISSUED_DATE VARCHAR(30), PRICE INT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
