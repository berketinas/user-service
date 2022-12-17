package com.berketinas.sm.berkemedia.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class ConnectionPool {
    private static String url;
    private static String user;
    private static String password;
    private static List<Connection> pool;
    private static List<Connection> usedConnections;
    private static final int POOL_MAX = 10;

    static {
        try {
            Properties props = PropertiesLoader.loadProperties();
            url = props.getProperty("jdbc.url");
            user = props.getProperty("jdbc.user");
            password = props.getProperty("jdbc.password");

            pool = new ArrayList<>(POOL_MAX);
            Class.forName("org.postgresql.Driver");
            for(int i = 0; i < POOL_MAX; i++) {
                pool.add(createConnection(url, user, password));
            }

            usedConnections = new ArrayList<>();
        } catch(SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Connection createConnection(String url, String user, String password)
        throws SQLException {

        return DriverManager.getConnection(url, user, password);
    }

    public static Connection getConnection() {
        Connection connection = pool.remove(pool.size() - 1);
        usedConnections.add(connection);

        return connection;
    }

    public static void releaseConnection(Connection connection) {
        pool.add(connection);
        usedConnections.remove(connection);
    }
}
