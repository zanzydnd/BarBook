package Servlets;

import DAO.CocktailDao;
import Entities.Cocktail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CocktailListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CocktailDao c = new CocktailDao();
        List<Cocktail> list = c.getCocktails();
        request.setAttribute("cockts" , list);
        request.getRequestDispatcher("/views/cocktailList.jsp").forward(request,response);
    }
}
