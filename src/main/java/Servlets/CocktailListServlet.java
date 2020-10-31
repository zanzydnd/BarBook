package Servlets;

import DAO.CocktailDao;
import DAO.UserDao;
import Entities.Cocktail;
import Entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CocktailListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CocktailDao c = new CocktailDao();
        List<Cocktail> list = null;
        try {
            list = c.getCocktails();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(request.getSession().getAttribute("user") != null){
            try {
                request.setAttribute("user", new UserDao().getUserById(((User)request.getSession().getAttribute("user")).getId()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        request.setAttribute("cocktails" , list);
        request.getRequestDispatcher("/views/cocktailList.ftl").forward(request,response);
    }
}
