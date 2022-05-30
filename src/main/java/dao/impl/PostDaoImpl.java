package dao.impl;

import connection.Query;
import dao.inte.PostDao;
import entities.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostDaoImpl implements PostDao {
    private final Connection conn;

    public PostDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<Post> get(Long id) {
        String query = String.format("SELECT * FROM %s WHERE id=%s", Post.tableName, id);
        ResultSet rs = Query.executeQuery(query, conn);
        return Post.fromRow(rs);
    }

    @Override
    public List<Post> getAll() {
        String query = String.format("SELECT * FROM %s", Post.tableName);
        ResultSet rs = Query.executeQuery(query, conn);
        return Post.fromRows(rs);
    }

    @Override
    public Long save(Post courseModule) {
        String query = String.format(
                "INSERT INTO %s (title, vehicle_id, description) VALUES ('%s', '%s', '%s') RETURNING id",
                Post.tableName,
                courseModule.getTitle(),
                courseModule.getVehicleId(),
                courseModule.getDescription()
        );
        ResultSet rs = Query.executeQuery(query, conn);

        Long retVal = null;
        try {
            rs.next();
            retVal = rs.getLong("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void update(Post courseModule) {
        String query = String.format(
                "UPDATE %s SET title = '%s', vehicle_id = '%s', description = '%s' WHERE id = %s",
                Post.tableName,
                courseModule.getTitle(),
                courseModule.getVehicleId(),
                courseModule.getDescription(),
                courseModule.getId()
        );
        Query.executeUpdate(query, conn);
    }

    @Override
    public void delete(Long id) {
        String query = String.format("DELETE FROM %s WHERE id=%s", Post.tableName, id);
        Query.executeUpdate(query, conn);
    }
}
