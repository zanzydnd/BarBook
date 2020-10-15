package Servlets;

import DAO.LoginDao;
import Entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if(cookies.length > 0){
            System.out.println("Я использую куки");
            LoginDao ld = new LoginDao();
            User user = new User();
            user.setLogin(cookies[0].getValue());
            user.setPassword(cookies[1].getValue());
            user.setId(Integer.parseInt(cookies[2].getValue()));
            user = ld.authenticateUser(user);
            request.getSession().setAttribute("user",user);
            request.setAttribute("user",user);
        }
        RequestDispatcher rqDispatcher = request.getRequestDispatcher("views/main.jsp");
        rqDispatcher.forward(request,response);
    }
}
