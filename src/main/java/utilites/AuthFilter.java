package utilites;

import DAO.LoginDao;
import DAO.UserDao;
import Entities.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;

        final String login = req.getParameter("login");
        final String password = req.getParameter("pass");

        UserDao dao = new UserDao();
        User user = new User();
        user.setLogin(login);
        user.setPassword(Hashing.md5Custom(password));
        LoginDao ld = new LoginDao();
        user = ld.authenticateUser(user);
        final HttpSession session = req.getSession();
        if(user != null) //If function returns success string then user will be rooted to Home page
        {
            if(req.getParameter("cookie")!=null){
                Cookie cook1 = new Cookie("login",user.getLogin());
                Cookie cook2 = new Cookie("pass",user.getPassword());
                Cookie cook3 = new Cookie("id", user.getId().toString());
                cook1.setMaxAge(60*60*24*30);
                cook2.setMaxAge(60*60*24*30);
                cook3.setMaxAge(60*60*24*30);
                res.addCookie(cook1);
                res.addCookie(cook2);
                res.addCookie(cook3);
            }
            session.setAttribute("user",user);
            servletRequest.getRequestDispatcher("/views/main.jsp").forward(servletRequest, servletResponse);
        }
        else
        {
            servletRequest.setAttribute("errMessage", "smth went wrong"); //If authenticateUser() function returnsother than SUCCESS string it will be sent to Login page again. Here the error message returned from function has been stored in a errMessage key.
            servletRequest.getRequestDispatcher("/views/main.jsp").forward(servletRequest, servletResponse);//forwarding the request
        }
    }

    @Override
    public void destroy() {

    }
}
