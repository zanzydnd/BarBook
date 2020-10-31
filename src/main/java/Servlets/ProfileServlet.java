package Servlets;

import DAO.UserDao;
import Entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao dao = new UserDao();
        if(request.getParameter("id").equals("self")){
            request.setAttribute("check" , "self");
            try {
                request.setAttribute("user_profile",dao.getUserById(((User)request.getSession().getAttribute("user")).getId()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            request.setAttribute("check" , "not_self");
            Integer id = Integer.parseInt(request.getParameter("id"));
            try {
                request.setAttribute("user_profile",new UserDao().getUserById(id));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        try {
            request.setAttribute("user",dao.getUserById(((User)request.getSession().getAttribute("user")).getId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        RequestDispatcher rqDispatcher = request.getRequestDispatcher("views/profile.ftl");
        rqDispatcher.forward(request,response);
    }
}
