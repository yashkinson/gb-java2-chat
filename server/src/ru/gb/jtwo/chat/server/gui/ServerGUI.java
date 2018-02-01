package ru.gb.jtwo.chat.server.gui;

import ru.gb.jtwo.chat.server.core.ChatServer;
import ru.gb.jtwo.chat.server.core.ChatServerListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler, ChatServerListener{
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
    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;

    private final ChatServer chatServer = new ChatServer(this);

    private final JPanel panelTop = new JPanel(new GridLayout(1, 2));
    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private final JTextArea log = new JTextArea();


    ServerGUI(){
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);

        setTitle("Chat server");
        setAlwaysOnTop(true);

        log.setEditable(false);
        log.setLineWrap(true);
        JScrollPane scrollLog = new JScrollPane(log);
        panelTop.add(btnStart);
        panelTop.add(btnStop);

        btnStart.addActionListener(this);
        btnStop.addActionListener(this);

        add(panelTop, BorderLayout.NORTH);
        add(scrollLog, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(src == btnStart){
            chatServer.start(8189);
        } else if(src == btnStop){
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

    @Override
    public void onChatServerLog(ChatServer server, String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(message + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }
}
