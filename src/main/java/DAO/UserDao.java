package DAO;

import Entities.Cocktail;
import Entities.User;
import org.apache.commons.dbutils.DbUtils;
import utilites.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public User getUserById(Integer id) throws SQLException {
        Connection con = DBConnector.createConnection();
        String query = "select * from user where id=?";
        User res = new User();
        CocktailDao dao = new CocktailDao();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet1 = null;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                res.setId(id);
                res.setInformation(resultSet.getString("information"));
                res.setName(resultSet.getString("name"));
                res.setEmail(resultSet.getString("email"));
                res.setLogin(resultSet.getString("login"));
                res.setImg(resultSet.getString("img"));
                res.setCreatedCocktails(this.getCocktailsCreatedByUser(id));
            }
            connection = DBConnector.createConnection();
            statement = connection.prepareStatement("select * from user_favourite_cocktail where user_id = ?");
            statement.setInt(1, id);
            resultSet1 = statement.executeQuery();
            List<Cocktail> list = new ArrayList<>();
            while (resultSet1.next()) {
                Cocktail cocktail = dao.getCocktailById(resultSet1.getInt("cocktail_id"));
                list.add(cocktail);
            }
            res.setFavCocktails(list);
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(resultSet1);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(con);
            DbUtils.closeQuietly(connection);
        } catch (SQLException e) {
            if (resultSet != null)
                DbUtils.closeQuietly(resultSet);
            if (resultSet1 != null)
                DbUtils.closeQuietly(resultSet1);
            if (statement != null)
                DbUtils.closeQuietly(statement);
            if (ps != null)
                DbUtils.closeQuietly(ps);
            if (con != null)
                DbUtils.closeQuietly(con);
            if (connection != null)
                DbUtils.closeQuietly(connection);
            e.printStackTrace();
        }
        return res;
    }

    public void updateDB(String info, String name, String pathFile, Integer id) throws SQLException {
        Connection con = DBConnector.createConnection();
        PreparedStatement ps = null;
        try {
            if (!pathFile.equals("")) {
                String query = "update user set information = ? , name = ? ,img = ? where id=" + id;
                ps = con.prepareStatement(query);
                ps.setString(1, info);
                ps.setString(2, name);
                ps.setString(3, pathFile);
                ps.executeUpdate();
                DbUtils.closeQuietly(ps);
                DbUtils.closeQuietly(con);
            } else {
                String query = "update user set  information = ? , name = ? where id=" + id;
                ps = con.prepareStatement(query);
                ps.setString(1, info);
                ps.setString(2, name);
                ps.executeUpdate();
                DbUtils.closeQuietly(ps);
                DbUtils.closeQuietly(con);
            }
        } catch (SQLException e) {
            if (ps != null)
                DbUtils.closeQuietly(ps);
            if (con != null) {
                DbUtils.closeQuietly(con);
            }
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
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
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(con);
            return list;
        } catch (SQLException e) {
            if (resultSet != null)
                DbUtils.closeQuietly(resultSet);
            if (statement != null)
                DbUtils.closeQuietly(statement);
            if (con != null)
                DbUtils.closeQuietly(con);
            e.printStackTrace();
            return list;
        }
    }

    public List<Cocktail> getCocktailsCreatedByUser(Integer id) throws SQLException {
        Connection con = DBConnector.createConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<Cocktail> list = new ArrayList<>();
        try{
            ps = con.prepareStatement("select id from cocktail where author_id=?");
            ps.setInt(1,id);
            resultSet = ps.executeQuery();
            while(resultSet.next()){
                list.add(new CocktailDao().getCocktailById(resultSet.getInt("id")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            assert resultSet != null;
            DbUtils.closeQuietly(resultSet);
            assert ps != null;
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(con);
        }
        return list;
    }
}
