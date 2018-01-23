package ru.gb.jtwo.client.gui;



import javax.swing.*;

public class ClientGUI extends JFrame{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    ClientGUI() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Chat Client");

        setVisible(true);
    }
}
