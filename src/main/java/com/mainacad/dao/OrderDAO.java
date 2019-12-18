package com.mainacad.dao;

import com.mainacad.dao.model.OrderDTO;
import com.mainacad.model.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public static Order save(Order order) {
        String sql = "INSERT INTO orders " +
                "(item_id, cart_id, amount) " +
                "VALUES (?, ?, ?)";
        String sequenceSQL = "SELECT currval(pg_get_serial_sequence('orders','id'))";

        int result = 0;
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql);
              PreparedStatement sequenceStatement =
                      connection.prepareStatement(sequenceSQL)) {
            preparedStatement.setInt(1, order.getItem().getId());
            preparedStatement.setInt(2, order.getCart().getId());
            preparedStatement.setInt(3, order.getAmount());
            result = preparedStatement.executeUpdate();

            if( result == 1 ) {
                ResultSet resultSet = sequenceStatement.executeQuery();
                while (resultSet.next()) {
                    Integer id = resultSet.getInt(1);
                    order.setId(id);
                    break;
                }
            }
            else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public static List<Order> getAllByCart(Cart cart) {
        String sql = "SELECT *, o.id as order_id FROM orders o JOIN items i ON i.id=o.item_id " +
                "WHERE o.cart_id=?";
        List<Order> orders = new ArrayList<>();
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, cart.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Order order = new Order (
                        resultSet.getInt("order_id"),
                        null,
                        cart,
                        resultSet.getInt("amount"));
                Item item = new Item(
                        resultSet.getInt("item_id"),
                        resultSet.getString("name"),
                        resultSet.getString("code"),
                        resultSet.getInt("price"),
                        resultSet.getInt("availability"));
                order.setItem(item);
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static Order getById(Integer id) {
        String sql = "SELECT * FROM orders o " +
                "JOIN items i ON i.id=o.item_id " +
                "JOIN carts c ON c.id=o.cart_id " +
                "JOIN users u ON u.id=c.user_id " +
                "WHERE o.id = ?";
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Order order = new Order (
                        resultSet.getInt("id"),
                        null,
                        null,
                        resultSet.getInt("amount")
                );
                Item item = new Item(
                        resultSet.getInt("item_id"),
                        resultSet.getString("name"),
                        resultSet.getString("code"),
                        resultSet.getInt("price"),
                        resultSet.getInt("availability"));
                Cart cart = new Cart(
                        resultSet.getInt("cart_id"),
                        Status.values()[resultSet.getInt("status")],
                        null,
                        resultSet.getLong("creation_time"));
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
                order.setItem(item);
                order.setCart(cart);
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void delete(Integer id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<OrderDTO> getAllDTOByCard(Cart cart) {
    	String sql = "SELECT *, o.id as order_id, o.amount "
    			+ "FROM orders o "
    			+ "JOIN items i ON i.id=o.item_id " +
                "WHERE o.cart_id=?";
    	List<OrderDTO> orderDTOS = new ArrayList<>();
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, cart.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                OrderDTO orderDTO = new OrderDTO(resultSet.getInt("order_id"), resultSet.getInt("item_id"), resultSet.getString("name"), resultSet.getInt("price"), resultSet.getInt("amount"));
                orderDTOS.add(orderDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDTOS;
    }
    	
    public static Order updateAmount(Order order, Integer amount) {
        String sql = "UPDATE orders SET " +
                "amount=? "+
                "WHERE id = ?";
        try ( Connection connection = ConnectionToDB.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setInt(2, order.getId());

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


