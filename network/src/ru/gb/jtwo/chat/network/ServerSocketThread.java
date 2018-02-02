package ru.gb.jtwo.chat.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


/**
 * Слушает входящие соединения и выдает готовые сокеты для каждого клиента
 */
public class ServerSocketThread extends Thread{

    private final int port;
    private final int timeout;

    @Override
    public void run() {
        System.out.println("Поток стартоваол");
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Создали серверСокет");
            serverSocket.setSoTimeout(timeout);
            while (!isInterrupted()){
                try {
                    Socket socket = serverSocket.accept();
                } catch (SocketTimeoutException e){
                    System.out.println("Случился timeout accept");
                    continue;
                }
                System.out.println("создан новый сокет");
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            System.out.println("Поток остановился");
        }
    }

    public ServerSocketThread (String name, int port, int timeout){
        super(name);
        this.port = port;
        this.timeout = timeout;
        start();
    }
}
