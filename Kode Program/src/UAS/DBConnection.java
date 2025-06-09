package UAS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_sepeda";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void main(String[] args) {
        try {
            Connection conn = connect();
            if (conn != null) {
                System.out.println("Koneksi ke database berhasil!");
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
    }
}
