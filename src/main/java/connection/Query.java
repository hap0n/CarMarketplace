package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {
    public static ResultSet executeQuery(String query, Connection conn) {
        PreparedStatement stmt;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(query);
            System.out.printf("Executing query: %s%n", query);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void executeUpdate(String query, Connection conn) {
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(query);
            System.out.printf("Executing query: %s%n", query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
