package com.fomina;

import com.fomina.dao.MessageDao;
import com.fomina.dao.UserDao;
import com.fomina.dao.exceptions.DaoException;
import com.fomina.model.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * Created by wiki_fi on 05/19/2018.
 * @author Vika Belka aka wiki_fi
 * @link https://vk.com/wiki_fi
 */
class Controller {

    // Properties ---------------------------------------------------------------------------------

    private MessageDao messageDao;
    private UserDao userDao;

    // Constructors -------------------------------------------------------------------------------



    Controller(MessageDao messageDao, UserDao userDao) {
        this.messageDao = messageDao;
        this.userDao = userDao;
    }

    // Methods ------------------------------------------------------------------------------------


    private void sendMessage(HttpServletRequest request,
                                    HttpServletResponse response)
            throws ServletException, IOException {
    }

    private void listAllMessages(HttpServletRequest request,
                                    HttpServletResponse response)
            throws ServletException, IOException {

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


        PrintWriter writer = response.getWriter();

        String responseText = messageList.toString();

        writer.write("HTTP/1.1 200 OK\n");
        writer.write("Content-Type: text/html\n");
        writer.write("Content-Length: " + responseText.length() + "\n");
        writer.write("\n");
        writer.write(responseText);
        writer.flush();

    }

    void doAction(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String action = ofNullable(request.getParameter("action")).orElse("list");

        switch (action){
            case "list": listAllMessages(request,response);
                break;
            case "send": sendMessage(request,response);
                break;
            default: listAllMessages(request,response);
        }
    }
}
