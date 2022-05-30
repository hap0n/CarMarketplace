package dao;

import dao.impl.VehicleDaoImpl;
import dao.impl.PostDaoImpl;
import dao.impl.UserDaoImpl;
import dao.inte.PostDao;
import dao.inte.UserDao;
import dao.inte.VehicleDao;

import java.sql.Connection;

public class DaoFactory {
    public static VehicleDao getVehicleDao(Connection conn) {
        return new VehicleDaoImpl(conn);
    }

    public static PostDao getPostDao(Connection conn) {
        return new PostDaoImpl(conn);
    }

    public static UserDao getUserDao(Connection conn) {
        return new UserDaoImpl(conn);
    }
}
