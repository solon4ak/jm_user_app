package ru.solon4ak.jm_user_app.service;


import java.sql.SQLException;
import java.util.List;
import ru.solon4ak.jm_user_app.model.User;
import ru.solon4ak.jm_user_app.repository.UserDao;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userDao.listAll();
    }

    @Override
    public User getUserById(long id) throws SQLException {
        return userDao.get(id);
    }

    @Override
    public boolean addUser(User user) throws SQLException {
        return userDao.add(user);
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        return userDao.update(user);
    }

    @Override
    public boolean deleteUser(long id) throws SQLException {
        return userDao.delete(id);
    }
}
