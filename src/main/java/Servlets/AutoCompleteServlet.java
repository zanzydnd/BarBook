package Servlets;

import DAO.CocktailDao;
import Entities.Cocktail;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class AutoCompleteServlet extends HttpServlet {

    private ServletContext context;
    private CocktailDao dao= new CocktailDao();
    private List<Cocktail> list= dao.getCocktails();


    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String targetId = req.getParameter("id");
        StringBuffer sb = new StringBuffer();

        if (targetId != null) {
            targetId = targetId.trim().toLowerCase();
        } else {
            context.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
        boolean namesAdded = false;
        Cocktail searched = new Cocktail();
        if (action.equals("complete")) {
            // check if user sent empty string
            if (!targetId.equals("")) {

                Iterator it = list.iterator();

                while (it.hasNext()) {
                    Cocktail cocktail = (Cocktail) it.next();
                    if (cocktail.getName().toLowerCase().startsWith(targetId)) {
                        sb.append("<coctail>");
                        sb.append("<id>" + cocktail.getId() + "</id>");
                        sb.append("<name>" + cocktail.getName() + "</name>");
                        sb.append("</cocktail>");
                        namesAdded = true;
                        searched = cocktail;
                    }
                }
            }

            if (namesAdded) {
                resp.setContentType("text/xml");
                resp.setHeader("Cache-Control", "no-cache");
                resp.getWriter().write("<cocktails>" + sb.toString() + "</cocktails>");
            } else {
                //nothing to show
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
        if (action.equals("lookup")) {

            // put the target composer in the request scope to display
            if ((targetId != null) ) {
                req.setAttribute("cocktail", searched);
                context.getRequestDispatcher("/Cocktail.jsp").forward(req, resp);
            }
        }
    }
}
