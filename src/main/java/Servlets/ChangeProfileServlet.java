package Servlets;

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
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;

@MultipartConfig
public class ChangeProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.setCharacterEncoding("UTF-8");
            try {
                request.setAttribute("user", new UserDao().getUserById(((User) request.getSession().getAttribute("user")).getId()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (request.getParameter("text") != null && request.getParameter("name") != null && request.getPart("image") != null) {
                String info = request.getParameter("text");
                System.out.println("info " + info);
                String name = request.getParameter("name");
                System.out.println("name" + name);
                Part filePart = request.getPart("image");
                System.out.println(filePart);
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String pathFile = "";
                String absPath = "";
                if (fileName.length() > 1) {
                    InputStream fileContent = filePart.getInputStream();
                    pathFile = "img\\user\\" + ((User) request.getSession().getAttribute("user")).getLogin() + UUID.randomUUID().toString() + fileName;
                    absPath = "C:\\Users\\Даня\\IdeaProjects\\BarBook\\BarBook\\src\\main\\webapp\\views\\" + pathFile;
                    File file = new File(absPath);
                    boolean created = file.createNewFile();
                    OutputStream os = new FileOutputStream(absPath);
                    byte[] b = new byte[2048];
                    int l;
                    while ((l = fileContent.read(b)) != -1) {
                        os.write(b, 0, l);
                    }
                    fileContent.close();
                    os.close();
                }
                Integer user_id = ((User) request.getSession().getAttribute("user")).getId();
                System.out.println("id" + user_id);
                UserDao dao = new UserDao();
                pathFile = absPath.replace('\\', '/');
                try {
                    if(name.replace(" ","").equals("")){
                        name = ((User) request.getAttribute("user")).getName();
                    }
                    if(info.replace(" ","").equals("")){
                        info = ((User) request.getAttribute("user")).getInformation();
                    }
                    dao.updateDB(info, name, pathFile, user_id);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                response.sendRedirect(request.getContextPath() + "/profile?id=self");
            }
            else{
                request.setAttribute("errMsg","Что-то не так");
                this.doGet(request,response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            try {
                request.setAttribute("user", new UserDao().getUserById(((User) request.getSession().getAttribute("user")).getId()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            RequestDispatcher rqDispatcher = request.getRequestDispatcher("views/profile_change.ftl");
            rqDispatcher.forward(request, response);
        }
    }
}
