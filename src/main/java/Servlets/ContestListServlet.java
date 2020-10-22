package Servlets;

import DAO.ContestDao;
import Entities.Contest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ContestListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Contest> list = ContestDao.getAllContest();
        request.setAttribute("user",request.getSession().getAttribute("user"));
        request.setAttribute("contests",list);
        request.getRequestDispatcher("/views/contestlist.ftl").forward(request,response);
    }
}
