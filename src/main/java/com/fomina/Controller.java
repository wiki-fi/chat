package com.fomina;

import com.fomina.dao.MessageDao;
import com.fomina.dao.UserDao;
import com.fomina.dao.exceptions.DaoException;
import com.fomina.model.Message;
import com.fomina.model.User;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private Gson gson = new Gson();
    private GsonBuilder gsonBuilder = new GsonBuilder();
    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    private JsonDeserializer<Message> deserializer = new JsonDeserializer<Message>() {
        @Override
        public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            User user = new User("alice");

            try {
                Integer user_id = jsonObject.get("sender").getAsJsonObject().get("id").getAsInt();
                user = userDao.getUserById(user_id);
            } catch (DaoException ex) {
                try {
                    user = userDao.addUser(new User("alisa"));
                } catch (DaoException e) {
                    e.printStackTrace();
                }
            }

            return new Message(
                    jsonObject.get("text").getAsString(),
                    new Date(jsonObject.get("timestamp").getAsLong()),
                    user);
        }
    };

    // Constructors -------------------------------------------------------------------------------



    Controller(MessageDao messageDao, UserDao userDao) {
        this.messageDao = messageDao;
        this.userDao = userDao;
        gsonBuilder.registerTypeAdapter(Message.class, deserializer);
        gsonBuilder.setDateFormat(DateFormat.FULL, DateFormat.FULL);
        gson = gsonBuilder.create();
    }

    // Methods ------------------------------------------------------------------------------------


    void doPostAction(HttpServletRequest request,
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

    void doGetAction(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        //HttpSession session = request.getSession();
        String username = request.getParameter( "user_id" );

        if (username == null || username.isEmpty()) {
//            session.setAttribute("user_id", "alice");
            request.setAttribute("user_id", "alice");
        } else {
//            session.setAttribute("user_id", username);
        }

        String nextJSP = "/WEB-INF/jsp/index.jsp";
//        request.getSession().getAttribute("user");
        request.getRequestDispatcher(nextJSP).forward(request, response);
    }


    private void sendMessage(HttpServletRequest request,
                             HttpServletResponse response)
            throws ServletException, IOException {

        String json = new BufferedReader(request.getReader()).lines().collect(Collectors.joining("\n"));

        logger.info(json);

        Message message = gson.fromJson(json, Message.class);

        try {
            messageDao.createMessage(message);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        listAllMessages(request,response);

    }

    private void listAllMessages(HttpServletRequest request,
                                 HttpServletResponse response)
            throws ServletException, IOException {

        List<Message> msgs = new ArrayList<>();
        try {
            msgs = messageDao.listAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }

        String jsonObject = gson.toJson(msgs);
        response.setContentType("application/json;charset=UTF-8");
        logger.info(jsonObject);
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
    }
}
