package ru.gb.jtwo.chat.network;

import java.net.Socket;

public class SocketThread extends Thread {

    private SocketThreadListener listener;
    private  Socket socket;

    SocketThread(SocketThreadListener listener, String name, Socket socket){
        super(name);
        this.socket = socket;
        this.listener = listener;
        start();
    }

    @Override
    public void run() {

    }

    public void sandMessage(String message){

    }

    public void close(){

    }
}
