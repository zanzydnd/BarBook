package Servlets;

import DAO.CocktailDao;
import DAO.IngridientDao;
import Entities.Cocktail;
import Entities.Ingridient;
import Entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngridientServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);
        Ingridient ing = IngridientDao.getIngredientById(id);
        request.setAttribute("ingridient", ing);
        List<Cocktail> list = null;
        try {
            list = new IngridientDao().getCoctailsByIngridient(ing);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<List<Ingridient>> rec = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            try {
                list.get(i).setIngridients(new CocktailDao().getRecepie(list.get(i)));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        request.setAttribute("cocktail_recipie",rec);
        request.setAttribute("cocktails", list);
        request.getRequestDispatcher("/views/ingridient.ftl").forward(request, response);
    }
}
