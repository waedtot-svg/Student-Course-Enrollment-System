package dao;
//waed rabah zaqout

import config.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public List<Integer> getAllCoursesIds() {
        List<Integer> ids = new ArrayList<>();

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT course_id FROM courses";
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);

            while (rs.next()) {
                ids.add(rs.getInt("course_id"));
            }

        } catch (SQLException ex) {
            System.getLogger(CourseDAO.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return ids;
    }
}