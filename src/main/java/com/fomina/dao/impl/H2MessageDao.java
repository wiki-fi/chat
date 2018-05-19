package com.fomina.dao.impl;

import com.fomina.dao.MessageDao;
import com.fomina.dao.exceptions.DaoException;
import com.fomina.dao.exceptions.MessageNotFoundException;
import com.fomina.model.Message;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class H2MessageDao implements MessageDao {

    // Properties ---------------------------------------------------------------------------------

    private DataSource connectionPool;

    // Constructor --------------------------------------------------------------------------------

    public H2MessageDao(DataSource dataSource) {
        this.connectionPool = dataSource;
    }

    // Methods --------------------------------------------------------------------------------

    @Override
    public void createMessage(Message message) throws DaoException {
        /*
         * https://stackoverflow.com/questions/19431234/converting-between-java-time-localdatetime-and-java-util-date
         */
        LocalDateTime ldt = LocalDateTime.ofInstant(message.getTimestamp().toInstant(), ZoneId.systemDefault());

        String statement = "INSERT INTO PUBLIC.MESSAGES (TIMESTAMP,TEXT,SENDER_ID,HASH) VALUES ('"
                +ldt+"','"+message.getText()+"','"+message.getSender().getId()+"','"+message.getId()+"')";

        System.out.println(statement);

        try(Connection con = getConnection()){
            boolean oldAutoCommit=con.getAutoCommit();
            con.setAutoCommit(false);//auto commit to false
            try(Statement st = con.createStatement()) {
                con.setAutoCommit(false);//auto commit to false
                int affectedRows = st.executeUpdate(statement);
                if (affectedRows == 0) {
                    throw new SQLException("Creating message failed, no rows affected.");
                }

//            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    message.setId(generatedKeys.getInt(1));
//                }
//                else {
//                    throw new SQLException("Creating message failed, no ID obtained.");
//                }
//            }
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
    }

    @Override
    public Message getMessage(Integer id) throws DaoException {

        String statement = "SELECT TIMESTAMP,TEXT,SENDER_ID,HASH FROM PUBLIC.MESSAGES WHERE (HASH="+id+");";

        System.out.println(statement);
        Message message = null;
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(statement)){

            if (rs.next()) {

                message = new Message(Date.from(rs.getTimestamp(1).toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()),
                        rs.getString(2),
                        (new H2UserDao(connectionPool)).getUserById(rs.getInt(3)),
                        rs.getInt(4)
                );
            } else {
                throw new MessageNotFoundException("message with id: "+id+" not found in database");
            }

        } catch (SQLException | MessageNotFoundException e) {
            throw new DaoException(e);
        }
        return message;
    }

    @Override
    public List<Message> listAll() throws DaoException {

        String statement = "SELECT TIMESTAMP,TEXT,SENDER_ID,HASH FROM PUBLIC.MESSAGES order by TIMESTAMP limit 30";

        List<Message> messageList= new ArrayList<>();
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(statement)){

            while (rs.next()) {

                messageList.add(new Message(Date.from(rs.getTimestamp(1).toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()),
                        rs.getString(2),
                        (new H2UserDao(connectionPool)).getUserById(rs.getInt(3)),
                        rs.getInt(4))
                );
            }

        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        }
        return messageList;
    }

    @Override
    public void deleteMessage(Message message) throws DaoException {

    }

    private Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

}
