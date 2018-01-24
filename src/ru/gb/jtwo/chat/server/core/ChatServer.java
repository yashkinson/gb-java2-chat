package ru.gb.jtwo.chat.server.core;

import ru.gb.jtwo.chat.network.ServerSocketThread;

public class ChatServer {

    ServerSocketThread serverSocketThread;

    public void start(int port){
        serverSocketThread = new ServerSocketThread("Server thread", port);
        //System.out.println("Сервер стартовал на порте " + port);
    }

    public void stop(){
        serverSocketThread.interrupt();
    }
}
