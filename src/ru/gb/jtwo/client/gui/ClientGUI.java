package ru.gb.jtwo.client.gui;

import javax.swing.*;

public class ClientGUI extends JFrame implements Thread.UncaughtExceptionHandler{
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
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Chat Client");

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
}
