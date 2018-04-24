package com.fomina;

import com.fomina.dao.MessageDao;
import com.fomina.dao.UserDao;
import com.fomina.dao.impl.ListMessageDao;
import com.fomina.dao.impl.ListUserDao;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*
 * Created by Vika on 13.03.2018.
 * Inspired by https://www.tutorialspoint.com/javaexamples/net_multisoc.htm
 * and
 * http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html
 */
public class ServerListener{

    // Properties ---------------------------------------------------------------------------------

    private ServerSocket serverSocket;
    private MessageDao messageDao = new ListMessageDao(new ArrayList<>());
    private UserDao userDao = new ListUserDao(new ArrayList<>());

    // Constructor --------------------------------------------------------------------------------

    public ServerListener(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    // Methods ------------------------------------------------------------------------------------

    void doListen(){

        while (true) {
            try {
                Socket socket = serverSocket.accept(); // при команде accept()готов слушать все что происходит на этом порту
                System.out.println("Connected");


                new Thread(new ConnectionWorker(socket, messageDao, userDao)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
