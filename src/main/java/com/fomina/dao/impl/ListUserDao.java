package com.fomina.dao.impl;

import com.fomina.dao.UserDao;
import com.fomina.dao.exceptions.DaoException;
import com.fomina.dao.exceptions.UserNotFoundException;
import com.fomina.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class ListUserDao implements UserDao {

    // Properties ---------------------------------------------------------------------------------

    private Map<Integer, User> users;

    // Constructor --------------------------------------------------------------------------------

    public ListUserDao(List<User> users) {
        Map<Integer, User> userz = new ConcurrentHashMap<>();
        users.forEach(u-> userz.put(u.getId(),u));
        this.users = userz;
    }

    // Methods --------------------------------------------------------------------------------

    @Override
    public User addUser(User user) throws DaoException {
        User user1 = new User(ThreadLocalRandom.current().nextInt(),user.getName());
        users.put(user1.getId(), user1);
        return user1;
    }

    @Override
    public void deleteUser(User user) throws DaoException {
        users.remove(user.getId());
    }

    @Override
    public List<User> getAllUsers() throws DaoException {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(Integer userId) throws DaoException {
        if (!users.containsKey(userId)) {
            throw new UserNotFoundException("user with id: "+userId+" not found in database");
        }
        return users.get(userId);
    }
}
