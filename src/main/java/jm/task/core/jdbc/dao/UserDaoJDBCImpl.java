package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS USER " +
                "(" +
                "id int NOT NULL AUTO_INCREMENT," +
                "name char(30) NOT NULL," +
                "lastname char(30)," +
                "age int NOT NULL," +
                "PRIMARY KEY (id))";
        statement.execute(sql);
        statement.close();
    }

    @Override
    public void dropUsersTable() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "DROP TABLE IF EXISTS user";
        statement.execute(sql);
        statement.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sql = "INSERT user (name, lastname, age) values (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, lastName);
        ps.setByte(3, age);
        ps.executeUpdate();
        ps.close();
        ps.close();
    }

    @Override
    public void removeUserById(long id) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM user";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setLastName(resultSet.getString("lastname"));
            user.setAge(resultSet.getByte("age"));
            userList.add(user);
        }
        statement.close();
        resultSet.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "TRUNCATE TABLE user";
        statement.execute(sql);
        statement.close();
    }
}
