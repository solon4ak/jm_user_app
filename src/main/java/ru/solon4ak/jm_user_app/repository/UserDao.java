package ru.solon4ak.jm_user_app.repository;

import java.sql.SQLException;
import java.util.List;
import ru.solon4ak.jm_user_app.model.User;

public interface UserDao {

    boolean add(User user) throws SQLException;
    List<User> listAll() throws SQLException;
    boolean delete(long id) throws SQLException;
    boolean update(User user) throws SQLException;
    User get(long id) throws SQLException;
}
