package ru.gb.jtwo.chat.server.core;

import ru.gb.jtwo.chat.library.Messages;
import ru.gb.jtwo.chat.network.SocketThread;
import ru.gb.jtwo.chat.network.SocketThreadListener;

import java.net.Socket;

public class ClientThread extends SocketThread {

    public ClientThread(SocketThreadListener listener, String name, Socket socket) {
        super(listener, name, socket);
    }

    private String nickname;
    private boolean isAuthorized;
    private boolean isReconnected;

    String getNickname() {
        return nickname;
    }

    boolean isAuthorized() {
        return isAuthorized;
    }

    void authorizeAccept(String nickname) {
        isAuthorized = true;
        this.nickname = nickname;
        sendMessage(Messages.getAuthAccept(nickname));
    }

    void authorizeError() {
        sendMessage(Messages.getAuthDenied());
        close();
    }

    void msgFormatError(String value) {
        sendMessage(Messages.getMsgFormatError(value));
        close();
    }

    public boolean isReconnected() {
        return isReconnected;
    }

    void reconnect() {
        isReconnected = true;
        close();
    }
}
