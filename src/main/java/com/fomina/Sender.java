package  com.fomina;

import com.fomina.dao.impl.H2MessageDao;
import com.fomina.dao.impl.H2UserDao;

import java.io.*;
import java.net.ServerSocket;
import java.sql.*;
import java.util.stream.Collectors;

/**
 * Created by Vika on 13.03.2018.
 */
public class Sender {
    public static void main(String[] args) throws IOException {
        initialize();
    }

    private static void initialize() throws IOException {

        ServerSocket serverSocket = new ServerSocket(8888);

        System.out.println("Listening");

        String ddl = new BufferedReader(new InputStreamReader(Sender.class
                .getClassLoader()
                .getResourceAsStream("database.ddl")))
                .lines()
                .collect(Collectors.joining("\n"));

        System.out.println(ddl);

        try(Statement st = DriverManager.getConnection("jdbc:h2:mem:chat;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1").createStatement()){
            st.execute(ddl);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String url = "jdbc:h2:mem:chat;DB_CLOSE_DELAY=-1";

        ServerListener serverListener = new ServerListener(
                serverSocket,
                new H2MessageDao(url),
                new H2UserDao(url)
        );
        serverListener.doListen();
    }
}
