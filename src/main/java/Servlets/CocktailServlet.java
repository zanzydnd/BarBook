package Servlets;

import DAO.CocktailDao;
import DAO.CommentsDao;
import Entities.Cocktail;
import Entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CocktailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer cockt_id = Integer.parseInt(request.getParameter("likedCocktId"));
        Integer user_id = ((User) request.getSession().getAttribute("user")).getId();
        CocktailDao dao = new CocktailDao();
        try {
            dao.newLike(cockt_id, user_id);
            request.setAttribute("user",(User)request.getSession().getAttribute("user"));
            request.setAttribute("cocktail",dao.getCocktailById(Integer.parseInt(request.getParameter("id"))));
            request.setAttribute("ingridients",dao.getRecepie(dao.getCocktailById(Integer.parseInt(request.getParameter("id")))));
            request.setAttribute("comments",new CommentsDao().getComments(dao.getCocktailById(Integer.parseInt(request.getParameter("id")))));
            String[] str = dao.getCocktailById(Integer.parseInt(request.getParameter("id"))).getRecipie().split(";");
            List<String> list = Arrays.asList(str);
            request.setAttribute("str",list);
            request.setAttribute("errMsg",null);
            request.getRequestDispatcher("/views/Cocktail.ftl").forward(request,response);
        }
        catch (SQLException e){
            request.setAttribute("errMsg","Вы уже оставляли лайк");
            request.setAttribute("user",(User)request.getSession().getAttribute("user"));
            request.setAttribute("cocktail",dao.getCocktailById(Integer.parseInt(request.getParameter("id"))));
            request.setAttribute("ingridients",dao.getRecepie(dao.getCocktailById(Integer.parseInt(request.getParameter("id")))));
            request.setAttribute("comments",new CommentsDao().getComments(dao.getCocktailById(Integer.parseInt(request.getParameter("id")))));
            String[] str = dao.getCocktailById(Integer.parseInt(request.getParameter("id"))).getRecipie().split(";");
            List<String> list = Arrays.asList(str);
            request.setAttribute("str",list);
            request.getRequestDispatcher("/views/Cocktail.ftl").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CocktailDao dao = new CocktailDao();
        request.setAttribute("user",(User)request.getSession().getAttribute("user"));
        request.setAttribute("cocktail",dao.getCocktailById(Integer.parseInt(request.getParameter("id"))));
        request.setAttribute("ingridients",dao.getRecepie(dao.getCocktailById(Integer.parseInt(request.getParameter("id")))));
        request.setAttribute("comments",new CommentsDao().getComments(dao.getCocktailById(Integer.parseInt(request.getParameter("id")))));
        String[] str = dao.getCocktailById(Integer.parseInt(request.getParameter("id"))).getRecipie().split(";");
        List<String> list = Arrays.asList(str);
        request.setAttribute("str",list);
        request.getRequestDispatcher("/views/Cocktail.ftl").forward(request,response);
    }
}
