package com.mainacad.dao;

import com.mainacad.dao.model.CartDTO;
import com.mainacad.model.Cart;
import com.mainacad.model.Status;
import com.mainacad.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public static Cart save(Cart cart) {
        String sql = "INSERT INTO carts " +
                "(status, user_id, creation_time) " +
                "VALUES (?, ?, ?)";
        String sequenceSQL = "SELECT currval(pg_get_serial_sequence('carts','id'))";

        int result = 0;
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql);
              PreparedStatement sequenceStatement =
                      connection.prepareStatement(sequenceSQL)) {
            preparedStatement.setInt(1, cart.getStatus().ordinal());
            preparedStatement.setInt(2, cart.getUser().getId());
            preparedStatement.setLong(3, cart.getCreationTime());
            result = preparedStatement.executeUpdate();

            if( result == 1 ) {
                ResultSet resultSet = sequenceStatement.executeQuery();
                while (resultSet.next()) {
                    Integer id = resultSet.getInt(1);
                    cart.setId(id);
                    break;
                }
            }
            else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }

    public static List<Cart> getAllByUserAndPeriod(User user, Long timeFrom, Long timeTo) {
        String sql = "SELECT * FROM carts WHERE user_id=? AND creation_time >= ? AND creation_time <=?";
        List<Cart> carts = new ArrayList<>();
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setLong(2, timeFrom);
            preparedStatement.setLong(3, timeTo);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Cart cart = new Cart(
                        resultSet.getInt("id"),
                        Status.values()[resultSet.getInt("status")],
                        user,
                        resultSet.getLong("creation_time")
                );
                carts.add(cart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carts;
    }

    public static Cart getById(Integer id) {
        String sql = "SELECT *, c.id AS cartId FROM carts c " +
                "JOIN users u ON u.id=c.user_id " +
                "WHERE c.id = ?";
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Cart cart = new Cart (
                        resultSet.getInt("cartId"),
                        Status.values()[resultSet.getInt("status")],
                        null,
                        resultSet.getLong("creation_time")
                );
                User user = new User (
                        resultSet.getInt("user_id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("e_mail"),
                        resultSet.getString("phone")
                );
                cart.setUser(user);
                return cart;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cart getByUserAndOpenStatus(User user) {
        String sql = "SELECT * FROM carts WHERE user_id = ? AND status=0";
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Cart cart = new Cart (
                        resultSet.getInt("id"),
                        Status.values()[resultSet.getInt("status")],
                        user,
                        resultSet.getLong("creation_time")
                );
                return cart;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cart updateStatus(Cart cart, Status status) {
        String sql = "UPDATE carts SET " +
                "status=? "+
                "WHERE id = ?";
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, status.ordinal());
            preparedStatement.setInt(2, cart.getId());

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return cart;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void delete(Integer id) {
        String sql = "DELETE FROM carts WHERE id = ?";
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<CartDTO> getItemsSumGroupedByUser(Long timeFrom, Long timeTo){
        String sql = "SELECT u.login, SUM(o.amount * i.price) as sum_items, MIN(c.creation_time) as creat_time FROM carts c " +
                "JOIN users u ON c.user_id = u.id " +
                "JOIN orders o ON c.id = o.cart_id " +
                "JOIN items i ON i.id = o.item_id " +
                "WHERE c.creation_time >= ? AND c.creation_time <= ? AND c.status = 2 " +
                "GROUP BY u.login " +
                "ORDER BY MIN(c.creation_time)";
        List<CartDTO> cartDTOS = new ArrayList<>();
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql);
        ) {
            preparedStatement.setLong(1, timeFrom);
            preparedStatement.setLong(2, timeTo);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                CartDTO cartDTO = new CartDTO(
                        resultSet.getString("login"),
                        resultSet.getInt("sum_items"),
                        resultSet.getLong("creat_time")
                );
                cartDTOS.add(cartDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartDTOS;
    }

}