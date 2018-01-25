package ru.gb.jtwo.chat.network;
/**
 * Слушает входящие соединения и выдает готовые сокеты для каждого клиента
 */
public class ServerSocketThread extends Thread{

    private final int port;

    @Override
    public void run() {
        System.out.println("Server thread started");
        while (!isInterrupted()){
            System.out.println("Server socket thread works");
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("thrown IterruptedException");
                break;
            }
        }
        System.out.println("Server thread terminated");
    }

    public ServerSocketThread (String name, int port){
        super(name);
        this.port = port;
        start();
    }
}
