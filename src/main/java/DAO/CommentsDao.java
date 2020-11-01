package DAO;

import Entities.Cocktail;
import Entities.Comment;
import Entities.User;
import org.apache.commons.dbutils.DbUtils;
import utilites.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentsDao {
    public String putComment(Comment comment) throws SQLException {
        Connection connector = DBConnector.createConnection();
        PreparedStatement preparedStatement = null;
        try {
            String query = "insert into commentofcocktail(Author_id,Comm,cockt_id,date) values(?,?,?,?)";
            preparedStatement = connector.prepareStatement(query);
            System.out.println(comment.getUser().getId());
            System.out.println(comment.getCocktail().getId());
            System.out.println(comment.getComm());
            preparedStatement.setInt(1, comment.getUser().getId());
            preparedStatement.setString(2, comment.getComm());
            preparedStatement.setInt(3, comment.getCocktail().getId());
            preparedStatement.setDate(4,comment.getDate());
            int i = preparedStatement.executeUpdate();
            DbUtils.closeQuietly(preparedStatement);
            DbUtils.closeQuietly(connector);
            if (i != 0) {
                return "SUCCESS";
            }
        } catch (SQLException e) {
            if (preparedStatement != null)
                DbUtils.closeQuietly(preparedStatement);
            if (connector != null)
                DbUtils.closeQuietly(connector);
            e.printStackTrace();
        }
        return "Oops.. Something went wrong there..!";
    }

    public List<Comment> getComments(Cocktail cocktail) throws SQLException {
        List<Comment> list = new ArrayList<>();
        Connection connector = DBConnector.createConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "select * from commentofcocktail where cockt_id = ? order by date DESC";
            statement = connector.prepareStatement(query);
            statement.setInt(1,cocktail.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment();
                User user;
                UserDao userDao = new UserDao();
                user = userDao.getUserById(resultSet.getInt("Author_id"));
                comment.setCocktail(cocktail);
                comment.setUser(user);
                comment.setComm(resultSet.getString("Comm"));
                comment.setId(resultSet.getInt("id"));
                comment.setDate(resultSet.getDate("date"));
                list.add(comment);
            }
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(connector);
            return list;
        } catch (SQLException e) {
            if (resultSet != null)
                DbUtils.closeQuietly(resultSet);
            if (statement != null)
                DbUtils.closeQuietly(statement);
            if (connector != null)
                DbUtils.closeQuietly(connector);
            e.printStackTrace();
        }
        return list;
    }
}
