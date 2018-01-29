package ru.gb.jtwo.chat.server.core;

import ru.gb.jtwo.chat.network.ServerSocketThread;
import ru.gb.jtwo.chat.network.ServerSocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer implements ServerSocketThreadListener{

    ServerSocketThread serverSocketThread;

    public void start(int port){
        if (serverSocketThread != null && serverSocketThread.isAlive()){
            System.out.println("Server is already running");
        } else{
            serverSocketThread = new ServerSocketThread(this, "Server thread", port, 2000);
        }
    }

    public void stop(){
        if (serverSocketThread == null || !serverSocketThread.isAlive())
            System.out.println("Server is not running");
        else
            serverSocketThread.interrupt();
    }

    /**
     * Методы Server Socket Thread
     *
     */

    @Override
    public void onStartServerSocketThread(ServerSocketThread thread) {

    }

    @Override
    public void onStopServerSocketThread(ServerSocketThread thread) {

    }

    @Override
    public void onCreateServerSocket(ServerSocketThread thread, ServerSocket serverSocket) {

    }

    @Override
    public void onAcceptTimeout(ServerSocketThread thread, ServerSocket serverSocket) {

    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, Socket socket) {

    }

    @Override
    public void onServerSocketException(ServerSocketThread thread, Exception e) {

    }
}
