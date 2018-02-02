package ru.gb.jtwo.chat.gui;

import ru.gb.jtwo.chat.library.Messages;
import ru.gb.jtwo.chat.network.SocketThread;
import ru.gb.jtwo.chat.network.SocketThreadListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler, SocketThreadListener{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private final JTextArea log = new JTextArea();

    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("ivan_igorevich");
    private final JPasswordField tfPassword = new JPasswordField("123456");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("Disconnect");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();

    private SocketThread socketThread;

    ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Chat Client");

        cbAlwaysOnTop.addActionListener(this);
        btnSend.addActionListener(this);
        tfMessage.addActionListener(this);
        tfIPAddress.addActionListener(this);
        tfLogin.addActionListener(this);
        tfPassword.addActionListener(this);
        tfPort.addActionListener(this);
        btnLogin.addActionListener(this);

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);

        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);
        panelBottom.setVisible(false);

        JScrollPane scrollPane = new JScrollPane(log);
        add(scrollPane, BorderLayout.CENTER);
        log.setEditable(false);
        log.setLineWrap(true);

        JScrollPane scrollUsers = new JScrollPane(userList);
        scrollUsers.setPreferredSize(new Dimension(100, 0));
        add(scrollUsers, BorderLayout.EAST);

        setVisible(true);
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
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSend || src == tfMessage) {
            sendMessage();
        } else if (src == tfIPAddress || src == tfLogin ||
                src == tfPassword || src == tfPort || src == btnLogin) {
            connect();
        } else if (src == btnDisconnect) {
            socketThread.close();
        } else {
            throw new RuntimeException("Unknown source: " + src);
        }

    }

    public void sendMessage(){
        String msg = tfMessage.getText();
        if("".equals(msg)) return;
        tfMessage.setText(null);
        tfMessage.requestFocusInWindow();
        socketThread.sendMessage(msg);
//        try (FileWriter out = new FileWriter("log.txt", true)){
//            out.write(username + ": " + msg + "\n");
//            out.flush();
//        } catch(IOException e){
//            throw new RuntimeException(e);
//        }

    }

    private void connect() {
        Socket socket = null;
        try {
            socket = new Socket(tfIPAddress.getText(),
                    Integer.parseInt(tfPort.getText()));
        } catch (IOException e) {
            log.append("Exception: " + e.getMessage());
        }
        socketThread = new SocketThread(this, "SocketTHread", socket);
    }

    private void putLog(String message) {
        log.append(message + "\n");
        log.setCaretPosition(log.getDocument().getLength());
    }

    @Override
    public void onStartSocketThread(SocketThread thread, Socket socket) {
        putLog("Поток сокета стартовал");
    }

    @Override
    public void onStopSocketThread(SocketThread thread) {
        putLog("Соединение разорвано");
        panelBottom.setVisible(false);
        panelTop.setVisible(true);
    }

    @Override
    public void onSocketIsReady(SocketThread thread, Socket socket) {
        putLog("Соединение установлено");
        String login = tfLogin.getText();
        String password = new String(tfPassword.getPassword());
        thread.sendMessage(Messages.getAuthRequest(login, password));
        panelBottom.setVisible(true);
        panelTop.setVisible(false);
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String value) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(value + "\n");
            }
        });
    }

    @Override
    public void onSocketThreadException(SocketThread thread, Exception e) {

    }
}
