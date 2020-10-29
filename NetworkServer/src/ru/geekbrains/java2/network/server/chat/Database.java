package ru.geekbrains.java2.network.server.chat;

import java.sql.*;

public class Database {

private Statement statement;
private PreparedStatement preparedStatement;
private ResultSet resultSet;

 public Database()  {
     init();
     try{
         Connection connection = getConnection();
        createDB(connection);
        writeDB(connection);
        changeUserName(connection);
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

    private void createDB(Connection connection) throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE if not exists 'users'('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'username' text, 'password' text)");
        }
    }
    private void writeDB(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name) value (?) ");
            preparedStatement.setString(1,"Ivan");
            preparedStatement.addBatch();
            preparedStatement.setString(1,"Max");
            preparedStatement.addBatch();
            preparedStatement.setString(1,"Alex");
            preparedStatement.addBatch();
            statement.executeBatch();

    }
    private void changeUserName(Connection connection) throws SQLException {
      try(Statement statement = connection.createStatement()){
//          statement.executeUpdate("UPDATE users SET username = " + newUsername +
//                                      ", where username = " + username);
          statement.executeUpdate("UPDATE users SET username = Dmitry" +
                                      ", where username = Alex");
      }
    }
}