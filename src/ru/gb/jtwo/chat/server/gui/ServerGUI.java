package ru.gb.jtwo.chat.server.gui;

import ru.gb.jtwo.chat.server.core.ChatServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler{
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
        Thread.setDefaultUncaughtExceptionHandler(this);
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

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace(); // если есть консоль то распечатать в нее сообщение
        StackTraceElement[] stackTraceElements = e.getStackTrace(); //получаем сообщение у объекта исключения в виде массива stackTraceElements
        String message;
        if (stackTraceElements.length == 0) { // если массив из элементов stackTraceElements пустой, то вывести об этом сообщение
            message = "Empty Stacktrace";
        } else {
            message = e.getClass().getCanonicalName() + // полное имя класса исключения
                    ": " + e.getMessage() + "\n" +      // сообщение
                    "\t at " + stackTraceElements[0];   // первый элемент stackTraceElements
        }

        // Показываем полученое исключение в отдельном всплывающем окошке
        JOptionPane.showMessageDialog(this, message, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
