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
public class Vehicle {
    public static final String tableName = "vehicles";

    private Long id;
    private String make;
    private String model;
    private int year;
    private int mileage;

    public static Vehicle fromResultSet(ResultSet rs) throws SQLException {
        Vehicle vehicle = new Vehicle();

        vehicle.setId(rs.getLong("id"));
        vehicle.setMake(rs.getString("make"));
        vehicle.setModel(rs.getString("model"));
        vehicle.setYear(rs.getInt("year"));
        vehicle.setMileage(rs.getInt("mileage"));

        return vehicle;
    }

    public static Optional<Vehicle> fromRow(ResultSet rs) {
        Optional<Vehicle> maybeObject = Optional.empty();
        try {
            if (rs.next()) {
                maybeObject = Optional.of(Vehicle.fromResultSet(rs));
            }
        } catch (SQLException ignored) {
        }
        return maybeObject;
    }

    public static ArrayList<Vehicle> fromRows(ResultSet rs) {
        ArrayList<Vehicle> entities = new ArrayList<>();

        try {
            if (!rs.next()) {
                return entities;
            }

            do {
                entities.add(Vehicle.fromResultSet(rs));
            } while (rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entities;
    }
}
