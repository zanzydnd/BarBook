package DAO;

import Entities.Cocktail;
import Entities.Ingridient;
import utilites.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CocktailDao {
    public List<Cocktail> getCocktails() {
        Connection con;
        Statement statement;
        ResultSet resultSet;
        Integer idDB;
        List<Cocktail> list = new ArrayList<>();
        try {
            con = DBConnector.createConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery("select * from cocktail");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Integer id = resultSet.getInt("id");
                String inf = resultSet.getString("information");
                String img = resultSet.getString("img");
                Integer rating = resultSet.getInt("rating");
                Cocktail cocktail = new Cocktail();
                cocktail.setName(name);
                cocktail.setId(id);
                cocktail.setImg(img);
                cocktail.setInf(inf);
                cocktail.setRating(rating);
                list.add(cocktail);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Ingridient> getRecepie(Cocktail cocktail) {
        String name = cocktail.getName();
        Integer id = cocktail.getId();
        String inf = cocktail.getInf();
        Connection con;
        Statement statement;
        ResultSet resultSet;
        Integer idDB;
        List<Ingridient> list = new ArrayList<>();
        try {
            con = DBConnector.createConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery("select cocktid,ingid from recipie where cocktid=" + id);
            while (resultSet.next()) {
                Connection con2 = DBConnector.createConnection();
                Statement statement2 = con2.createStatement();
                int ingId = resultSet.getInt("ingid");
                ResultSet finalSet = statement2.executeQuery("select * from ingridient where id=" + ingId);
                Ingridient ingridient = new Ingridient();
                while (finalSet.next()) {
                    ingridient.setId(finalSet.getInt("id"));
                    ingridient.setName(finalSet.getString("name"));
                    ingridient.setInf(finalSet.getString("information"));
                    ingridient.setImg(finalSet.getString("img"));
                    list.add(ingridient);
                }
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Cocktail getCocktailById(Integer id) {
        Cocktail res = new Cocktail();
        try {
            Connection con = DBConnector.createConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from cocktail where id=" + id);
            while (resultSet.next()) {
                res.setId(id);
                res.setName(resultSet.getString("name"));
                res.setImg(resultSet.getString("img"));
                res.setInf(resultSet.getString("information"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
