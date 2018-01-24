package ru.gb.jtwo.chat.network;
/**
 * Слушает входящие соединения и выдает готовые сокеты для каждого клиента
 */
public class ServerSocketThread extends Thread{

    private final int port;

    @Override
    public void run() {
        while (!isInterrupted()){
            System.out.println("Server socket thread works");
        }
    }

    public ServerSocketThread (String name, int port){
        super(name);
        this.port = port;
        start();
    }
}
