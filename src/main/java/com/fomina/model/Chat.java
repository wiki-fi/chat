package com.fomina.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {

    // Properties ---------------------------------------------------------------------------------

    private static final com.fomina.model.Chat Chat = new Chat();
    private List<User> users;
    private List<Message> messages;

    // Constructor --------------------------------------------------------------------------------

    private Chat() {
        this.messages = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    // Getters & Setters --------------------------------------------------------------------------

    public static com.fomina.model.Chat getChat() {
        return Chat;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Message> getMessages() {
        return messages;
    }


    // Object overrides ---------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Chat{" +
                "users=" + users +
                ", messages=" + messages +
                '}';
    }
}
