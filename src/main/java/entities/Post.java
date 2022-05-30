package entities;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class Post {
    public static final String tableName = "posts";

    private Long id;
    private String title;
    private Long vehicleId;
    private String description;

    public static Post fromResultSet(ResultSet rs) throws SQLException {
        Post post = new Post();

        post.setId(rs.getLong("id"));
        post.setTitle(rs.getString("title"));
        post.setVehicleId(rs.getLong("vehicle_id"));
        post.setDescription(rs.getString("description"));

        return post;
    }

    public static Optional<Post> fromRow(ResultSet rs) {
        Optional<Post> maybeObject = Optional.empty();
        try {
            if (rs.next()) {
                maybeObject = Optional.of(Post.fromResultSet(rs));
            }
        } catch (SQLException ignored) {
        }
        return maybeObject;
    }

    public static ArrayList<Post> fromRows(ResultSet rs) {
        ArrayList<Post> entities = new ArrayList<>();

        try {
            if (!rs.next()) {
                return entities;
            }

            do {
                entities.add(Post.fromResultSet(rs));
            } while (rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entities;
    }
}
