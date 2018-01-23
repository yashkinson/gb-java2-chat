package ru.gb.jtwo.server.gui;

import ru.gb.jtwo.server.core.ChatServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ActionListener{
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

        btnStart.addActionListener(this);
        btnStop.addActionListener(this);

        add(btnStart);
        add(btnStop);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(src == btnStart){
            System.out.println("Кнопка START нажата");
            chatServer.start(8189);
        } else if(src == btnStop){
            System.out.println("Кнопка STOP нажата");
            chatServer.stop();
        } else{
            throw new RuntimeException("Unknown button presed");
        }
    }
}
