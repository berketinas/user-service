package com.berketinas.sm.berkemedia.dao;

import com.berketinas.sm.berkemedia.model.User;
import com.berketinas.sm.berkemedia.utils.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// ***
// do I need to initialize the driver before calling DriverManager.getConnection()? - NO
// ***

public class UserDAOImpl implements DAO<User> {
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SAVE_USER = "INSERT INTO users (given_name, family_name, email, country) " +
            "VALUES (?, ?, ?, ?) ON CONFLICT (email) DO UPDATE " +
            "SET given_name = EXCLUDED.given_name, family_name = EXCLUDED.family_name, country = EXCLUDED.country";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";

    public UserDAOImpl() {}

    @Override
    public User find(UUID id) {
        User user = null;

        try {
            Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
//            PreparedStatement preparedStatement = StatementPool.getFind();

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new User(
                        resultSet.getObject("id", java.util.UUID.class),
                        resultSet.getString("given_name"),
                        resultSet.getString("family_name"),
                        resultSet.getString("email"),
                        resultSet.getString("country")
                );
            }

            preparedStatement.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try {
            Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
//            PreparedStatement preparedStatement = StatementPool.getFindAll();

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getObject("id", java.util.UUID.class),
                        resultSet.getString("given_name"),
                        resultSet.getString("family_name"),
                        resultSet.getString("email"),
                        resultSet.getString("country")));
            }

            preparedStatement.close();
            ConnectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void save(User user) {
        try {
            Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER);
//            PreparedStatement preparedStatement = StatementPool.getSave();

            preparedStatement.setObject(1, user.getGivenName());
            preparedStatement.setObject(2, user.getFamilyName());
            preparedStatement.setObject(3, user.getEmail());
            preparedStatement.setObject(4, user.getCountry());

            preparedStatement.executeUpdate();

            ConnectionPool.releaseConnection(connection);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
//            PreparedStatement preparedStatement = StatementPool.getDelete();

            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();

            ConnectionPool.releaseConnection(connection);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
