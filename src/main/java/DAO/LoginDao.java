package DAO;

import Entities.User;
import utilites.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDao {
    public String authenticateUser(User user)
    {
        String userName = user.getLogin();
        String password = user.getPassword();

        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String userNameDB = "";
        String passwordDB = "";

        try
        {
            con = DBConnector.createConnection(); //Fetch database connection object
            statement = con.createStatement(); //Statement is used to write queries. Read more about it.
            resultSet = statement.executeQuery("select login,password from user"); //the table name is users and userName,password are columns. Fetching all the records and storing in a resultSet.

            while(resultSet.next()) // Until next row is present otherwise it return false
            {
                userNameDB = resultSet.getString("login"); //fetch the values present in database
                passwordDB = resultSet.getString("password");

                if(userName.equals(userNameDB) && password.equals(passwordDB))
                {
                    return "SUCCESS"; ////If the user entered values are already present in the database, which means user has already registered so return a SUCCESS message.
                }
            }// Return appropriate message in case of failure
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return "Invalid user credentials";
    }
}
