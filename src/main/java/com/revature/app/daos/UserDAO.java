package com.revature.app.daos;

import com.revature.app.util.datasource.ConnectionFactory;
import com.revature.app.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class UserDAO implements EntityDAO<User> {

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public User save(User newUser) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

            newUser.setId(UUID.randomUUID().toString());
            newUser.setRole("student");

            String sql = "insert into users (user_id, role, email, first_name, last_name, password) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newUser.getId());
            statement.setString(2, newUser.getRole());
            statement.setString(3, newUser.getEmail());
            statement.setString(4, newUser.getFirstName());
            statement.setString(5, newUser.getLastName());
            statement.setString(6, newUser.getPassword());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted != 0) {
                return newUser;
            }

        } catch (SQLException e) {
            // TODO log this and throw our own custom exception to be caught in the service layer
            e.printStackTrace();

        }

        return null;
    }

    @Override
    public void deleteById(String id) {
      // TODO complete method stub
    }

    @Override
    public User update(User updatedUser) {
        return null;
    }

    public User findByEmail(String email) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "select * from users where email = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getString("user_id"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPassword(rs.getString("password"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public User findByEmailAndPassword(String email, String password) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "select * from users where email = ? and password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getString("user_id"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPassword(rs.getString("password"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }
}
