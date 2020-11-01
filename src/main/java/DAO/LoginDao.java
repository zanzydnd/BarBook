package DAO;

import Entities.User;
import org.apache.commons.dbutils.DbUtils;
import utilites.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDao {
    public User authenticateUser(User user) throws SQLException {
        String userName = user.getLogin();
        String password = user.getPassword();

        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String userNameDB = "";
        String passwordDB = "";

        try {
            con = DBConnector.createConnection(); //Fetch database connection object
            statement = con.createStatement(); //Statement is used to write queries. Read more about it.
            resultSet = statement.executeQuery("select * from user"); //the table name is users and userName,password are columns. Fetching all the records and storing in a resultSet.

            while (resultSet.next()) // Until next row is present otherwise it return false
            {
                userNameDB = resultSet.getString("login"); //fetch the values present in database
                passwordDB = resultSet.getString("password");
                User res = new User();
                if (userName.equals(userNameDB) && password.equals(passwordDB)) {
                    res.setInformation(resultSet.getString("information"));
                    res.setId(resultSet.getInt("id"));
                    res.setName(resultSet.getString("name"));
                    res.setPassword(resultSet.getString("password"));
                    res.setEmail(resultSet.getString("email"));
                    res.setLogin(resultSet.getString("login"));
                    res.setImg(resultSet.getString("img"));
                    DbUtils.closeQuietly(resultSet);
                    DbUtils.closeQuietly(statement);
                    DbUtils.closeQuietly(con);
                    return res; ////If the user entered values are already present in the database, which means user has already registered so return a SUCCESS message.
                }
            }
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(con);
        } catch (SQLException e) {
            if (resultSet != null)
                DbUtils.closeQuietly(resultSet);
            if (statement != null)
                DbUtils.closeQuietly(statement);
            if (con != null)
                DbUtils.closeQuietly(con);
            e.printStackTrace();
        }
        return null;
    }
}
