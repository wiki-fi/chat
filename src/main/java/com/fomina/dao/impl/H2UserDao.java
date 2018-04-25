package com.fomina.dao.impl;


import com.fomina.dao.UserDao;
import com.fomina.dao.exceptions.DaoException;
import com.fomina.dao.exceptions.UserNotFoundException;
import com.fomina.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2UserDao implements UserDao {

    // Properties ---------------------------------------------------------------------------------

    private String url;

    // Constructor --------------------------------------------------------------------------------

    public H2UserDao(String url) {
        this.url = url;
    }

    // Methods --------------------------------------------------------------------------------

    @Override
    public User addUser(User user) throws DaoException {
        String statement = "INSERT INTO PUBLIC.USERS (\"NAME\") VALUES ('"+user.getName()+"')";

        System.out.println(statement);

        try(Connection con = getConnection()){
            boolean oldAutoCommit=con.getAutoCommit();
            con.setAutoCommit(false);//auto commit to false
            try(Statement st = con.createStatement()) {
                con.setAutoCommit(false);//auto commit to false
                int affectedRows = st.executeUpdate(statement);
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating message failed, no ID obtained.");
                    }
                }
                con.commit();
            } catch (SQLException e) {
                try {
                    con.rollback();
                    throw new DaoException(e);
                } catch(SQLException excep) {
                    throw new DaoException(excep);
                }
            } finally {
                con.setAutoCommit(oldAutoCommit);//reset auto commit
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return user;

    }

    @Override
    public void deleteUser(User user) throws DaoException {

    }

    @Override
    public List<User> getAllUsers() throws DaoException {
        String statement = "SELECT ID,NAME FROM USERS limit 30 order by id asc;";
        System.out.println(statement);
        List<User> userList = new ArrayList<>();
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(statement)){

            while (rs.next()) {

                userList.add(new User(rs.getInt(1),
                        rs.getString(2))
                );
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return userList;

    }

    @Override
    public User getUserById(Integer userId) throws DaoException {
        String statement = "SELECT ID,NAME FROM USERS WHERE (ID="+userId+");";
        System.out.println(statement);
        User user = null;
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(statement)){

            if (rs.next()) {

                user = new User(rs.getInt(1),
                        rs.getString(2)
                );
            } else {
                throw new UserNotFoundException("user with id: "+userId+" not found in database");
            }

        } catch (SQLException | UserNotFoundException e) {
            throw new DaoException(e);
        }
        return user;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
