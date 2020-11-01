package DAO;

import Entities.User;
import org.apache.commons.dbutils.DbUtils;
import utilites.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDao {
    public User authenticateUser(User user) throws SQLException {
        String userName = user.getLogin();
        String password = user.getPassword();

        String userNameDB = "";
        String passwordDB = "";

        try (Connection con = DBConnector.createConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from user");) {
            while (resultSet.next()) {
                userNameDB = resultSet.getString("login");
                passwordDB = resultSet.getString("password");
                User res = new User();
                if (userName.equals(userNameDB) && password.equals(passwordDB)) {
                    res.setInformation(resultSet.getString("information"));
                    res.setId(resultSet.getInt("id"));
                    res.setName(resultSet.getString("name"));
                    res.setPassword(resultSet.getString("password"));
                    res.setEmail(resultSet.getString("email"));
                    res.setLogin(resultSet.getString("login"));
                    res.setImg(resultSet.getString("img"));
                    DbUtils.closeQuietly(resultSet);
                    DbUtils.closeQuietly(statement);
                    DbUtils.closeQuietly(con);
                    return res;
                }
            }
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
