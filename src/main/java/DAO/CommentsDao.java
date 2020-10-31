package DAO;

import Entities.Cocktail;
import Entities.Comment;
import Entities.User;
import utilites.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentsDao {
    public String putComment(Comment comment) throws SQLException {
        Connection connector = DBConnector.createConnection();
        PreparedStatement preparedStatement = null;
        try {
            String query = "insert into commentofcocktail(Author_id,Comm,cockt_id) values(?,?,?)";
            preparedStatement = connector.prepareStatement(query);
            System.out.println(comment.getUser().getId());
            System.out.println(comment.getCocktail().getId());
            System.out.println(comment.getComm());
            preparedStatement.setInt(1, comment.getUser().getId());
            preparedStatement.setString(2, comment.getComm());
            preparedStatement.setInt(3, comment.getCocktail().getId());
            int i = preparedStatement.executeUpdate();
            preparedStatement.close();
            connector.close();
            if (i != 0) {
                return "SUCCESS";
            }
        } catch (SQLException e) {
            if (preparedStatement != null)
                preparedStatement.close();
            if (connector != null)
                connector.close();
            e.printStackTrace();
        }
        return "Oops.. Something went wrong there..!";
    }

    public List<Comment> getComments(Cocktail cocktail) throws SQLException {
        List<Comment> list = new ArrayList<>();
        Connection connector = DBConnector.createConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "select * from commentofcocktail where cockt_id=" + cocktail.getId();
            statement = connector.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Comment comment = new Comment();
                User user;
                UserDao userDao = new UserDao();
                user = userDao.getUserById(resultSet.getInt("Author_id"));
                comment.setCocktail(cocktail);
                comment.setUser(user);
                comment.setComm(resultSet.getString("Comm"));
                comment.setId(resultSet.getInt("id"));
                list.add(comment);
            }
            resultSet.close();
            statement.close();
            connector.close();
            return list;
        } catch (SQLException e) {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
            if (connector != null)
                connector.close();
            e.printStackTrace();
        }
        return list;
    }
}
