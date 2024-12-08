package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final String dbName;
    private final String userName;
    private final String password;
    private final String host;
    private final String port;
    private Connection connection;

    public Database(final String dbName, final String userName, final String password, final String host, final String port) {
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public Connection getConnection() {
        try {
            // Periksa apakah koneksi sudah ditutup atau null
            if (connection == null || connection.isClosed()) {
                setup();  // Buat koneksi baru jika belum ada koneksi aktif
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to validate connection!", e);
        }
        return connection;
    }

    public void setup() {
        String mysqlConnUrlTemplate = "jdbc:mysql://%s:%s/%s";
        String url = String.format(mysqlConnUrlTemplate, host, port, dbName);
        try {
            Class.forName("com.mysql.jdbc.Driver");  // Pastikan driver MySQL modern digunakan
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Database connected!");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to connect to database!", e);
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection = null;  // Set koneksi ke null setelah ditutup
            }
        }
    }
}
