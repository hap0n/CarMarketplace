import connection.ConnectionPool;
import dao.DaoFactory;
import dao.inte.UserDao;


import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();


        Connection conn1 = pool.getConnection();
        UserDao userDao = DaoFactory.getUserDao(conn1);
//        User user = new User();
//        user.setUsername("max");
//        user.setPwdHash("qwerty");
//        userDao.save(user);
        userDao.delete("username");
        System.out.println(userDao.get("max"));
//        CourseDao courseDao = DaoFactory.getCourseDao(conn);
//        Course course = new Course();
//        course.setLevel(CourseLevel.B2);
//        course.setTitle("Course b2 english");
//        course.setDescription("description");
//        courseDao.save(course);

    }
}
