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
        Integer cockt_id = Integer.parseInt(request.getParameter("likedCocktId"));
        //Integer user_id = Integer.parseInt(request.getParameter("user_id"));
        Integer user_id = ((User) request.getSession().getAttribute("user")).getId();
        CocktailDao dao = new CocktailDao();
        try {
            dao.newLike(cockt_id, user_id);
            //response.getWriter().write(dao.getCocktailById(cockt_id).getRating());
            request.getRequestDispatcher("/views/Cocktail.jsp").forward(request,response);
        }
        catch (SQLException e){
            //response.getWriter().write("Already");
            request.setAttribute("errMsg","Вы уже оставляли отзыв.");
            request.getRequestDispatcher("/views/Cocktail.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/Cocktail.jsp").forward(request,response);
    }
}
