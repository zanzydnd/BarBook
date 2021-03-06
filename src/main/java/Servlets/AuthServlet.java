package Servlets;

import DAO.RegisterDao;
import Entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        RegisterDao regDao = new RegisterDao();
        User user = new User();
        Pattern loginp = Pattern.compile("^[a-zA-Z](.[a-zA-Z0-9_-]*)");
        Pattern passwordp = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$");
        user.setLogin(request.getParameter("login"));
        user.setEmail(request.getParameter("email"));
        user.setName(request.getParameter("name"));
        user.setPassword(request.getParameter("pass"));
        user.setInformation(request.getParameter("information"));
        Matcher loginm = loginp.matcher(request.getParameter("login"));
        Matcher passwordm = passwordp.matcher(request.getParameter("pass"));
        if (loginm.matches() && passwordm.matches()) {
            String userRegister = null;
            try {
                userRegister = regDao.registerUser(user);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (userRegister.equals("SUCCESS")) {
                RequestDispatcher rqDispatcher = request.getRequestDispatcher("views/authorizing.ftl");
                rqDispatcher.forward(request, response);
            } else {
                request.setAttribute("errMsg","Попробуйте еще раз");
                request.getRequestDispatcher("views/registration.ftl").forward(request,response);
            }
        } else {
            request.setAttribute("errMsg","Неверный формат логина или пароля");
            request.getRequestDispatcher("views/registration.ftl").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rqDispatcher = request.getRequestDispatcher("views/authorizing.ftl");
        rqDispatcher.forward(request, response);
    }
}
