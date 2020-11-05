package ru.geekbrains.java2.network.server.chat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

private Connection connection;
private List<User> users;

 public Database()  {
     init();
     try{
         Connection connection = getConnection();
        this.connection = connection;
        createDB();
        writeDB();
        users = getUsersFromDB();
     } catch (SQLException e ){
         e.printStackTrace();
     }
 }
    private void init() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws  SQLException{
     return DriverManager.getConnection("jdbc:sqlite:chatDB.s3db");
    }
    private void clearDB() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE if exists 'users'");
        }
    }
    private void createDB() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE if not exists 'users'('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "'login' text, 'password' text, 'username' text)");
        }
    }
    private void writeDB() throws SQLException {
     String sql2 = "INSERT INTO users (login, password, username)" +
             "SELECT * FROM (SELECT ?,?,?) AS tmp " +
             "WHERE NOT EXISTS (" +
             "    SELECT login FROM users WHERE login = ?" +
             ") LIMIT 1;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql2)) {
            addUserRow(preparedStatement,"login1","pass1","Ivan");
            addUserRow(preparedStatement,"login2","pass2","Max");
            addUserRow(preparedStatement,"login3","pass3","Alex");
            preparedStatement.executeBatch();
        }

    }
    private List<User> getUsersFromDB() throws SQLException {
     List<User> users = new ArrayList<>();
     try(ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users")) {
         while (resultSet.next()) {
             String login = resultSet.getString("login");
             String password = resultSet.getString("password");
             String username = resultSet.getString("username");
             users.add(new User(login,password,username));
         }
     }
     return users;
    }
    public List<User> getUsers()  {
     return users;
    }
    private void addUserRow(PreparedStatement preparedStatement, String login, String password, String username) throws SQLException {
     preparedStatement.setString(1,login);
     preparedStatement.setString(2,password);
     preparedStatement.setString(3,username);
     preparedStatement.setString(4,login);
     preparedStatement.addBatch();
    }

    public void changeUserName(String usernameValue, String newUsernameValue) throws SQLException {
      try(Statement statement = connection.createStatement()){
          statement.executeUpdate(String.format("UPDATE users SET username = '%s' WHERE username = '%s'",newUsernameValue,usernameValue));
      }
    }

    public void stop() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            System.err.println("Database exception when trying to close the connection");
            throwables.printStackTrace();
        }
    }
}