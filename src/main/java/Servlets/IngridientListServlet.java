package Servlets;

import DAO.IngridientDao;
import Entities.Ingridient;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class IngridientListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IngridientDao dao = new IngridientDao();
        request.setCharacterEncoding("UTF-8");
        try {
            request.setAttribute("ingridients",dao.findIngridients(request.getParameter("ina")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/ingridients.ftl" );
        requestDispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Ingridient> list = null;
        try {
            list = IngridientDao.getAllIngridients();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        request.setAttribute("ingridients",list);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/ingridients.ftl" );
        requestDispatcher.forward(request,response);
    }
}
