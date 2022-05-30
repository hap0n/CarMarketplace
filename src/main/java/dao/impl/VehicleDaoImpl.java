package dao.impl;

import connection.Query;
import dao.inte.VehicleDao;
import entities.Vehicle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class VehicleDaoImpl implements VehicleDao {
    private final Connection conn;

    public VehicleDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<Vehicle> get(Long id) {
        String query = String.format("SELECT * FROM %s WHERE id=%s", Vehicle.tableName, id);
        ResultSet rs = Query.executeQuery(query, conn);
        return Vehicle.fromRow(rs);
    }

    @Override
    public List<Vehicle> getAll() {
        String query = String.format("SELECT * FROM %s", Vehicle.tableName);
        ResultSet rs = Query.executeQuery(query, conn);
        return Vehicle.fromRows(rs);
    }

    @Override
    public Long save(Vehicle vehicle) {
        String query = String.format(
                "INSERT INTO %s (make, model, year, mileage) VALUES ('%s', '%s', '%s', '%s') RETURNING id",
                Vehicle.tableName,
                vehicle.getMake(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.getMileage()
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
    public void update(Vehicle vehicle) {
        String query = String.format(
                "UPDATE %s SET make = '%s', model = '%s', year = '%s', mileage = '%s' WHERE id = %s",
                Vehicle.tableName,
                vehicle.getMake(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.getMileage(),
                vehicle.getId()
        );
        Query.executeUpdate(query, conn);
    }

    @Override
    public void delete(Long id) {
        String query = String.format("DELETE FROM %s WHERE id=%s", Vehicle.tableName, id);
        Query.executeUpdate(query, conn);
    }
}
