package DAO;

import Entities.User;
import utilites.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {
    public User getUserById(Integer id){
        Connection con = DBConnector.createConnection();
        String query = "select * from user where id=" + id;
        System.out.println(query);
        User res = new User();
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                res.setId(id);
                res.setInformation(resultSet.getString("information"));
                res.setRating(resultSet.getInt("rate"));
                res.setName(resultSet.getString("name"));
                res.setEmail(resultSet.getString("email"));
                res.setLogin(resultSet.getString("login"));
                System.out.println(res.getName());
                res.setImg(resultSet.getString("img"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
