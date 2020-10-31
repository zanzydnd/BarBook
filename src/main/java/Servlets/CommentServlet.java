package Servlets;

import DAO.CocktailDao;
import DAO.CommentsDao;
import DAO.UserDao;
import Entities.Cocktail;
import Entities.Comment;
import Entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class CommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") != null) {
            System.out.println("Зашел");
            req.setCharacterEncoding("UTF-8");
            CommentsDao dao = new CommentsDao();
            UserDao userDao = new UserDao();
            System.out.println(req.getParameter("user_id"));
            User user = null;
            try {
                user = userDao.getUserById(Integer.parseInt(req.getParameter("user_id")));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println(user.getName());
            CocktailDao cocktdao = new CocktailDao();
            Cocktail cocktail = null;
            try {
                cocktail = cocktdao.getCocktailById(Integer.parseInt(req.getParameter("cocktail_id")));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Comment comment = new Comment();
            if (comment != null) {
                comment.setComm(req.getParameter("comment"));
                comment.setUser(user);
                comment.setCocktail(cocktail);
                try {
                    dao.putComment(comment);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                resp.sendRedirect(req.getContextPath() + "/cocktail?id=" + cocktail.getId());
            } else {
                req.setAttribute("errMsg", "Text is empty");
                resp.sendRedirect(req.getContextPath() + "/cocktail?id=" + cocktail.getId());
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/cocktail?id=" + Integer.parseInt(req.getParameter("cocktail_id")));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
