package ru.gb.jtwo.server.gui;

import ru.gb.jtwo.server.core.ChatServer;

import javax.swing.*;
import java.awt.*;

public class ServerGUI extends JFrame{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerGUI();
            }
        });
    }

    private static final int POS_X = 1000;
    private static final int POS_Y = 600;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;

    private final ChatServer chatServer = new ChatServer();
    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");

    ServerGUI(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);

        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        setLayout(new GridLayout(1, 2));

        setVisible(true);
    }
}
