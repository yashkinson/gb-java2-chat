package ru.gb.jtwo.chat.server.core;

import ru.gb.jtwo.chat.network.ServerSocketThread;
import ru.gb.jtwo.chat.network.ServerSocketThreadListener;
import ru.gb.jtwo.chat.network.SocketThread;
import ru.gb.jtwo.chat.network.SocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {

    ServerSocketThread serverSocketThread;
    private final ChatServerListener listener;
    private final DateFormat dataFormat = new SimpleDateFormat("hh:mm:ss: ");

    private Vector<SocketThread> clients = new Vector<>();

    public ChatServer(ChatServerListener chatServerListener){
        this.listener = chatServerListener;
    }
    public void start(int port){
        if (serverSocketThread != null && serverSocketThread.isAlive()){
            putLog("Server is already running");
        } else{
            serverSocketThread = new ServerSocketThread(this, "Server thread", port, 2000);
        }
    }

    public void stop(){
        if (serverSocketThread == null || !serverSocketThread.isAlive())
            putLog("Server is not running");
        else
            serverSocketThread.interrupt();
    }

    void putLog(String msg){
        msg = dataFormat.format(System.currentTimeMillis()) +
                Thread.currentThread().getName() + ": " + msg;
        listener.onChatServerLog(this, msg);
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
        String threadName = "SocketThread " + socket.getInetAddress() + ":" + socket.getPort();
        new SocketThread(this, threadName, socket);
    }

    @Override
    public void onServerSocketException(ServerSocketThread thread, Exception e) {
        putLog("Exception: " + e.getClass().getName() + ": " + e.getMessage());
    }




    @Override
    public synchronized void onStartSocketThread(SocketThread thread, Socket socket) {
        putLog("started");
    }

    @Override
    public synchronized void onStopSocketThread(SocketThread thread) {
        putLog("stopped");
    }

    @Override
    public synchronized void onSocketIsReady(SocketThread thread, Socket socket) {
        putLog("is ready");
        clients.add(thread);
    }

    @Override
    public synchronized void onReceiveString(SocketThread thread, Socket socket, String value) {
        thread.sendMessage("echo " + value);
    }

    @Override
    public synchronized void onSocketThreadException(SocketThread thread, Exception e) {
        e.printStackTrace();
    }
}
