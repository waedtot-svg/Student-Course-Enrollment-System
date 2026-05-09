package dao;
//waed rabah zaqout

import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Enrollment;

public class EnrollmentDAO {

    public List<Enrollment> findAll() {
        List<Enrollment> list = new ArrayList<>();

        String sql = "SELECT * FROM enrollment";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);

            while (rs.next()) {
                Enrollment e = new Enrollment(
                        rs.getInt("enrollment_id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getString("enrollment_date")
                );

                list.add(e);
            }

        } catch (SQLException ex) {
            System.getLogger(EnrollmentDAO.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return list;
    }

    public boolean exists(int studentId, int courseId) {
        String sql = "SELECT * FROM enrollment WHERE student_id = ? AND course_id = ?";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException ex) {
            System.getLogger(EnrollmentDAO.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return false;
    }

    public boolean insertOne(Enrollment e) {
        String sql = "INSERT INTO enrollment(student_id, course_id, enrollment_date) "
                + "VALUES (?, ?, ?)";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, e.getStudentId());
            ps.setInt(2, e.getCourseId());
            ps.setString(3, e.getEnrollmentDate());

            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.getLogger(EnrollmentDAO.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return false;
    }

    public boolean updateOne(Enrollment e) {
        String sql = "UPDATE enrollment SET student_id = ?, course_id = ?, enrollment_date = ? "
                + "WHERE enrollment_id = ?";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, e.getStudentId());
            ps.setInt(2, e.getCourseId());
            ps.setString(3, e.getEnrollmentDate());
            ps.setInt(4, e.getEnrollmentId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            System.getLogger(EnrollmentDAO.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return false;
    }

    public boolean deleteOne(Enrollment e) {
        String sql = "DELETE FROM enrollment WHERE enrollment_id = ?";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, e.getEnrollmentId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            System.getLogger(EnrollmentDAO.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return false;
    }
}