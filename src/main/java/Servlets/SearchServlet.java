package Servlets;

import DAO.CocktailDao;
import Entities.Cocktail;
import com.google.gson.Gson;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

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
        req.setCharacterEncoding("UTF-8");
        List<Cocktail> list;
        CocktailDao dao = new CocktailDao();
        System.out.println("search " + req.getParameter("search"));
        System.out.println("tags " + req.getParameter("tags"));
        String s = req.getParameter("tags");
        String name = req.getParameter("search");
        if (!s.equals("[]")) {
            String[] tags;
            tags = s.split(",");
            for (int i = 0; i < tags.length; i++) {
                tags[i] = tags[i].replace("[", "");
                tags[i] = tags[i].replace("\"", "");
                tags[i] = tags[i].replace("]", "");
            }
            if(!name.equals("")){
                System.out.println("фильтр + строка");
                list = dao.getCocktailsByTagsAndName(name, tags);
            }
            else{
                System.out.println("фильтр");
                list = dao.getCocktailsByTags(tags);
            }
        } else {
            System.out.println("строка");
            list = dao.getCocktailsIdByName(name);
        }
        resp.setContentType("application/json");
        String json = new Gson().toJson(list);
        System.out.println(json);
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
