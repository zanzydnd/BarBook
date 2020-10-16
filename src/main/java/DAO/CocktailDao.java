package DAO;

import Entities.Cocktail;
import Entities.Ingridient;
import utilites.DBConnector;

import java.sql.*;
import java.util.*;
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


    public List<Cocktail> getCocktailsByTags(String[] tags) {
        Integer[] cocktails = null;
        Connection con;
        Statement statement;
        List<Integer> first = null;
        List<Integer> second = null;
        Set<Integer> s1 = null;
        Set<Integer> s2 = null;
        List<Cocktail> list = new ArrayList<>();
        try {
            Map<Integer, List<Integer>> map = new HashMap<>();
            for (int i = 0; i < tags.length; i++) {
                System.out.println("+");
                con = DBConnector.createConnection();
                Connection con2 = DBConnector.createConnection();
                statement = con.createStatement();
                Statement statement2 = con2.createStatement();
                System.out.println(tags[i]);
                String sql1 = "select id from tags where tag='" + tags[i]+ "'";
                ResultSet resultSetForTag = statement.executeQuery(sql1);
                Integer tag_id = 0;
                while(resultSetForTag.next()){
                    tag_id = resultSetForTag.getInt("id");
                }
                System.out.println("-");
                ResultSet resSetForCockt = statement2.executeQuery("select cocktail_id from cocktails_tags where tag_id=" + tag_id.toString());
                List<Integer> ar = new ArrayList<>();
                while (resSetForCockt.next()) {
                    System.out.println("айди коктейлей" + resSetForCockt.getInt("cocktail_id"));
                    ar.add(resSetForCockt.getInt("cocktail_id"));
                }
                System.out.println("ar" + ar);
                map.put(tag_id, ar);
                Integer curr = tag_id;
                if (i > 1) {
                    s2 = new HashSet<>(map.get(curr));
                    s1.retainAll(s2);
                    System.out.println(s1);
                } else if (i == 0) {
                    first = map.get(curr);
                    s1 = new HashSet<>(first);
                    System.out.println("s1" + s1);
                } else if(i==1){
                    second = map.get(curr);
                    System.out.println("second " + second);
                    s1 = new HashSet<>(first);
                    s2 = new HashSet<>(second);
                    s1.retainAll(s2);
                    System.out.println(s1);
                }
            }
            System.out.println("s1" + s1);
            System.out.println("asdadsas");
            cocktails =  s1.toArray(new Integer[s1.size()]);
            System.out.println(cocktails[0]);
            for (Integer id : cocktails) {
                System.out.println("в лист id" + id);
                list.add(this.getCocktailById(id));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Cocktail> getCocktailsByTagsAndName(String name, String[] tags) {
        List<Cocktail> c1 = this.getCocktailsIdByName(name);
        List<Cocktail> c2 = this.getCocktailsByTags(tags);
        List<Cocktail> res = new ArrayList<>();
        if (c1 == null || c2 == null)
            return null;
        for (int i = 0; i < c1.size(); i++) {
            int j = 0;
            if (c2.get(j).getId() == c1.get(i).getId()) {
                res.add(this.getCocktailById(c2.get(j).getId()));
            }
        }
        return res;
    }

    public List<Cocktail> getCocktailsIdByName(String name) {
        Connection con;
        Statement statement;
        ResultSet resultSet;
        List<Cocktail> list = new ArrayList<>();
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
            for (Cocktail c : cockts) {
                Matcher matcher = pattern.matcher(c.getName().toLowerCase());
                if (matcher.find()) {
                    list.add(this.getCocktailById(c.getId()));
                    System.out.println("ya zashel v pattern");
                    System.out.println(c.getId());
                }
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
        } else {
            throw new SQLException();
        }
    }
}
