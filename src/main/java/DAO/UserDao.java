package DAO;

import Entities.Cocktail;
import Entities.User;
import utilites.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public User getUserById(Integer id){
        Connection con = DBConnector.createConnection();
        String query = "select * from user where id=?";
        User res = new User();
        CocktailDao dao = new CocktailDao();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                res.setId(id);
                res.setInformation(resultSet.getString("information"));
                res.setName(resultSet.getString("name"));
                res.setEmail(resultSet.getString("email"));
                res.setLogin(resultSet.getString("login"));
                res.setImg(resultSet.getString("img"));
            }
            Connection connection = DBConnector.createConnection();
            PreparedStatement statement = connection.prepareStatement("select * from user_favourite_cocktail where user_id = ?");
            statement.setInt(1,id);
            ResultSet resultSet1 = statement.executeQuery();
            List<Cocktail> list = new ArrayList<>();
            while(resultSet1.next()){
                Cocktail cocktail = dao.getCocktailById(resultSet1.getInt("cocktail_id"));
                list.add(cocktail);
            }
            res.setFavCocktails(list);
            con.close();
            connection.close();
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
                ps.setString(3,pathFile);
                ps.executeUpdate();
                con.close();
            } else {
                String query = "update user set  information = ? , name = ? where id=" + id;
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1,info);
                ps.setString(2,name);
                ps.executeUpdate();
                con.close();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers(){
        Connection con;
        Statement statement;
        ResultSet resultSet;
        Integer idDB;
        List<User> list = new ArrayList<>();
        try {
            con = DBConnector.createConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery("select id from user");
            while (resultSet.next()) {
                User user = new User();
                user = this.getUserById(resultSet.getInt("id"));
                list.add(user);
            }
            con.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }
    }
}
