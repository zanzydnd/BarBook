package DAO;

import Entities.Cocktail;
import Entities.Ingridient;
import utilites.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngridientDao {
    public List<Cocktail> getCoctailsByIngridient(Ingridient ingridient){
        int idIng = ingridient.getId();
        Connection con;
        ResultSet resultSet;
        List<Cocktail> list = new ArrayList<>();
        try{
            con = DBConnector.createConnection();
            PreparedStatement ps = con.prepareStatement("select cocktid,ingid from recipie where ingid=?");
            ps.setInt(1,idIng);
            resultSet = ps.executeQuery();
            while(resultSet.next()){
                    int cocktid = resultSet.getInt("cocktid");
                    Connection con2 = DBConnector.createConnection();
                    PreparedStatement ps2 = con2.prepareStatement("select * from cocktail where id=?");
                    ps2.setInt(1,cocktid);
                    ResultSet finalSet = ps2.executeQuery();
                    while(finalSet.next()){
                        Cocktail cocktail = new Cocktail();
                        cocktail.setRating(finalSet.getInt("rating"));
                        cocktail.setInf(finalSet.getString("information"));
                        cocktail.setId(finalSet.getInt("id"));
                        cocktail.setImg(finalSet.getString("img"));
                        cocktail.setName(finalSet.getString("name"));
                        cocktail.setSmallImg(finalSet.getString("smallImg"));
                        list.add(cocktail);
                    }
            }
            con.close();
            return list;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public static List<Ingridient> getAllIngridients(){
        Connection con;
        Statement statement;
        ResultSet resultSet;
        List<Ingridient> list = new ArrayList<>();
        try{
            con = DBConnector.createConnection();
            statement = con.createStatement();
            resultSet =statement.executeQuery("select * from ingridient");
            while(resultSet.next()){
                String name = resultSet.getString("name");
                Integer id = resultSet.getInt("id");
                String inf = resultSet.getString("information");
                String img = resultSet.getString("img");
                Ingridient ingridient = new Ingridient();
                ingridient.setImg(img);
                ingridient.setInf(inf);
                ingridient.setSmallImg(resultSet.getString("smallImg"));
                ingridient.setId(id);
                ingridient.setName(name);
                ingridient.setContent_type(resultSet.getString("content_type"));
                list.add(ingridient);
            }
            con.close();
            return list;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public static Ingridient getIngredientById(Integer id){
        Ingridient res = new Ingridient();
        try {
            Connection con = DBConnector.createConnection();
            PreparedStatement statement = con.prepareStatement("select * from ingridient where id=?");
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                res.setId(id);
                res.setInf(resultSet.getString("information"));
                res.setImg(resultSet.getString("img"));
                res.setName(resultSet.getString("name"));
                res.setSmallImg(resultSet.getString("smallImg"));
                res.setContent_type(resultSet.getString("content_type"));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
