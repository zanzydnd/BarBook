package Servlets;

import DAO.CocktailDao;
import Entities.Cocktail;
import Entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class CocktailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer cockt_id = Integer.parseInt(request.getParameter("id"));
        System.out.println(cockt_id);
        Integer user_id = ((User) request.getSession().getAttribute("user")).getId();
        CocktailDao dao = new CocktailDao();
        try {
            dao.newLike(cockt_id, user_id);
            request.getRequestDispatcher("/views/Cocktail.jsp").forward(request,response);
        }
        catch (SQLException e){
            request.setAttribute("errMsg","Вы уже оставляли отзыв.");
            request.getRequestDispatcher("/views/Cocktail.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/Cocktail.jsp").forward(request,response);
    }
}
