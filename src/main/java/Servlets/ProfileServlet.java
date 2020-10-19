package Servlets;

import DAO.UserDao;
import Entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer id = ((User)request.getSession().getAttribute("user")).getId();
        request.getSession().setAttribute("user",new UserDao().getUserById(id));
        RequestDispatcher rqDispatcher = request.getRequestDispatcher("views/profile.jsp");
        rqDispatcher.forward(request,response);
    }
}
