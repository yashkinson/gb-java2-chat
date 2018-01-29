package ru.gb.jtwo.chat.server.core;

import ru.gb.jtwo.chat.network.ServerSocketThread;
import ru.gb.jtwo.chat.network.ServerSocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ChatServer implements ServerSocketThreadListener{

    ServerSocketThread serverSocketThread;
    private final DateFormat dataFormat = new SimpleDateFormat("hh:mm:ss: ");

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

    void putLog(String msg){
        msg = dataFormat.format(System.currentTimeMillis()) +
                Thread.currentThread().getName() + ": " + msg;
        System.out.println(msg);
    }

    /**
     * Методы Server Socket Thread
     *
     */

    @Override
    public void onStartServerSocketThread(ServerSocketThread thread) {
        putLog("Сервер запущен");
    }

    @Override
    public void onStopServerSocketThread(ServerSocketThread thread) {
        putLog("сервер остановлен");
    }

    @Override
    public void onCreateServerSocket(ServerSocketThread thread, ServerSocket serverSocket) {
        putLog("создан Server socket");
    }

    @Override
    public void onAcceptTimeout(ServerSocketThread thread, ServerSocket serverSocket) {

    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, Socket socket) {
        putLog("Клиент подключился: " + socket);
    }

    @Override
    public void onServerSocketException(ServerSocketThread thread, Exception e) {
        putLog("Exception: " + e.getClass().getName() + ": " + e.getMessage());
    }
}
