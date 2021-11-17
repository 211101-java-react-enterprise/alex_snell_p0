package com.revature.app.daos;

import com.revature.app.models.Course;
import com.revature.app.models.User;
import com.revature.app.util.collections.LinkedList;
import com.revature.app.util.collections.List;
import com.revature.app.util.datasource.ConnectionFactory;

import java.sql.*;
import java.util.UUID;

public class CourseDAO implements EntityDAO<Course> {


    public List<Course> findRegistrationByUserId(String userId) {

        List<Course> courses = new LinkedList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "select c.* " +
                    "from courses c " +
                    "join registrations r " +
                    "on r.course_id = c.course_id " +
                    "and r.user_id = user_id " +
                    "where user_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Course course = new Course();
                User courseAdmin = new User();
                course.setId(rs.getString("course_id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
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

    public List<Course> findAll() {

        List<Course> courses = new LinkedList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "select * from flashcards f join app_users u on f.creator_id = u.id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Course course = new Course();
                User courseAdmin = new User();
                course.setId(rs.getString("course_id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
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
    public Course findById(String id) {
        return null;
    }


    @Override
    public void deleteById(String id) {

    }

    @Override
    public Course update(Course updatedCourse) {
        return null;
    }

}
