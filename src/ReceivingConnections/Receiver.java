package ReceivingConnections;

import AnswersSender.CommandToObjectServer;
import AnswersSender.ServerAnswer;
import ClientAnswer.ComplicatedObject;
import CommandProcessing.Commands;
import CommandProcessing.Control;
import SpaceMarineDataClient.SpaceMarine;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Receiver {
    public static SocketChannel socketChannel = null;
    public static String[] sarray;
    public static SpaceMarine spaceMarine;
    public static int g;
    public static Long j;
    public static int p;
    public static String historyR;
    public static String s;

    public static void receiveObject() throws IOException, ClassNotFoundException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(55665));
        while (true) {
            try {
                socketChannel = serverSocketChannel.accept();
              //  System.out.println("connection established..."+ socketChannel.getLocalAddress());
                ObjectInputStream is = new ObjectInputStream(socketChannel.socket().getInputStream());
                try {
                    while (socketChannel.isConnected()) {
                        s = "";
                        Object object = is.readObject();
                      //  System.out.println("Object received: " + object.toString());
                        g = ((ComplicatedObject) object).getId();
                        j = ((ComplicatedObject) object).getParam();
                        spaceMarine = ((ComplicatedObject) object).getSpaceMarine();
                        p = ((ComplicatedObject) object).getP();
                        historyR = ((ComplicatedObject) object).getHistory();
                        s = ((ComplicatedObject) object).getCommand();
                        Control control = new Control();
                        control.processing();
                    }
                } catch (EOFException e) {
                    //System.out.println("--------------------------------------\nКлиент завершил работу программы. Коллекция успешно сохранена.");
                    Commands.save();
                    socketChannel.close();
                    receiveObject();
                }
            } catch (IOException e) {
                // хз как исправить
            }
        }
    }

    private static SocketChannel CreateSocketChannel() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(55665));
        socketChannel = serverSocketChannel.accept();
        //System.out.println("connection established..."+ socketChannel.getRemoteAddress());
        return socketChannel;
    }

}
