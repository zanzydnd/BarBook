package DAO;

import Entities.Contest;
import Entities.Ingridient;
import utilites.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContestDao {
    public static List<Contest> getAllContest() {
        Connection con;
        Statement statement;
        ResultSet resultSet;
        List<Contest> list = new ArrayList<>();
        try {
            con = DBConnector.createConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery("select * from contests");
            while (resultSet.next()) {
                Contest contest = new Contest();
                if(resultSet.getString("statment").equals("active")) {
                    contest.setStatement(resultSet.getString("statment"));
                    contest.setHref(resultSet.getString("href"));
                    contest.setName(resultSet.getString("name"));
                    contest.setInfo(resultSet.getString("info"));
                    contest.setImg(resultSet.getString("img"));
                    contest.setId(resultSet.getInt("id"));
                    contest.setHtml_id(resultSet.getString("html_id"));
                    Date date = resultSet.getDate("date");
                    contest.setDate(date);
                    list.add(contest);
                }
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}