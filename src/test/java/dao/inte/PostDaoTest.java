package dao.inte;

import connection.ConnectionPool;
import connection.Query;
import dao.DaoFactory;
import entities.Post;
import entities.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Optional;


class PostDaoTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        Query.executeUpdate("DELETE FROM posts", conn);
        Query.executeUpdate("DELETE FROM users", conn);
        Query.executeUpdate("DELETE FROM vehicles", conn);
        pool.closeConnection(conn);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        Query.executeUpdate("DELETE FROM posts", conn);
        Query.executeUpdate("DELETE FROM users", conn);
        Query.executeUpdate("DELETE FROM vehicles", conn);
        pool.closeConnection(conn);
    }

    @Test
    void end2end() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn1 = pool.getConnection();
        PostDao postDao = DaoFactory.getPostDao(conn1);
        Connection conn2 = pool.getConnection();
        VehicleDao vehicleDao = DaoFactory.getVehicleDao(conn2);

        Assertions.assertTrue(postDao.getAll().isEmpty());

        Vehicle vehicle = new Vehicle();
        vehicle.setMileage(20000);
        vehicle.setMake("mazda");
        vehicle.setModel("6");
        vehicle.setYear(2012);
        Long vehicleId = vehicleDao.save(vehicle);

        Optional<Vehicle> maybeVehicle = vehicleDao.get(vehicleId);
        Vehicle vehicleFromDB = maybeVehicle.get();

        Assertions.assertEquals(vehicleFromDB.getMake(), vehicle.getMake());
        Assertions.assertEquals(vehicleFromDB.getModel(), vehicle.getModel());
        Assertions.assertEquals(vehicleFromDB.getMileage(), vehicle.getMileage());
        Assertions.assertEquals(vehicleFromDB.getYear(), vehicle.getYear());

        Post post = new Post();
        post.setTitle("MAZDA 6 Red");
        post.setDescription("desc");
        post.setVehicleId(vehicleId);

        Long postId = postDao.save(post);

        Post postFromDB = postDao.get(postId).get();
        Assertions.assertEquals(postFromDB.getDescription(), post.getDescription());
        Assertions.assertEquals(postFromDB.getTitle(), post.getTitle());
        Assertions.assertEquals(postFromDB.getVehicleId(), post.getVehicleId());

        vehicleDao.delete(vehicleId);
        Assertions.assertTrue(vehicleDao.getAll().isEmpty());
        Assertions.assertTrue(postDao.getAll().isEmpty());
    }
}