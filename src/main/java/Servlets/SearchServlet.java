package Servlets;

import DAO.CocktailDao;
import Entities.Cocktail;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Cocktail> list;
        CocktailDao dao = new CocktailDao();
        list = dao.getCocktailsIdByName(req.getParameter("search"));
        resp.setContentType("application/json");
        String json = new Gson().toJson(list);
        System.out.println(json);
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
