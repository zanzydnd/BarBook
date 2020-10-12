package utilites;

import DAO.LoginDao;
import DAO.UserDao;
import Entities.User;

import javax.servlet.*;
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
        user.setPassword(password);
        LoginDao ld = new LoginDao();
        user = ld.authenticateUser(user);
        final HttpSession session = req.getSession();
        if(user != null) //If function returns success string then user will be rooted to Home page
        {
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
