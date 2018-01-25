package ru.gb.jtwo.chat.server.core;

import ru.gb.jtwo.chat.network.ServerSocketThread;

public class ChatServer {

    ServerSocketThread serverSocketThread;

    public void start(int port){
        serverSocketThread = new ServerSocketThread("Server thread", port);
    }

    public void stop(){
        serverSocketThread.interrupt();
    }
}
