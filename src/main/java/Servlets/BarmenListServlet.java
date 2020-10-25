package Servlets;

import DAO.UserDao;
import Entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BarmenListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("user") != null) {
            request.setAttribute("user", new UserDao().getUserById(((User) request.getSession().getAttribute("user")).getId()));
        }
        request.setAttribute("barmen", new UserDao().getAllUsers());
        request.getRequestDispatcher("views/barmen-list.ftl").forward(request,response);
    }
}
