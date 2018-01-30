package ru.gb.jtwo.chat.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketThread extends Thread {

    private SocketThreadListener listener;
    private  Socket socket;
    private DataOutputStream out;

    SocketThread(SocketThreadListener listener, String name, Socket socket){
        super(name);
        this.socket = socket;
        this.listener = listener;
        start();
    }

    /**
     *  this method creates streams and receives messages
     */
    @Override
    public void run() {
        try {
            listener.onStartSocketThread(this, socket);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            listener.onSocketIsReady(this, socket);
            while (!isInterrupted()) {
                String msg = in.readUTF();
                listener.onReceiveString(this, socket, msg);
            }
        } catch (IOException e) {
            listener.onSocketThreadException(this, e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                listener.onSocketThreadException(this, e);
            }
            listener.onStopSocketThread(this);
        }
    }

    public boolean sandMessage(String message){
        try {
            out.writeUTF(message);
            out.flush();
            return true;
        } catch (IOException e) {
            listener.onSocketThreadException(this, e);
            close();
            return false;
        }
    }

    public void close(){
        interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            listener.onSocketThreadException(this, e);
        }
    }
}
