package com.fomina;

import com.fomina.dao.MessageDao;
import com.fomina.dao.UserDao;
import com.fomina.dao.exceptions.DaoException;
import com.fomina.dao.exceptions.UserNotFoundException;
import com.fomina.model.Message;
import com.fomina.model.User;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.net.URLDecoder.decode;

public class ConnectionWorker implements Runnable{

    // Properties ---------------------------------------------------------------------------------

    private Socket socket;
    private Thread runningThread = null;
    private MessageDao messageDao;
    private UserDao userDao;

    // Constructors -------------------------------------------------------------------------------

    ConnectionWorker(Socket socket, MessageDao messageDao, UserDao userDao) {
        this.socket = socket;
        this.messageDao = messageDao;
        this.userDao = userDao;
    }

    // Object overrides ---------------------------------------------------------------------------

    @Override
    public void run() {

        Integer userId;
        User user = null;

        try{
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                HashMap<String, String> requestHeaders = getRequestHeaders(reader);


                //TODO
               /* if (requestHeaders.get("method").equals("POST")) {
                    doPost();
                } else if (requestHeaders.get("method").equals("GET")) {
                    doGet();
                }*/
//doPost
                if (requestHeaders.get("method").equals("POST")) {
                    System.out.println("method POST");
                    String urlencoded = getBody(reader, Integer.parseInt(requestHeaders.get("Content-Length")));

                    Map<String, String> body = parseBody(urlencoded);
                    System.out.println("body" +body);
                    if(body.containsKey("user_id")) {
                        userId = Integer.parseInt(body.get("user_id"));
                        try {
                            user = userDao.getUserById(userId);
                        } catch (UserNotFoundException ex) {
                            user = userDao.addUser(new User("alisa"));
                        }
                    } else {
                        user = userDao.addUser(new User("alisa"));
                    }

                    String messageText = body.getOrDefault("message", "");
                    Message message = new Message(messageText, new Date(), user);

                    messageDao.createMessage(message);

                    System.out.println(message);

                }
//doGet
                if(user == null) {
                    user = userDao.addUser(new User("bob"));
                }

                doRender(user, writer);

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private void doRender(User user, BufferedWriter writer) throws IOException {
        StringBuilder messageList = new StringBuilder();
        List<Message> msgs = new ArrayList<>();
        try {
            msgs = messageDao.listAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }

        for ( Message msg : msgs) {
            messageList.append("<li>").append(msg.getSender().getName()).append("_").append(msg.getSender().getId()).append(": ").append(msg.getText()).append("</li>\n");
        }

        System.out.println(messageList);

        String response = getTemplate();
        response = response.replace("<!--msg_list-->", messageList.toString());
        response = response.replace("<!--user_id-->", user.getId().toString());

        writer.write("HTTP/1.1 200 OK\n");
        writer.write("Content-Type: text/html\n");
        writer.write("Content-Length: " + response.length() + "\n");
        writer.write("\n");
        writer.write(response);
        writer.flush();
    }

    private String getTemplate(){
        ClassLoader classLoader = getClass().getClassLoader();
        return new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream("index.html")))
                .lines().collect(Collectors.joining("\n"));
    }

    private Map<String, String> parseBody(String urlencoded) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        for (String keyValue : urlencoded.trim().split("&")) {
            String encoding = StandardCharsets.UTF_8.name();
            String[] tokens = keyValue.trim().split("=");
            String key = tokens[0];
            String value = tokens.length == 1 ? "" : decode(tokens[1], encoding);
            map.put(key,value);
        }
        return map;
    }

    private String getBody(BufferedReader reader, Integer contentLength) throws IOException {
        char[] body = new char[contentLength];
        reader.read(body, 0, contentLength);
        return new String(body);
    }

    private HashMap<String, String> getRequestHeaders(BufferedReader br) throws IOException {

        HashMap<String, String> requestHeders = new HashMap<>();
        String nextLine = br.readLine();

        while (!nextLine.equals("")) {
            if (nextLine.indexOf(":") > -1) {
                requestHeders.put(nextLine.split(":")[0].trim(), nextLine.split(":")[1].trim());
            } else {
                requestHeders.put("method", nextLine.split(" ")[0].trim());
                requestHeders.put("request-target", nextLine.split(" ")[1].trim());
                requestHeders.put("http-version", nextLine.split(" ")[2].trim());
            }
            System.out.println(nextLine);
            nextLine = br.readLine();
        }
        return requestHeders;
    }
}
