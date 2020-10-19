package DAO;

import Entities.User;
import utilites.DBConnector;

import java.sql.*;

public class UserDao {
    public User getUserById(Integer id){
        Connection con = DBConnector.createConnection();
        String query = "select * from user where id=?";
        User res = new User();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                res.setId(id);
                res.setInformation(resultSet.getString("information"));
                res.setRating(resultSet.getInt("rate"));
                res.setName(resultSet.getString("name"));
                res.setEmail(resultSet.getString("email"));
                res.setLogin(resultSet.getString("login"));
                res.setFavCockt(resultSet.getInt("favouriteCocktail_id"));
                res.setImg(resultSet.getString("img"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void updateDB(String info , String name, String pathFile , Integer id){
        System.out.println("ya v update");
        Connection con = DBConnector.createConnection();
        try {
            if (!pathFile.equals("")) {
                System.out.println("ya v ife");
                String query = "update user set information = ? , name = ? ,img = ? where id=" + id;
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1,info);
                ps.setString(2,name);
                //ps.setInt(3,fav_cockt);
                ps.setString(3,pathFile);
                ps.executeUpdate();
            } else {
                String query = "update user set  information = ? , name = ? where id=" + id;
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1,info);
                ps.setString(2,name);
                //ps.setInt(3,fav_cockt);
                ps.executeUpdate();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
