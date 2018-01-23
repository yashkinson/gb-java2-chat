package ru.gb.jtwo.server.core;

public class ChatServer {
    public void start(int port){
        System.out.println("Сервер стартовал на порте" + port);
    }

    public void stop(){
        System.out.println("Сервер остановился");
    }
}
