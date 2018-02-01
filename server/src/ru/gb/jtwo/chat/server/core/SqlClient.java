package ru.gb.jtwo.chat.server.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlClient {

    private static Connection connection;

    synchronized static void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:chatDB.db");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized static void disconnect(){

    }

    synchronized static String getNick(String login, String password) {
        return null;
    }

}
