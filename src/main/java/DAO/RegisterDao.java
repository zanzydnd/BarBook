package DAO;

import Entities.User;
import utilites.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterDao {
    public String registerUser(User user)
    {
        String name = user.getName();
        String email = user.getEmail();
        String login = user.getLogin();
        String password = user.getPassword();
        String img = user.getImg();
        String information = user.getInformation();
        Integer favCocktail = user.getFavCockt();

        Connection con = null;
        PreparedStatement preparedStatement = null;
        try
        {
            con = DBConnector.createConnection();
            String query = "insert into user(login,name,password,email,img,favouriteCocktail_id," +
                    "information) values (?,?,?,?,?,?,?)"; //Insert user details into the table 'USERS'
            preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5,img);
            if(favCocktail != null) {
                preparedStatement.setString(6, Integer.toString(favCocktail));
            }
            else{
                preparedStatement.setString(6, null);
            }
            preparedStatement.setString(7,information);

            int i= preparedStatement.executeUpdate();

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
