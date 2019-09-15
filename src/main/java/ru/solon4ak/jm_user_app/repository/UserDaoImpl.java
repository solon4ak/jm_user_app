package ru.solon4ak.jm_user_app.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ru.solon4ak.jm_user_app.model.User;
import ru.solon4ak.jm_user_app.util.DBUtil;

public class UserDaoImpl implements UserDao {

    private DBUtil dbUtil;

    public UserDaoImpl(DBUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public boolean add(User user) throws SQLException {

        StringBuilder sb = new StringBuilder();
        sb.append("insert into users (first_name, last_name, email, address, phone_number, age) ");
        sb.append("values (?, ?, ?, ?, ?, ?)");

        Connection connection = dbUtil.connect();

        PreparedStatement ps = connection.prepareStatement(sb.toString());
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getAddress());
        ps.setString(5, user.getPhoneNumber());
        ps.setByte(6, user.getAge());

        boolean isInserted = ps.executeUpdate() > 0;
        ps.close();
        dbUtil.disconnect();

        return isInserted;
    }

    @Override
    public List<User> listAll() throws SQLException {
        createTable();
        List<User> users = new ArrayList<>();
        String sql = "select * from users";
        Connection connection = dbUtil.connect();
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        User user;
        while (result.next()) {
            user = new User(
                    result.getLong("id"),
                    result.getString("first_name"),
                    result.getString("last_name"),
                    result.getString("email"),
                    result.getString("address"),
                    result.getString("phone_number"),
                    result.getByte("age")
            );
            users.add(user);
        }

        result.close();
        stmt.close();
        dbUtil.disconnect();

        return users;
    }

    @Override
    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM users where id = ?";
        Connection connection = dbUtil.connect();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);

        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        dbUtil.disconnect();

        return rowDeleted;
    }

    @Override
    public boolean update(User user) throws SQLException {

        StringBuilder sb = new StringBuilder();
        sb.append("update users set first_name=?, last_name=?, email=?, ");
        sb.append("address=?, phone_number=?, age=? ");
        sb.append("where id=?");

        Connection connection = dbUtil.connect();
        PreparedStatement ps = connection.prepareStatement(sb.toString());
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getAddress());
        ps.setString(5, user.getPhoneNumber());
        ps.setByte(6, user.getAge());
        ps.setLong(7, user.getId());
        boolean isUpdated = ps.executeUpdate() > 0;
        ps.close();
        dbUtil.disconnect();

        return isUpdated;
    }

    @Override
    public User get(long id) throws SQLException {
        User user = null;
        String sql = "select * from users where id = ?";
        Connection connection = dbUtil.connect();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("address"),
                    resultSet.getString("phone_number"),
                    resultSet.getByte("age"));
        }

        resultSet.close();
        statement.close();
        dbUtil.disconnect();
        return user;
    }

    public void createTable() throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists users ");
        sb.append("(id bigint auto_increment, ");
        sb.append("first_name varchar(256), ");
        sb.append("last_name varchar(256), ");
        sb.append("email varchar(256), ");
        sb.append("address varchar(512), ");
        sb.append("phone_number varchar(15), ");
        sb.append("age TINYINT UNSIGNED, ");
        sb.append("primary key (id))");

        Connection connection = dbUtil.connect();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sb.toString());
        stmt.close();
        dbUtil.disconnect();
    }
}
