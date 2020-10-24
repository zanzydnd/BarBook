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
        if(request.getParameter("id").equals("self")){
            request.setAttribute("check" , "self");
            UserDao dao = new UserDao();
            request.setAttribute("user_profile",dao.getUserById(((User)request.getSession().getAttribute("user")).getId()));
        }
        else{
            request.setAttribute("check" , "not_self");
            Integer id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("user_profile",new UserDao().getUserById(id));
        }
        request.setAttribute("user",(User)request.getSession().getAttribute("user"));
        RequestDispatcher rqDispatcher = request.getRequestDispatcher("views/profile.ftl");
        rqDispatcher.forward(request,response);
    }
}
