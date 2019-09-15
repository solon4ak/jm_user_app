package ru.solon4ak.jm_user_app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private final String jdbcURL;
    private final String jdbcUserName;
    private final String jdbcUserPassword;
    private Connection jdbcConnection;

    public DBUtil(String jdbcURL, String jdbcUserName, String jdbcUserPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUserName = jdbcUserName;
        this.jdbcUserPassword = jdbcUserPassword;
    }

    public Connection connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcUserPassword);
        }
        return jdbcConnection;
    }

    public void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }
}
