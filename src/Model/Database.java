package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private String user = "user";
    private String pass = "pass";
    private String url = "jbdc:mysql://localhost/hospital";
    private Statement statement;

    public Database() {
        try {
            Connection connection = DriverManager.getConnection(url);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                        ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        return statement;
    }
}
