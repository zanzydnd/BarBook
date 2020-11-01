package DAO;

import Entities.User;
import org.apache.commons.dbutils.DbUtils;
import utilites.DBConnector;
import utilites.Hashing;

import java.sql.*;

public class RegisterDao {
    public String registerUser(User user) throws SQLException {
        String name = user.getName();
        String email = user.getEmail();
        String login = user.getLogin();
        String password = Hashing.md5Custom(user.getPassword());
        String img = user.getImg();
        String information = user.getInformation();
        String query = "insert into user(login,name,password,email," +
                "information) values (?,?,?,?,?)";
        try (
                Connection con = DBConnector.createConnection();
                Connection con2 = DBConnector.createConnection();
                PreparedStatement ps = con2.prepareStatement("select login from user where login=?");
                PreparedStatement preparedStatement = con.prepareStatement(query);
        ) {

            ps.setString(1, login);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String s = resultSet.getString("login");
                if (s.equals(login))
                    throw new SQLException();
            }

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, information);

            int i = preparedStatement.executeUpdate();
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(con2);
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(preparedStatement);
            DbUtils.closeQuietly(con);
            if (i != 0)
                return "SUCCESS";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Oops.. Something went wrong there..!";
    }
}
