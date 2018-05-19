package com.fomina.dao.impl;

import com.fomina.dao.MessageDao;
import com.fomina.dao.exceptions.DaoException;
import com.fomina.dao.exceptions.MessageNotFoundException;
import com.fomina.model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ListMessageDao implements MessageDao {

    // Properties ---------------------------------------------------------------------------------

    private Map<Integer, Message> messages;

    // Constructor --------------------------------------------------------------------------------


    public ListMessageDao(List<Message> messages) {
        Map<Integer, Message> msgs = new ConcurrentHashMap<>();
        messages.forEach(m -> msgs.put(m.getId(),m));
        this.messages = msgs;
    }

    // Methods --------------------------------------------------------------------------------

    @Override
    public void createMessage(Message message) throws DaoException {
        messages.put(message.getId(),message);
    }

    @Override
    public Message getMessage(Integer id) throws DaoException {

        if (!messages.containsKey(id)) {
            throw new MessageNotFoundException("message with id: "+id+" not found in database");
        }

        return messages.get(id);
    }

    @Override
    public List<Message> listAll() throws DaoException {
        
        List<Message> msgs = new ArrayList<>(messages.values());
        
        Collections.sort(msgs);
        return msgs;
    }

    @Override
    public void deleteMessage(Message message) throws DaoException {
        messages.remove(message.getId());
    }
}
