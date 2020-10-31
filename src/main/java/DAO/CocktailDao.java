package DAO;

import Entities.Cocktail;
import Entities.Ingridient;
import utilites.DBConnector;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CocktailDao {

    public List<Cocktail> getCocktails() throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
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
                cocktail.setSmallImg(resultSet.getString("smallImg"));
                cocktail.setContent_type(resultSet.getString("content_type"));
                cocktail.setRating(rating);
                cocktail.setAuthor_id(resultSet.getInt("author_id"));
                cocktail.setIngridients(this.getRecepie(cocktail));
                list.add(cocktail);
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


    public List<Cocktail> getCocktailsByTags(String[] tags) throws SQLException {
        Integer[] cocktails = null;
        Connection con = null;
        Statement statement = null;
        List<Integer> first = null;
        List<Integer> second = null;
        Set<Integer> s1 = null;
        Set<Integer> s2 = null;
        Connection con2 = null;
        ResultSet resultSetForTag = null;
        Statement statement2 = null;
        List<Cocktail> list = new ArrayList<>();
        ResultSet resSetForCockt = null;
        try {
            Map<Integer, List<Integer>> map = new HashMap<>();
            for (int i = 0; i < tags.length; i++) {
                System.out.println("+");
                con = DBConnector.createConnection();
                con2 = DBConnector.createConnection();
                statement = con.createStatement();
                statement2 = con2.createStatement();
                System.out.println(tags[i]);
                String sql1 = "select id from tags where tag='" + tags[i] + "'";
                resultSetForTag = statement.executeQuery(sql1);
                Integer tag_id = 0;
                while (resultSetForTag.next()) {
                    tag_id = resultSetForTag.getInt("id");
                }
                System.out.println("-");
                resSetForCockt = statement2.executeQuery("select cocktail_id from cocktails_tags where tag_id=" + tag_id.toString());
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
                } else {
                    second = map.get(curr);
                    System.out.println("second " + second);
                    s1 = new HashSet<>(first);
                    s2 = new HashSet<>(second);
                    s1.retainAll(s2);
                    System.out.println(s1);
                }
                resultSetForTag.close();
                resSetForCockt.close();
                statement2.close();
                statement.close();
                con2.close();
                con.close();
            }
            System.out.println("s1" + s1);
            System.out.println("asdadsas");
            cocktails = s1.toArray(new Integer[s1.size()]);
            System.out.println(cocktails[0]);
            for (Integer id : cocktails) {
                System.out.println("в лист id" + id);
                list.add(this.getCocktailById(id));
            }
            return list;
        } catch (SQLException e) {
            if (resultSetForTag != null)
                resultSetForTag.close();
            if (resSetForCockt != null)
                resSetForCockt.close();
            if (statement2 != null)
                statement2.close();
            if (statement != null)
                statement.close();
            if (con2 != null)
                con2.close();
            if (con != null)
                con.close();
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
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
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
                cockt.setIngridients(this.getRecepie(cockt));
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

    public List<Ingridient> getRecepie(Cocktail cocktail) throws SQLException {
        Integer id = cocktail.getId();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Ingridient> list = new ArrayList<>();
        Connection con2 = null;
        PreparedStatement statement2 = null;
        ResultSet finalSet = null;
        try {
            con = DBConnector.createConnection();
            statement = con.prepareStatement("select cocktid,ingid from recipie where cocktid=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int ingId = resultSet.getInt("ingid");
                con2 = DBConnector.createConnection();
                statement2 = con2.prepareStatement("select * from ingridient where id=?");
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
            resultSet.close();
            finalSet.close();
            statement2.close();
            statement.close();
            con.close();
            con2.close();
            return list;
        } catch (SQLException e) {
            if (resultSet != null)
                resultSet.close();
            if (finalSet != null)
                finalSet.close();
            if (statement2 != null)
                statement2.close();
            if (statement != null)
                statement.close();
            if (con != null)
                con.close();
            if (con2 != null)
                con2.close();
            e.printStackTrace();
        }
        return list;
    }

    public Cocktail getCocktailById(Integer id) throws SQLException {
        Cocktail res = new Cocktail();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            con = DBConnector.createConnection();
            statement = con.prepareStatement("select * from cocktail where id=?");
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
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
            if (con != null)
                con.close();
            e.printStackTrace();
        }
        return res;
    }

    public void newLike(Integer cockt_id, Integer user_id) throws SQLException {
        boolean flag = true;
        Connection con1 = DBConnector.createConnection();
        PreparedStatement preparedStatement = con1.prepareStatement("select id_user , id_cocktail " +
                "from cocktails_likes where id_cocktail =?");
        preparedStatement.setInt(1, cockt_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getInt("id_user") == user_id) {
                flag = false;
                break;
            }
        }
        Connection con = null;
        PreparedStatement statement2 = null;
        PreparedStatement ps = null;
        Connection con2 = null;
        if (flag) {
            try {
                con = DBConnector.createConnection();
                statement2 = con.prepareStatement("insert into cocktails_likes(id_user,id_cocktail) values(?,?)");
                statement2.setInt(1, user_id);
                statement2.setInt(2, cockt_id);
                statement2.executeLargeUpdate();
                Cocktail cocktail = this.getCocktailById(cockt_id);
                int rate = cocktail.getRating() + 1;
                con2 = DBConnector.createConnection();
                ps = con2.prepareStatement("update cocktail set rating=? where id =?");
                ps.setInt(1, rate);
                ps.setInt(2, cockt_id);
                cocktail.setRating(rate);
                ps.executeUpdate();
                ps.close();
                statement2.close();
                statement2.close();
                con2.close();
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                if (ps != null)
                    ps.close();
                if (statement2 != null)
                    statement2.close();
                if (con2 != null)
                    con2.close();
                if (con != null)
                    con.close();
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (con1 != null)
                    con1.close();
            }
        } else {
            if (ps != null)
                ps.close();
            if (statement2 != null)
                statement2.close();
            if (con2 != null)
                con2.close();
            if (con != null)
                con.close();
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            if (con1 != null)
                con1.close();
            throw new SQLException();
        }
    }

    public void favCockt(Integer cockt_id, Integer user_id) throws SQLException {
        Connection con = DBConnector.createConnection();
        Connection con1 = DBConnector.createConnection();
        PreparedStatement preparedStatement1 = con1.prepareStatement("select user_id , cocktail_id " +
                "from user_favourite_cocktail where cocktail_id =?");
        preparedStatement1.setInt(1, cockt_id);
        ResultSet resultSet = preparedStatement1.executeQuery();
        boolean flag = true;
        while (resultSet.next()) {
            if (resultSet.getInt("user_id") == user_id) {
                flag = false;
                break;
            }
        }
        PreparedStatement ps = null;
        if (flag) {
            try {
                String query = "insert into user_favourite_cocktail set user_id = ? , cocktail_id = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, user_id);
                ps.setInt(2, cockt_id);
                ps.executeUpdate();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (ps != null)
                    ps.close();
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement1 != null)
                    preparedStatement1.close();
                if (con1 != null)
                    con1.close();
                if (con != null)
                    con.close();
            }
        } else {
            if (ps != null)
                ps.close();
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement1 != null)
                preparedStatement1.close();
            if (con1 != null)
                con1.close();
            if (con != null)
                con.close();
            throw new SQLException();
        }

    }

    public Integer newCocktail(String newName, String newInfo, String[] ingList, String[] recipie, String[] tags, Integer user_id, String pathFileBig, String pathFileSmall) throws SQLException {
        Connection conToCocktail = DBConnector.createConnection();
        PreparedStatement psCocktail = null;
        String rcp = "";
        for (int i = 0; i < recipie.length; i++) {
            rcp += recipie[i] + ";";
        }
        try {
            psCocktail = conToCocktail.prepareStatement("insert into cocktail set name = ? ," +
                    "information = ?, recipie_info = ?, author_id = ?, img = ? , smallImg = ?");
            psCocktail.setString(1, newName);
            psCocktail.setString(2, newInfo);
            psCocktail.setString(3, rcp);
            psCocktail.setInt(4, user_id);
            psCocktail.setString(5, pathFileBig);
            psCocktail.setString(6, pathFileSmall);
            psCocktail.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            assert psCocktail != null;
            psCocktail.close();
            conToCocktail.close();
        }

        Integer id = 0;
        Connection getIdCon = DBConnector.createConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = getIdCon.prepareStatement("select id from cocktail where name= ?");
            ps.setString(1, newName);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert rs != null;
            rs.close();
            assert ps != null;
            ps.close();
            getIdCon.close();
        }

        Connection setRecCon = DBConnector.createConnection();
        try {
            for (int i = 0; i < ingList.length; i++) {
                PreparedStatement setRecPs = setRecCon.prepareStatement("insert into recipie set cocktid = ?, ingid=?");
                setRecPs.setInt(1, id);
                setRecPs.setInt(2, Integer.parseInt(ingList[i]));
                setRecPs.executeUpdate();
                setRecPs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            setRecCon.close();
        }

        Connection forTagsCon = DBConnector.createConnection();
        List<Integer> tagsList = new ArrayList<>();
        try {
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
                rsForTags.close();
                preparedStatementForTags.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            forTagsCon.close();
        }

        Connection ferInsTagCon = DBConnector.createConnection();
        try {
            for (Integer tagid : tagsList) {
                PreparedStatement psInsTag = ferInsTagCon.prepareStatement("insert into cocktails_tags set cocktail_id=? , tag_id=?");
                psInsTag.setInt(1, id);
                psInsTag.setInt(2, tagid);
                psInsTag.executeUpdate();
                psInsTag.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ferInsTagCon.close();
        }
        return id;
    }
}