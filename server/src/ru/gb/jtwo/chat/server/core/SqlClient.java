package ru.gb.jtwo.chat.server.core;

import java.sql.*;

public class SqlClient {

    private static Connection connection;//для хранинения соединения
    private static Statement statement;//выражение для обращение к БД

    synchronized static void connect(){
        try {
            Class.forName("org.sqlite.JDBC"); //загрузка JDBC драйвера из статической секции
            connection = DriverManager.getConnection("jdbc:sqlite:chatDB.db");//используем драйвер jdbc для бд  sqlite и всё это будет храниться в chatDB.db
            statement = connection.createStatement();//создаем statement для текущего соединения
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized static String getNick(String login, String password) {
        String request = "SELECT nickname FROM users WHERE login='" +
                login + "' AND password='" + password + "'";
        //запрос возвращает множество с результатами в виде таблицы ResultSet
        //при возвращении указатель на текущее положение в таблице = -1
        try (ResultSet set = statement.executeQuery(request)) {
            if (set.next()) {
                return set.getString(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
