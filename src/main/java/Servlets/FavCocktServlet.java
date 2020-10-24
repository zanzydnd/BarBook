package Servlets;

import DAO.CocktailDao;
import Entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class FavCocktServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CocktailDao dao = new CocktailDao();
        try {
            dao.favCockt(Integer.parseInt(request.getParameter("favcocktid")), ((User) request.getSession().getAttribute("user")).getId());
        }
        catch (SQLException e) {
            System.out.println("ne srabotalo");
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/cocktail?id=" + Integer.parseInt(request.getParameter("favcocktid")));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
