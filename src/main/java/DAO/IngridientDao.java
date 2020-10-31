package DAO;

import Entities.Cocktail;
import Entities.Ingridient;
import utilites.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IngridientDao {
    public List<Cocktail> getCoctailsByIngridient(Ingridient ingridient) throws SQLException {
        int idIng = ingridient.getId();
        Connection con = null;
        ResultSet resultSet = null;
        List<Cocktail> list = new ArrayList<>();
        try {
            con = DBConnector.createConnection();
            PreparedStatement ps = con.prepareStatement("select cocktid,ingid from recipie where ingid=?");
            ps.setInt(1, idIng);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int cocktid = resultSet.getInt("cocktid");
                Connection con2 = DBConnector.createConnection();
                PreparedStatement ps2 = con2.prepareStatement("select * from cocktail where id=?");
                ps2.setInt(1, cocktid);
                ResultSet finalSet = ps2.executeQuery();
                while (finalSet.next()) {
                    Cocktail cocktail = new Cocktail();
                    cocktail.setRating(finalSet.getInt("rating"));
                    cocktail.setInf(finalSet.getString("information"));
                    cocktail.setId(finalSet.getInt("id"));
                    cocktail.setImg(finalSet.getString("img"));
                    cocktail.setName(finalSet.getString("name"));
                    cocktail.setSmallImg(finalSet.getString("smallImg"));
                    cocktail.setAuthor_id(finalSet.getInt("author_id"));
                    list.add(cocktail);
                }
            }
            resultSet.close();
            ps.close();
            con.close();
            return list;
        } catch (SQLException e) {
            if (resultSet != null)
                resultSet.close();
            if (con != null)
                con.close();
            e.printStackTrace();
        }
        return list;
    }

    public static List<Ingridient> getAllIngridients() throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Ingridient> list = new ArrayList<>();
        try {
            con = DBConnector.createConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery("select * from ingridient");
            while (resultSet.next()) {
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
            resultSet.close();
            statement.close();
            con.close();
            return list;
        } catch (SQLException e) {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
            if (con != null)
                con.close();
            e.printStackTrace();
        }
        return list;
    }

    public static Ingridient getIngredientById(Integer id) {
        Ingridient res = new Ingridient();
        try {
            Connection con = DBConnector.createConnection();
            PreparedStatement statement = con.prepareStatement("select * from ingridient where id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                res.setId(id);
                res.setInf(resultSet.getString("information"));
                res.setImg(resultSet.getString("img"));
                res.setName(resultSet.getString("name"));
                res.setSmallImg(resultSet.getString("smallImg"));
                res.setContent_type(resultSet.getString("content_type"));
            }
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<Ingridient> findIngridients(String search) throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Ingridient> list = new ArrayList<>();
        try {
            con = DBConnector.createConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery("select * from ingridient");
            List<Ingridient> ings = new ArrayList<>();
            while (resultSet.next()) {
                Ingridient ingridient = new Ingridient();
                ingridient.setId(resultSet.getInt("id"));
                ingridient.setName(resultSet.getString("name"));
                ingridient.setSmallImg(resultSet.getString("smallImg"));
                ingridient.setImg(resultSet.getString("img"));
                ingridient.setContent_type(resultSet.getString("content_type"));
                ingridient.setInf(resultSet.getString("information"));
                ings.add(ingridient);
            }
            System.out.println(search);
            Pattern pattern = Pattern.compile(search.toLowerCase());
            for (Ingridient c : ings) {
                Matcher matcher = pattern.matcher(c.getName().toLowerCase());
                if (matcher.find()) {
                    list.add(c);
                }
            }
            resultSet.close();
            statement.close();
            con.close();
            return list;
        } catch (SQLException e) {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
            if (con != null)
                con.close();
            e.printStackTrace();
        }
        return list;
    }
}
