package Servlets;

import DAO.CocktailDao;
import DAO.IngridientDao;
import DAO.UserDao;
import Entities.Cocktail;
import Entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;

@MultipartConfig
public class RedactCocktailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if(request.getSession().getAttribute("user") != null){
            try{
                System.out.println(request.getParameter("id"));
                String newName = request.getParameter("name");
                newName.isEmpty();
                System.out.println(newName);
                String newInfo = request.getParameter("text");
                newInfo.isEmpty();
                System.out.println(newInfo);
                Part bicPicPart = request.getPart("bigPicture");
                bicPicPart.toString();
                System.out.println("+big");
                String bicPict = Paths.get(bicPicPart.getSubmittedFileName()).getFileName().toString();
                String pathFileBig = "";
                String absPathBig = "";
                if (bicPict.length() > 1) {
                    InputStream fileContent = bicPicPart.getInputStream();
                    pathFileBig = "img\\cocktail\\" + ((User) request.getSession().getAttribute("user")).getLogin() + "big" + UUID.randomUUID().toString() + bicPict;
                    absPathBig = "C:\\Users\\Даня\\IdeaProjects\\BarBook\\BarBook\\src\\main\\webapp\\views\\" + pathFileBig;
                    File file = new File(absPathBig);
                    boolean created = file.createNewFile();
                    OutputStream os = new FileOutputStream(absPathBig);
                    byte[] b = new byte[2048];
                    int l;
                    while ((l = fileContent.read(b)) != -1) {
                        os.write(b, 0, l);
                    }
                    fileContent.close();
                    os.close();
                }
                pathFileBig = absPathBig.replace('\\', '/');
                Part smallPart = request.getPart("smallPicture");
                smallPart.toString();
                System.out.println("small+");
                String smallPict = Paths.get(smallPart.getSubmittedFileName()).getFileName().toString();
                String pathFileSmall = "";
                String absPathSmall = "";
                if (bicPict.length() > 1) {
                    InputStream fileContent = smallPart.getInputStream();
                    pathFileSmall = "img\\cocktail\\" + ((User) request.getSession().getAttribute("user")).getLogin() + "small" + UUID.randomUUID().toString() + smallPict;
                    absPathSmall = "C:\\Users\\Даня\\IdeaProjects\\BarBook\\BarBook\\src\\main\\webapp\\views\\" + pathFileSmall;
                    File file = new File(absPathSmall);
                    boolean created = file.createNewFile();
                    OutputStream os = new FileOutputStream(absPathSmall);
                    byte[] b = new byte[2048];
                    int l;
                    while ((l = fileContent.read(b)) != -1) {
                        os.write(b, 0, l);
                    }
                    fileContent.close();
                    os.close();
                }
                pathFileSmall = absPathSmall.replace('\\', '/');
                try{
                    CocktailDao dao = new CocktailDao();
                    dao.upgrade(Integer.parseInt(request.getParameter("id")),newName,newInfo,pathFileBig,pathFileSmall);
                    response.sendRedirect(request.getContextPath() + "/cocktail?id=" + request.getParameter("id"));
                } catch (SQLException e){
                    e.printStackTrace();
                    request.setAttribute("errMsg","Что-то с бд");
                    response.sendRedirect(request.getContextPath() + "/redactCocktail?id=" + request.getParameter("id"));
                }
            }catch (NullPointerException e){
                request.setAttribute("errMsg","Что-то null");
                response.sendRedirect(request.getContextPath() + "/redactCocktail?id=" + request.getParameter("id"));
            }
        }
        else{
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null && request.getParameter("id") != null) {
            try {
                request.setAttribute("user", new UserDao().getUserById(((User) request.getSession().getAttribute("user")).getId()));
                request.setAttribute("cocktail", new CocktailDao(). getCocktailById(Integer.parseInt((request.getParameter("id")))));
                if(!((User) request.getAttribute("user")).getId().equals(((Cocktail) request.getAttribute("cocktail")).getAuthor_id())){
                    request.setAttribute("errMsg","Не-а");
                    response.sendRedirect(request.getContextPath() + "/");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            request.getRequestDispatcher("views/redactCocktail.ftl").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
