package Servlets;

import DAO.RegisterDao;
import Entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        RegisterDao regDao = new RegisterDao();
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setEmail(request.getParameter("email"));
        user.setName(request.getParameter("name"));
        user.setPassword(request.getParameter("pass"));
        user.setInformation(request.getParameter("information"));
        String userRegister = regDao.registerUser(user);
        if(userRegister.equals("SUCCESS")){
            RequestDispatcher rqDispatcher = request.getRequestDispatcher("views/authorizing.jsp");
            rqDispatcher.forward(request,response);
        }
        else{
            RequestDispatcher rqDispatcher = request.getRequestDispatcher("views/main.jsp");
            rqDispatcher.forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rqDispatcher = request.getRequestDispatcher("views/authorizing.jsp");
        rqDispatcher.forward(request,response);
    }
}
