package  com.fomina;

import java.io.*;
import java.net.ServerSocket;

/**
 * Created by Vika on 13.03.2018.
 */
public class Sender {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("Listening");
        ServerListener serverListener = new ServerListener(serverSocket);
        serverListener.doListen();
    }
}
