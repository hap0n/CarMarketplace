package entities;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class User {
    public static final String tableName = "users";

    private String username;
    private String email;
    private String pwdHash;

    public static User fromResultSet(ResultSet rs) throws SQLException {
        User user = new User();

        user.setUsername(rs.getString("username"));
        user.setPwdHash(rs.getString("pwd_hash"));

        return user;
    }

    public static Optional<User> fromRow(ResultSet rs) {
        Optional<User> maybeObject = Optional.empty();
        try {
            if (rs.next()) {
                maybeObject = Optional.of(User.fromResultSet(rs));
            }
        } catch (SQLException ignored) {
        }
        return maybeObject;
    }

    public static ArrayList<User> fromRows(ResultSet rs) {
        ArrayList<User> entities = new ArrayList<>();

        try {
            if (!rs.next()) {
                return entities;
            }

            do {
                entities.add(User.fromResultSet(rs));
            } while (rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entities;
    }
}
