package com.berketinas.sm.berkemedia.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class StatementPool {
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SAVE_USER = "INSERT INTO users (given_name, family_name, email, country) " +
            "VALUES (?, ?, ?, ?) ON CONFLICT (email) DO UPDATE " +
            "SET given_name = EXCLUDED.given_name, family_name = EXCLUDED.family_name, country = EXCLUDED.country";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";

    private static PreparedStatement find;
    private static PreparedStatement findAll;
    private static PreparedStatement save;
    private static PreparedStatement delete;

    static {
        Connection connection = ConnectionPool.getConnection();

        try {
            find = connection.prepareStatement(SELECT_USER_BY_ID);
            findAll = connection.prepareStatement(SELECT_ALL_USERS);
            save = connection.prepareStatement(SAVE_USER);
            delete = connection.prepareStatement(DELETE_USER);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ConnectionPool.releaseConnection(connection);
    }

    public static PreparedStatement getFind() {
        return find;
    }

    public static PreparedStatement getFindAll() {
        return findAll;
    }

    public static PreparedStatement getSave() {
        return save;
    }

    public static PreparedStatement getDelete() {
        return delete;
    }
}
