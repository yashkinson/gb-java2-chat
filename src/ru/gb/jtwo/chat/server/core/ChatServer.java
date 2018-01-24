package ru.gb.jtwo.chat.server.core;

import ru.gb.jtwo.chat.network.ServerSocketThread;

public class ChatServer {

    ServerSocketThread serverSocketThread;

    public void start(int port){
        if (serverSocketThread != null && serverSocketThread.isAlive()){
            System.out.println("Server is already running");
        } else{
            serverSocketThread = new ServerSocketThread("Server thread", port);
        }
    }

    public void stop(){
        if (serverSocketThread == null || !serverSocketThread.isAlive())
            System.out.println("Server is not running");
        else
            serverSocketThread.interrupt();
    }
}
