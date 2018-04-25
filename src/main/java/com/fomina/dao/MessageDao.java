package com.fomina.dao;

import com.fomina.dao.exceptions.DaoException;
import com.fomina.model.Message;

import java.util.List;

public interface MessageDao {

    public void createMessage(Message message) throws DaoException;
    public Message getMessage(Integer id) throws DaoException;
    public List<Message> listAll() throws DaoException;
    public void deleteMessage(Message message) throws DaoException;

}
