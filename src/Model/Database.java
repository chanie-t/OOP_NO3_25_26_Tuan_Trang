package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private String user = "root";
    private String pass = "";
    private String url = "jdbc:mysql://localhost:3306/hospital";

    private Connection connection;
    private Statement statement;

    public Database() {
        try {
            connection = DriverManager.getConnection(url, user, pass);

            statement = connection.createStatement();

            System.out.println("Ket noi Database thanh cong");

        } catch (SQLException e) {
            System.out.println("Ket noi Database THAT BAI!");
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        return this.statement;
    }

    public void close() {
        try {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}