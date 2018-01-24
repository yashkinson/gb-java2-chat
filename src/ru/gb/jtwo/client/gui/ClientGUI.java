package ru.gb.jtwo.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler{
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

    ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Chat Client");

        cbAlwaysOnTop.addActionListener(this);
        btnSend.addActionListener(this);

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

        JScrollPane scrollPane = new JScrollPane(log);
        add(scrollPane, BorderLayout.CENTER);
        log.setEditable(false);

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
        } else if (src == btnSend) {
            // sending
        } else {
            throw new RuntimeException("Unknown source: " + src);
        }

    }
}
