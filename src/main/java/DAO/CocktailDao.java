package DAO;

import Entities.Cocktail;
import Entities.Ingridient;
import org.apache.commons.dbutils.DbUtils;
import utilites.DBConnector;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CocktailDao {

    static DBConnector db = new DBConnector();

    public List<Cocktail> getCocktails() throws SQLException {
        ResultSet resultSet = null;
        List<Cocktail> list = new ArrayList<>();
        try (Connection con = DBConnector.createConnection();
             Statement statement = con.createStatement()) {
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
                cocktail.setSmallImg(resultSet.getString("smallImg"));
                cocktail.setContent_type(resultSet.getString("content_type"));
                cocktail.setRating(rating);
                cocktail.setAuthor_id(resultSet.getInt("author_id"));
                cocktail.setIngridients(this.getRecepie(cocktail));
                list.add(cocktail);
            }
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(con);
            return list;
        } catch (SQLException e) {
            if (resultSet != null)
                DbUtils.closeQuietly(resultSet);
            e.printStackTrace();
        }
        return list;
    }


    public List<Cocktail> getCocktailsByTags(String[] tags) throws SQLException {
        Integer[] cocktails = null;
        List<Integer> first = null;
        List<Integer> second = null;
        Set<Integer> s1 = null;
        Set<Integer> s2 = null;
        ResultSet resultSetForTag = null;
        List<Cocktail> list = new ArrayList<>();
        ResultSet resSetForCockt = null;
        try (Connection con = DBConnector.createConnection();
             Connection con2 = DBConnector.createConnection();
             PreparedStatement statement = con.prepareStatement("select id from tags where tag=?");
             PreparedStatement statement2 = con2.prepareStatement("select cocktail_id from cocktails_tags where tag_id= ?")
        ) {
            Map<Integer, List<Integer>> map = new HashMap<>();
            for (int i = 0; i < tags.length; i++) {
                statement.setString(1, tags[i]);
                resultSetForTag = statement.executeQuery();
                Integer tag_id = 0;
                while (resultSetForTag.next()) {
                    tag_id = resultSetForTag.getInt("id");
                }
                statement2.setInt(1, tag_id);
                resSetForCockt = statement2.executeQuery();
                List<Integer> ar = new ArrayList<>();
                while (resSetForCockt.next()) {
                    ar.add(resSetForCockt.getInt("cocktail_id"));
                }
                map.put(tag_id, ar);
                Integer curr = tag_id;
                if (i > 1) {
                    s2 = new HashSet<>(map.get(curr));
                    s1.retainAll(s2);
                } else if (i == 0) {
                    first = map.get(curr);
                    s1 = new HashSet<>(first);
                } else {
                    second = map.get(curr);
                    s1 = new HashSet<>(first);
                    s2 = new HashSet<>(second);
                    s1.retainAll(s2);
                }
                DbUtils.closeQuietly(resultSetForTag);
                DbUtils.closeQuietly(resSetForCockt);
                DbUtils.closeQuietly(statement2);
                DbUtils.closeQuietly(statement);
                DbUtils.closeQuietly(con2);
                DbUtils.closeQuietly(con);
            }
            cocktails = s1.toArray(new Integer[s1.size()]);
            for (Integer id : cocktails) {
                list.add(this.getCocktailById(id));
            }
            return list;
        } catch (SQLException e) {
            if (resultSetForTag != null)
                DbUtils.closeQuietly(resultSetForTag);
            if (resSetForCockt != null)
                DbUtils.closeQuietly(resSetForCockt);
        }
        return list;
    }

    public List<Cocktail> getCocktailsByTagsAndName(String name, String[] tags) throws SQLException {
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

    public List<Cocktail> getCocktailsIdByName(String name) throws SQLException {
        ResultSet resultSet = null;
        List<Cocktail> list = new ArrayList<>();
        try (Connection con = DBConnector.createConnection();
             Statement statement = con.createStatement()) {
            resultSet = statement.executeQuery("select id,name from cocktail");
            List<Cocktail> cockts = new ArrayList<>();
            while (resultSet.next()) {
                Cocktail cockt = new Cocktail();
                cockt.setId(resultSet.getInt("id"));
                cockt.setName(resultSet.getString("name"));
                cockt.setIngridients(this.getRecepie(cockt));
                cockts.add(cockt);
            }
            Pattern pattern = Pattern.compile(name.toLowerCase());
            for (Cocktail c : cockts) {
                Matcher matcher = pattern.matcher(c.getName().toLowerCase());
                if (matcher.find()) {
                    list.add(this.getCocktailById(c.getId()));
                }
            }
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(con);
            return list;
        } catch (SQLException e) {
            if (resultSet != null)
                DbUtils.closeQuietly(resultSet);
            e.printStackTrace();
        }
        return list;
    }

    public List<Ingridient> getRecepie(Cocktail cocktail) throws SQLException {
        Integer id = cocktail.getId();
        ResultSet resultSet = null;
        List<Ingridient> list = new ArrayList<>();
        ResultSet finalSet = null;
        try (Connection con = DBConnector.createConnection();
             PreparedStatement statement = con.prepareStatement("select cocktid,ingid from recipie where cocktid=?");
             Connection con2 = DBConnector.createConnection();
             PreparedStatement statement2 = con2.prepareStatement("select * from ingridient where id=?");
        ) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int ingId = resultSet.getInt("ingid");
                statement2.setInt(1, ingId);
                finalSet = statement2.executeQuery();
                Ingridient ingridient = new Ingridient();
                while (finalSet.next()) {
                    ingridient.setId(finalSet.getInt("id"));
                    ingridient.setName(finalSet.getString("name"));
                    ingridient.setInf(finalSet.getString("information"));
                    ingridient.setImg(finalSet.getString("img"));
                    ingridient.setSmallImg(finalSet.getString("smallImg"));
                    ingridient.setContent_type(finalSet.getString("content_type"));
                    list.add(ingridient);
                }
            }
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(finalSet);
            DbUtils.closeQuietly(statement2);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(con);
            DbUtils.closeQuietly(con2);
            return list;
        } catch (SQLException e) {
            if (resultSet != null)
                DbUtils.closeQuietly(resultSet);
            if (finalSet != null)
                DbUtils.closeQuietly(finalSet);
            e.printStackTrace();
        }
        return list;
    }

    public Cocktail getCocktailById(Integer id) throws SQLException {
        Cocktail res = new Cocktail();
        ResultSet resultSet = null;
        try (Connection con = DBConnector.createConnection();
             PreparedStatement statement = con.prepareStatement("select * from cocktail where id=?")
        ) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                res.setId(id);
                res.setRecipie(resultSet.getString("recipie_info"));
                res.setRating(resultSet.getInt("rating"));
                res.setName(resultSet.getString("name"));
                res.setImg(resultSet.getString("img"));
                res.setInf(resultSet.getString("information"));
                res.setSmallImg(resultSet.getString("smallImg"));
                res.setContent_type(resultSet.getString("content_type"));
                res.setAuthor_id(resultSet.getInt("author_id"));
            }
            res.setIngridients(this.getRecepie(res));
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(con);
        } catch (SQLException e) {
            if (resultSet != null)
                DbUtils.closeQuietly(resultSet);
            e.printStackTrace();
        }
        return res;
    }

    public void newLike(Integer cockt_id, Integer user_id) throws SQLException {
        boolean flag = true;


        try (Connection con1 = DBConnector.createConnection();
             PreparedStatement preparedStatement = con1.prepareStatement("select id_user , id_cocktail " +
                     "from cocktails_likes where id_cocktail =?")) {
            preparedStatement.setInt(1, cockt_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("id_user") == user_id) {
                    flag = false;
                    break;
                }
            }
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(preparedStatement);
            DbUtils.closeQuietly(con1);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (flag) {
            try (Connection con = DBConnector.createConnection();
                 PreparedStatement statement2 = con.prepareStatement("insert into cocktails_likes(id_user,id_cocktail) values(?,?)");
                 Connection con2 = DBConnector.createConnection();
                 PreparedStatement ps = con2.prepareStatement("update cocktail set rating=? where id =?");
            ) {
                statement2.setInt(1, user_id);
                statement2.setInt(2, cockt_id);
                statement2.executeLargeUpdate();
                Cocktail cocktail = this.getCocktailById(cockt_id);
                int rate = cocktail.getRating() + 1;
                ps.setInt(1, rate);
                ps.setInt(2, cockt_id);
                cocktail.setRating(rate);
                ps.executeUpdate();
                DbUtils.closeQuietly(ps);
                DbUtils.closeQuietly(statement2);
                DbUtils.closeQuietly(con2);
                DbUtils.closeQuietly(con);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            throw new SQLException();
        }
    }

    public void favCockt(Integer cockt_id, Integer user_id) throws SQLException {
        boolean flag = true;

        try(
            Connection con1 = DBConnector.createConnection();
            PreparedStatement preparedStatement1 = con1.prepareStatement("select user_id , cocktail_id " +
                    "from user_favourite_cocktail where cocktail_id =?");) {
            preparedStatement1.setInt(1, cockt_id);
            ResultSet resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("user_id") == user_id) {
                    flag = false;
                    break;
                }
            }
            if (resultSet != null)
                DbUtils.closeQuietly(resultSet);
            if (preparedStatement1 != null)
                DbUtils.closeQuietly(preparedStatement1);
            if (con1 != null)
                DbUtils.closeQuietly(con1);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        if (flag) {
            String query = "insert into user_favourite_cocktail set user_id = ? , cocktail_id = ?";
            try(   Connection con = DBConnector.createConnection();
                   PreparedStatement ps = con.prepareStatement(query);
                    ){
                ps.setInt(1, user_id);
                ps.setInt(2, cockt_id);
                ps.executeUpdate();
                DbUtils.closeQuietly(ps);
                DbUtils.closeQuietly(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new SQLException();
        }

    }

    public Integer newCocktail(String newName, String newInfo, String[] ingList, String[] recipie, String[] tags, Integer user_id, String pathFileBig, String pathFileSmall) throws SQLException {
        String rcp = "";
        for (int i = 0; i < recipie.length; i++) {
            rcp += recipie[i] + ";";
        }
        try(        Connection conToCocktail = DBConnector.createConnection();
                    PreparedStatement psCocktail = conToCocktail.prepareStatement("insert into cocktail set name = ? ," +
                            "information = ?, recipie_info = ?, author_id = ?, img = ? , smallImg = ?")
        ) {
            psCocktail.setString(1, newName);
            psCocktail.setString(2, newInfo);
            psCocktail.setString(3, rcp);
            psCocktail.setInt(4, user_id);
            psCocktail.setString(5, pathFileBig);
            psCocktail.setString(6, pathFileSmall);
            psCocktail.executeUpdate();
            DbUtils.closeQuietly(psCocktail);
            DbUtils.closeQuietly(conToCocktail);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Integer id = 0;



        try(Connection getIdCon = DBConnector.createConnection();
            PreparedStatement ps = getIdCon.prepareStatement("select id from cocktail where name= ?");
        ) {
            ps.setString(1, newName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(getIdCon);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try(Connection setRecCon = DBConnector.createConnection()) {
            for (int i = 0; i < ingList.length; i++) {
                PreparedStatement setRecPs = setRecCon.prepareStatement("insert into recipie set cocktid = ?, ingid=?");
                setRecPs.setInt(1, id);
                setRecPs.setInt(2, Integer.parseInt(ingList[i]));
                setRecPs.executeUpdate();
                DbUtils.closeQuietly(setRecPs);
            }
            DbUtils.closeQuietly(setRecCon);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        List<Integer> tagsList = new ArrayList<>();
        try(Connection forTagsCon = DBConnector.createConnection()
        ) {
            for (int i = 0; i < tags.length; i++) {
                PreparedStatement preparedStatementForTags = null;
                ResultSet rsForTags = null;
                if (!tags[i].equals("")) {
                    preparedStatementForTags = forTagsCon.prepareStatement("select id from tags where tag = ?");
                    preparedStatementForTags.setString(1, tags[i]);
                    rsForTags = preparedStatementForTags.executeQuery();
                    while (rsForTags.next()) {
                        tagsList.add(rsForTags.getInt("id"));
                    }
                }
                DbUtils.closeQuietly(forTagsCon);
                DbUtils.closeQuietly(rsForTags);
                DbUtils.closeQuietly(preparedStatementForTags);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try(Connection ferInsTagCon = DBConnector.createConnection()) {
            for (Integer tagid : tagsList) {
                PreparedStatement psInsTag = ferInsTagCon.prepareStatement("insert into cocktails_tags set cocktail_id=? , tag_id=?");
                psInsTag.setInt(1, id);
                psInsTag.setInt(2, tagid);
                psInsTag.executeUpdate();
                DbUtils.closeQuietly(psInsTag);
            }
            DbUtils.closeQuietly(ferInsTagCon);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void upgrade(Integer id, String newName, String newText, String img, String smallImg) throws SQLException {
        PreparedStatement ps = null;
        try(Connection con = DBConnector.createConnection();) {
            if (img.equals("") && smallImg.equals("")) {
                ps = con.prepareStatement("update cocktail set name= ? ,information = ? where id=?");
                ps.setString(1, newName);
                ps.setString(2, newText);
                ps.setInt(3, id);
                ps.executeUpdate();
            } else if (!img.equals("") && smallImg.equals("")) {
                ps = con.prepareStatement("update cocktail set name= ? ,information = ?, img=? where id=?");
                ps.setString(1, newName);
                ps.setString(2, newText);
                ps.setString(3, img);
                ps.setInt(4, id);
                ps.executeUpdate();
            } else if (img.equals("") && !smallImg.equals("")) {
                ps = con.prepareStatement("update cocktail set name= ? ,information = ?, smallImg=? where id=?");
                ps.setString(1, newName);
                ps.setString(2, newText);
                ps.setString(3, smallImg);
                ps.setInt(4, id);
                ps.executeUpdate();
            } else {
                ps = con.prepareStatement("update cocktail set name= ? ,information = ?, img=?,smallImg=? where id=?");
                ps.setString(1, newName);
                ps.setString(2, newText);
                ps.setString(3, img);
                ps.setString(4, smallImg);
                ps.setInt(5, id);
                ps.executeUpdate();
            }
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(con);
        } catch (SQLException e) {
            DbUtils.closeQuietly(ps);
            e.printStackTrace();
        }
    }
}