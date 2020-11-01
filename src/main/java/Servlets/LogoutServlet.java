package Servlets;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("login") || cookie.getName().equals("pass") || cookie.getName().equals("id")){
                System.out.println("Ya zashel");
                cookie.setValue(null);
                cookie.setPath(request.getContextPath());
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        request.getServletContext().setAttribute("user",null);
        request.getSession().setAttribute("user",null);
        response.sendRedirect(request.getContextPath() + "/");

    }
}
