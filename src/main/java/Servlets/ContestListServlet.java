package Servlets;

import DAO.ContestDao;
import DAO.UserDao;
import Entities.Contest;
import Entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ContestListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Contest> list = null;
        try {
            list = ContestDao.getAllContest();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (request.getSession().getAttribute("user") != null){
            try {
                request.setAttribute("user",new UserDao().getUserById(((User)request.getSession().getAttribute("user")).getId()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        request.setAttribute("contests",list);
        request.getRequestDispatcher("/views/contestlist.ftl").forward(request,response);
    }
}
