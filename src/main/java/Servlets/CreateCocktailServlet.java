package Servlets;

import DAO.CocktailDao;
import DAO.IngridientDao;
import DAO.UserDao;
import Entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;


@MultipartConfig
public class CreateCocktailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            try {
                request.setCharacterEncoding("UTF-8");
                String newName = request.getParameter("name");
                newName.isEmpty();
                System.out.println("+");
                String newInfo = request.getParameter("text");
                newInfo.isEmpty();
                System.out.println("++");
                String[] ingList = request.getParameterValues("ing");
                ingList[0].isEmpty();
                System.out.println("+++");
                String alco = request.getParameter("alco");
                alco.isEmpty();
                System.out.println("++++");
                String taste = request.getParameter("taste");
                taste.isEmpty();
                System.out.println("+++++");
                String krep = request.getParameter("krep");
                krep.isEmpty();
                System.out.println("++++++");
                String[] tags = new String[3];
                tags[0] = alco;
                tags[1] = taste;
                tags[2] = krep;
                String[] recipie = request.getParameterValues("1");
                recipie[0].isEmpty();
                System.out.println("++++++-");
                Part bicPicPart = request.getPart("bigPicture");
                bicPicPart.toString();
                System.out.println("-");
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
                System.out.println("--");
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
                CocktailDao dao = new CocktailDao();
                Integer user_id = Integer.parseInt(request.getParameter("user_id"));
                try {
                    Integer id = dao.newCocktail(newName, newInfo, ingList, recipie, tags, user_id, pathFileBig, pathFileSmall);
                    response.sendRedirect(request.getContextPath() + "/cocktail?id=" + id);
                }
                catch (SQLException e){
                    request.setAttribute("errMsg", "Ошибка бд");
                    request.getRequestDispatcher("/views/createCocktail.ftl").forward(request, response);
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                request.setAttribute("errMsg", "Вы поступаете неправильно");
                request.getRequestDispatcher("/views/createCocktail.ftl").forward(request, response);
            }
        } else {
            request.getRequestDispatcher(request.getContextPath() + "/").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            try {
                request.setAttribute("user", new UserDao().getUserById(((User) request.getSession().getAttribute("user")).getId()));
                request.setAttribute("ingridients", IngridientDao.getAllIngridients());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            request.getRequestDispatcher("views/createCocktail.ftl").forward(request, response);
        } else {
            request.getRequestDispatcher(request.getContextPath() + "/").forward(request, response);
        }
    }
}
