package com.fomina.dao;

import com.fomina.dao.exceptions.DaoException;
import com.fomina.model.User;

import java.util.List;

public interface UserDao {

    public User addUser(User user) throws DaoException;
    public void deleteUser(User user) throws DaoException;
    public List<User> getAllUsers() throws DaoException;
    public User getUserById(Integer userId) throws DaoException;
}
