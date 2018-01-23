package ru.gb.jtwo.client.gui;

import ru.gb.jtwo.server.gui.ServerGUI;

import javax.swing.*;

public class ClientGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }
}
