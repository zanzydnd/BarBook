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

public class CommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Зашел");
        req.setCharacterEncoding("UTF-8");
        CommentsDao dao = new CommentsDao();
        UserDao userDao = new UserDao();
        System.out.println(req.getParameter("user_id"));
        User user = userDao.getUserById(Integer.parseInt(req.getParameter("user_id")));
        System.out.println(user.getName());
        CocktailDao cocktdao = new CocktailDao();
        Cocktail cocktail = cocktdao.getCocktailById(Integer.parseInt(req.getParameter("cocktail_id")));
        Comment comment = new Comment();
        comment.setComm(req.getParameter("comment"));
        comment.setUser(user);
        comment.setCocktail(cocktail);
        dao.putComment(comment);

        resp.sendRedirect(req.getContextPath() + "/cocktail?id=" + cocktail.getId());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
