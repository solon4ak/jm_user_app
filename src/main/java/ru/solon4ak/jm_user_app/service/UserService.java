package ru.solon4ak.jm_user_app.service;

import java.sql.SQLException;
import java.util.List;
import ru.solon4ak.jm_user_app.model.User;

public interface UserService {
    List<User> getAllUsers() throws SQLException;
    User getUserById(long id) throws SQLException;
    boolean addUser(User user) throws SQLException;
    boolean updateUser(User user) throws SQLException;
    boolean deleteUser(long id) throws SQLException;
}
