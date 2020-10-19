package Servlets;

import DAO.LoginDao;
import Entities.User;
import sun.rmi.runtime.Log;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String login = "";
        String pass = "";
        Integer id = 0;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("login")){
                login = cookie.getValue();
            }
            else if(cookie.getName().equals("pass")){
                pass = cookie.getValue();
            }
            else if(cookie.getName().equals("id")){
                id = Integer.parseInt(cookie.getValue());
            }
        }
        if(pass != null && login != null){
            LoginDao ld = new LoginDao();
            User user = new User();
            user.setId(id);
            user.setPassword(pass);
            user.setLogin(login);
            user = ld.authenticateUser(user);
            request.getSession().setAttribute("user",user);
        }
        RequestDispatcher rqDispatcher = request.getRequestDispatcher("views/main.jsp");
        rqDispatcher.forward(request,response);
    }
}
