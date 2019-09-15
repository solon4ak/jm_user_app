package ru.solon4ak.jm_user_app.servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import ru.solon4ak.jm_user_app.model.User;
import ru.solon4ak.jm_user_app.repository.UserDao;
import ru.solon4ak.jm_user_app.repository.UserDaoImpl;
import ru.solon4ak.jm_user_app.service.UserService;
import ru.solon4ak.jm_user_app.service.UserServiceImpl;
import ru.solon4ak.jm_user_app.util.DBUtil;

@WebServlet(name = "UserServlet", urlPatterns = "/")
public class UserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {

        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUserName = getServletContext().getInitParameter("jdbcUsername");
        String jdbcUserPassword = getServletContext().getInitParameter("jdbcPassword");

        DBUtil dbUtil = new DBUtil(jdbcURL, jdbcUserName, jdbcUserPassword);
        UserDao userDao = new UserDaoImpl(dbUtil);

        userService = new UserServiceImpl(userDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        
        String action = req.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(req, resp);
                    break;
                case "/insert":
                    insertUser(req, resp);
                    break;
                case "/delete":
                    deleteUser(req, resp);
                    break;
                case "/edit":
                    showEditForm(req, resp);
                    break;
                case "/update":
                    updateUser(req, resp);
                    break;
                default:
                    listUsers(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        userService.deleteUser(id);
        resp.sendRedirect("list");
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {
        List<User> users = userService.getAllUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("WEB-INF/jsp/view/user/list.jsp").forward(req, resp);
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        User user = userService.getUserById(id);

        user.setFirstName(req.getParameter("firstName"));
        user.setLastName(req.getParameter("lastName"));
        user.setEmail(req.getParameter("email"));
        user.setAddress(req.getParameter("address"));
        user.setPhoneNumber(req.getParameter("phoneNumber"));
        user.setAge(Byte.valueOf(req.getParameter("age")));

        userService.updateUser(user);
        resp.sendRedirect("list");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        User user = userService.getUserById(id);
        req.setAttribute("user", user);
        req.getRequestDispatcher("WEB-INF/jsp/view/user/user.jsp").forward(req, resp);
    }

    private void insertUser(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {
        User user = new User(
                req.getParameter("firstName"),
                req.getParameter("lastName"),
                req.getParameter("email"),
                req.getParameter("address"),
                req.getParameter("phoneNumber"),
                Byte.valueOf(req.getParameter("age")));
        userService.addUser(user);
        resp.sendRedirect("list");
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/view/user/user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
