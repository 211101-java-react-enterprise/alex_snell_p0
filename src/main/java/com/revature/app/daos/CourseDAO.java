package com.revature.app.daos;

import com.revature.app.models.Course;
import com.revature.app.models.User;
import com.revature.app.util.collections.LinkedList;
import com.revature.app.util.collections.List;
import com.revature.app.util.datasource.ConnectionFactory;

import java.sql.*;
import java.util.UUID;

public class CourseDAO implements EntityDAO<Course> {

    public List<Course> findAllCourses() {
        List<Course> courses = new LinkedList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM courses";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                User courseAdmin = new User();
                course.setId(rs.getString("course_id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setProgram(rs.getString("program"));
                course.setLevel(rs.getString("level"));
                courseAdmin.setId(rs.getString("creator_id"));
                course.setCreator(courseAdmin);
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public boolean findRegistrationById(String userId, String courseId) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * " +
                    "from registrations " +
                    "where user_id = ? " +
                    "and course_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, courseId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Course> findRegistrationsByUserId(String userId) {

        List<Course> courses = new LinkedList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "select c.* " +
                    "from courses c " +
                    "join registrations r on c.course_id = r.course_id " +
                    "join users u on u.user_id = r.user_id " +
                    "and u.user_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Course course = new Course();
                User courseAdmin = new User();
                course.setId(rs.getString("course_id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setProgram(rs.getString("program"));
                course.setLevel(rs.getString("level"));
                courseAdmin.setId(rs.getString("creator_id"));
                course.setCreator(courseAdmin);
                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;

    }

    public List<Course> findRegistrationByNotUserId(String userId) {

        List<Course> courses = new LinkedList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "select c.* " +
                    "from courses c " +
                    "outer join registrations r on c.course_id = r.course_id " +
                    "join users u on u.user_id = r.user_id " +
                    "and u.user_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Course course = new Course();
                User courseAdmin = new User();
                course.setId(rs.getString("course_id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setProgram(rs.getString("program"));
                course.setLevel(rs.getString("level"));
                courseAdmin.setId(rs.getString("creator_id"));
                course.setCreator(courseAdmin);
                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;

    }

    public List<Course> findCoursesByCreatorId(String creatorId) {

        List<Course> courses = new LinkedList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "select * " +
                    "from courses c " +
                    "join users u " +
                    "on c.creator_id = u.user_id " +
                    "where u.user_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, creatorId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Course course = new Course();
                User courseAdmin = new User();
                course.setId(rs.getString("course_id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setProgram(rs.getString("program"));
                course.setLevel(rs.getString("level"));
                courseAdmin.setId(rs.getString("user_id"));
                courseAdmin.setFirstName(rs.getString("first_name"));
                courseAdmin.setLastName(rs.getString("last_name"));
                courseAdmin.setEmail(rs.getString("email"));
                course.setCreator(courseAdmin);
                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;

    }

    @Override
    public Course save(Course newCourse) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            newCourse.setId(UUID.randomUUID().toString());

            String sql = "insert into courses (course_id, program, level, name, description, creator_id) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newCourse.getId());
            pstmt.setString(2, newCourse.getProgram());
            pstmt.setString(3, newCourse.getLevel());
            pstmt.setString(4, newCourse.getName());
            pstmt.setString(5, newCourse.getDescription());
            pstmt.setString(6, newCourse.getCreator().getId());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {
                return newCourse;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }


    @Override
    public Course findById(String id) {
        return null;
    }


    @Override
    public void deleteById(String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql1 = "delete from registrations " +
                    "where course_id = ?";
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setString(1, id);
            pstmt1.executeUpdate();

            String sql2 = "delete from courses " +
                    "where course_id = ?";
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1, id);
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Course update(Course updatedCourse) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {


            String sql = "UPDATE courses SET program = ?, level = ?, name = ?, description = ? " +
                    "WHERE course_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, updatedCourse.getProgram());
            pstmt.setString(2, updatedCourse.getLevel());
            pstmt.setString(3, updatedCourse.getName());
            pstmt.setString(4, updatedCourse.getDescription());
            pstmt.setString(5, updatedCourse.getId());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {
                return updatedCourse;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteRegistrationById(String userId, String courseId) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "delete from registrations " +
                    "where user_id = ? " +
                    "and course_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, courseId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createRegistrationById(String userId, String courseId) {
        if (!findRegistrationById(userId, courseId)) {
            try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
                String sql = "insert into registrations (user_id, course_id) values (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, userId);
                pstmt.setString(2, courseId);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
