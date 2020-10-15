package utilites;
/* <servlet>
        <servlet-name>freemarker</servlet-name>
        <servlet-class>freemarker.ext.servlet.FreemarkerServlet</servlet-class>


        <init-param>
            <param-name>TemplatePath</param-name>
            <param-value>/</param-value>
        </init-param>
        <init-param>
            <param-name>NoCache</param-name>
            <param-value>true</param-value>
        </init-param>
        <init-param>
            <param-name>ContentType</param-name>
            <param-value>text/html; charset=UTF-8</param-value>
        </init-param>


        <init-param>
            <param-name>template_update_delay</param-name>
            <param-value>0</param-value>
        </init-param>
        <init-param>
            <param-name>default_encoding</param-name>
            <param-value>ISO-8859-1</param-value>
        </init-param>
        <init-param>
            <param-name>number_format</param-name>
            <param-value>0.##########</param-value>
        </init-param>

        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>freemarker</servlet-name>
        <url-pattern>*.ftl</url-pattern>
        <url-pattern>*.ftlh</url-pattern>
    </servlet-mapping>


    <security-constraint>
        <web-resource-collection>
            <web-resource-name>FreeMarker MVC Views</web-resource-name>
            <url-pattern>*.ftl</url-pattern>
        </web-resource-collection>
        <auth-constraint>
        </auth-constraint>
    </security-constraint>*/
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
