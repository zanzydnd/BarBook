package DAO;

import Entities.Cocktail;
import Entities.Ingridient;
import utilites.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            resultSet = statement.executeQuery("select * from cocktail order by rating DESC");
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

    public Map<Integer,String> getCocktailsIdByName(String name){
        Connection con;
        Statement statement;
        ResultSet resultSet;
        Map<Integer,String> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        try {
            System.out.println(name);
            con = DBConnector.createConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery("select id,name from cocktail");
            List<Cocktail> cockts = new ArrayList<>();
            while (resultSet.next()) {
               Cocktail cockt = new Cocktail();
               cockt.setId(resultSet.getInt("id"));
               cockt.setName(resultSet.getString("name"));
               cockts.add(cockt);
            }
            Pattern pattern = Pattern.compile(name.toLowerCase());
            for(Cocktail c : cockts){
                Matcher matcher = pattern.matcher(c.getName().toLowerCase());
                if(matcher.find()){

                    map.put(c.getId(),c.getName());
                    System.out.println("ya zashel v pattern");
                    System.out.println(c.getId());
                }
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
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
                res.setRecipie(resultSet.getString("recipie_info"));
                res.setRating(resultSet.getInt("rating"));
                res.setName(resultSet.getString("name"));
                res.setImg(resultSet.getString("img"));
                res.setInf(resultSet.getString("information"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void newLike(Integer cockt_id, Integer user_id) throws SQLException {
        boolean flag = true;
        Connection con1 = DBConnector.createConnection();
        Statement statement = con1.createStatement();
        ResultSet resultSet = statement.executeQuery("select id_user , id_cocktail " +
                "from cocktails_likes where id_cocktail =" + cockt_id);
        while (resultSet.next()) {
            if (resultSet.getInt("id_user") == user_id) {
                flag = false;
                break;
            }
        }

        if (flag) {
            try {
                Connection con = DBConnector.createConnection();
                PreparedStatement statement2 = con.prepareStatement("insert into cocktails_likes(id_user,id_cocktail) values(?,?)");
                statement2.setInt(1, user_id);
                statement2.setInt(2, cockt_id);
                statement2.executeLargeUpdate();
                Cocktail cocktail = this.getCocktailById(cockt_id);
                int rate = cocktail.getRating() + 1;
                Connection con2 = DBConnector.createConnection();
                String query = "update cocktail set rating=" + rate + " where id =" + cockt_id;
                Statement statement1 = con2.createStatement();
                cocktail.setRating(rate);
                statement1.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            throw new SQLException();
        }
    }
}
