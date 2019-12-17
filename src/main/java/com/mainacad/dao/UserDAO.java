package com.mainacad.dao;

import com.mainacad.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static User save(User user) {
        String sql = "INSERT INTO users " +
                "(login, password, first_name, last_name, e_mail, phone) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String sequenceSQL = "SELECT currval(pg_get_serial_sequence('users','id'))";

        int result = 0;
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql);
              PreparedStatement sequenceStatement =
                      connection.prepareStatement(sequenceSQL)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhone());
            result = preparedStatement.executeUpdate();

            if( result == 1 ) {
                ResultSet resultSet = sequenceStatement.executeQuery();
                while (resultSet.next()) {
                    Integer id = resultSet.getInt(1);
                    user.setId(id);
                    break;
                }
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<User> getAll() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql);
              ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while(resultSet.next()) {
                User user = new User (
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("e_mail"),
                        resultSet.getString("phone")
                );
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public static User getById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                User user = new User (
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("e_mail"),
                        resultSet.getString("phone")
                );
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User getByLoginAndPassword(String login, String password) {
        String sql = "SELECT * FROM users WHERE login = ? AND password = ?";
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql)
              ) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                User user = new User (
                  resultSet.getInt("id"),
                  resultSet.getString("login"),
                  resultSet.getString("password"),
                  resultSet.getString("first_name"),
                  resultSet.getString("last_name"),
                  resultSet.getString("e_mail"),
                  resultSet.getString("phone")
                );
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User update(User user) {
        String sql = "UPDATE users SET " +
                "login = ?, " +
                "password = ?, " +
                "first_name = ?, " +
                "last_name = ?, " +
                "e_mail = ?, " +
                "phone = ? " +
                "WHERE id = ?";
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setInt(7, user.getId());

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void delete(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
