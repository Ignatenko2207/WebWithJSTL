package com.mainacad.dao;
import com.mainacad.model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ItemDAO {
    private static Logger logger = Logger.getLogger(ItemDAO.class.getName());

    public static Item create(Item item) {
        String statement = "INSERT INTO items(code, name, price)" +
                "VALUES(?,?,?)";

        String curIdStatement = "SELECT currval(pg_get_serial_sequence('items','id'))";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement);
             Statement seqStatement = connection.createStatement()) {

            preparedStatement.setString(1, item.getItemCode());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setInt(3, item.getPrice());

            preparedStatement.executeUpdate();

            ResultSet resultSet = seqStatement.executeQuery(curIdStatement);
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                item.setId(id);

                return item;
            }

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }

        return null;
    }

    public static Item update(Item item) {
        String statement = "UPDATE items SET code=?, name=?, price=? WHERE id=?";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {

            preparedStatement.setString(1, item.getItemCode());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setInt(3, item.getPrice());
            preparedStatement.setInt(4, item.getId());

            preparedStatement.executeUpdate();

            return item;

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
        return null;
    }

    public static Item findById(Integer id){
        String statement = "SELECT * FROM items WHERE id=?";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = getItemFromResultSetItem(resultSet);

                return item;
            }

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
        return null;
    }

    public static List<Item> findByItemCode(String code) {
        List<Item> items = new ArrayList<>();

        String statement = "SELECT * FROM items WHERE code=?";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {

            preparedStatement.setString(1, code);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                items.add(getItemFromResultSetItem(resultSet));
            }

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }

        return items;
    }

    public static List<Item> findByItemPriceBetween(Integer minPrice, Integer maxPrice){
        List<Item> items = new ArrayList<>();

        String statement = "SELECT * FROM items WHERE price>=? AND price<=?";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {

            preparedStatement.setInt(1, minPrice);
            preparedStatement.setInt(2, maxPrice);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                items.add(getItemFromResultSetItem(resultSet));
            }

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }

        return items;
    }

    public static List<Item> findAll(){
        List<Item> items = new ArrayList<>();

        String statement = "SELECT * FROM items";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                items.add(getItemFromResultSetItem(resultSet));
            }

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }

        return items;
    }

    public static void delete(Integer itemId){
        String statement = "DELETE FROM items WHERE id=?";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {

            preparedStatement.setInt(1, itemId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }

    private static Item getItemFromResultSetItem(ResultSet resultSet) throws SQLException {
        Item item = new Item();

        item.setId(resultSet.getInt("id"));
        item.setItemCode(resultSet.getString("code"));
        item.setName(resultSet.getString("name"));
        item.setPrice(resultSet.getInt("price"));

        return item;
    }
}