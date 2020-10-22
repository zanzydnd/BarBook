package Servlets;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ImageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String file_path = request.getParameter("image_path");
        response.setContentType("image/jpg");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        File file = new File(file_path);
        InputStream inp = new FileInputStream(file);
        OutputStream os = response.getOutputStream();;
        byte[] mass = new byte[2048];
        int b = 0;
        while ((b = inp.read(mass))!= -1){
            os.write(mass,0,b);
        }
        os.close();
        inp.close();
    }
}
