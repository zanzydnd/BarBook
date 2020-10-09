package utilites;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
    public static Connection createConnection(){
        Connection con = null;
        final String url = "jdbc:mysql://localhost:3306/barbook_final?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";
        String username = "root";
        String password = "root";
        try{
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
            }
            catch (ClassNotFoundException e){
                e.printStackTrace();
            }
            con = DriverManager.getConnection(url,username,password);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
