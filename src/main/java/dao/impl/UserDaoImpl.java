package dao.impl;

import connection.Query;
import dao.inte.UserDao;
import entities.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final Connection conn;

    public UserDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<User> get(String id) {
        String query = String.format("SELECT * FROM %s WHERE username='%s'", User.tableName, id);
        ResultSet rs = Query.executeQuery(query, conn);
        return User.fromRow(rs);
    }

    @Override
    public List<User> getAll() {
        String query = String.format("SELECT * FROM %s", User.tableName);
        ResultSet rs = Query.executeQuery(query, conn);
        return User.fromRows(rs);
    }

    @Override
    public String save(User user) {
        String query = String.format(
                "INSERT INTO %s (username, pwd_hash) VALUES ('%s', '%s') RETURNING username",
                User.tableName,
                user.getUsername(),
                user.getPwdHash()
        );
        ResultSet rs = Query.executeQuery(query, conn);

        String retVal = null;
        try {
            rs.next();
            retVal = rs.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void update(User user) {
        String query = String.format(
                "UPDATE %s SET pwd_hash = '%s' WHERE username = '%s'",
                User.tableName,
                user.getPwdHash(),
                user.getUsername()
        );
        Query.executeUpdate(query, conn);
    }

    @Override
    public void delete(String id) {
        String query = String.format("DELETE FROM %s WHERE username='%s'", User.tableName, id);
        Query.executeUpdate(query, conn);
    }
}
