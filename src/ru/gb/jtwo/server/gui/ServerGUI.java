package ru.gb.jtwo.server.gui;

import javax.swing.*;

public class ServerGUI extends JFrame{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerGUI();
            }
        });
    }

    ServerGUI(){

    }
}
