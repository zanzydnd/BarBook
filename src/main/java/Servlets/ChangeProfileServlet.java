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
import java.nio.file.Path;
import java.nio.file.Paths;

@MultipartConfig
public class ChangeProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String info = request.getParameter("info");
        System.out.println("info " + info);
        String name = request.getParameter("name");
        System.out.println("name" + name);
       // Integer fav_cocktId = Integer.parseInt(request.getParameter("fav_cockt"));
        Part filePart = request.getPart("image");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String pathFile = "";
        String absPath = "";
        if(fileName.length() > 1) {
            InputStream fileContent = filePart.getInputStream();
            pathFile =  "img\\" +  ((User)request.getSession().getAttribute("user")).getLogin() + fileName;
            absPath = "C:\\Users\\Даня\\IdeaProjects\\BarBook\\BarBook\\src\\main\\webapp\\views\\"  + pathFile;
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
        Integer user_id = ((User)request.getSession().getAttribute("user")).getId();
        System.out.println("id" + user_id);
        UserDao dao = new UserDao();
        dao.updateDB(info,name,pathFile,user_id);
        response.sendRedirect(request.getContextPath() + "/profile");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rqDispatcher = request.getRequestDispatcher("views/profile_change.jsp");
        rqDispatcher.forward(request, response);
    }
}
