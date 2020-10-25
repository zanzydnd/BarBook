package DAO;

import Entities.User;
import utilites.DBConnector;
import utilites.Hashing;

import java.sql.*;

public class RegisterDao {
    public String registerUser(User user)
    {
        String name = user.getName();
        String email = user.getEmail();
        String login = user.getLogin();
        String password = Hashing.md5Custom(user.getPassword());
        String img = user.getImg();
        String information = user.getInformation();

        Connection con = null;
        PreparedStatement preparedStatement = null;
        try
        {
            con = DBConnector.createConnection();
            Connection con2 = DBConnector.createConnection();
            PreparedStatement ps = con2.prepareStatement("select login from user where login=?");
            ps.setString(1,login);
            ResultSet resultSet = ps.executeQuery();
            //Statement statement = con2.createStatement();
            //ResultSet resultSet = statement.executeQuery("select login from user where login='" + login + "'");
            while(resultSet.next()){
                String s = resultSet.getString("login");
                if (s.equals(login))
                    throw new SQLException();
            }
            String query = "insert into user(login,name,password,email," +
                    "information) values (?,?,?,?,?)"; //Insert user details into the table 'USERS'
            preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5,information);

            int i= preparedStatement.executeUpdate();
            con.close();
            if (i!=0)  //Just to ensure data has been inserted into the database
                return "SUCCESS";
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("Oops.. Something went wrong there..!");
        return "Oops.. Something went wrong there..!";  // On failure, send a message from here.
    }
}
