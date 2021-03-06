package com.fomina.listener;

import com.fomina.dao.impl.H2MessageDao;
import com.fomina.dao.impl.H2UserDao;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by wiki_fi on 05/19/2018.
 * @author Vika Belka aka wiki_fi
 * @link https://vk.com/wiki_fi
 */
@WebListener
public class InitListener implements ServletContextListener {

    private final Logger logger = LoggerFactory.getLogger(InitListener.class);


    private DataSource connectionPool;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        Properties dbProperties = new Properties();
        try(InputStream props = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            dbProperties.load(props);
        } catch (Exception e) {
            e.printStackTrace();
        }

        connectionPool = new DataSource();
        connectionPool.setDriverClassName(dbProperties.getProperty("driver"));
        connectionPool.setUrl(dbProperties.getProperty("url"));
        connectionPool.setUsername(dbProperties.getProperty("user"));
        connectionPool.setPassword(dbProperties.getProperty("password"));
        connectionPool.setInitialSize(Integer.valueOf(dbProperties.getProperty("poolSize")));
        connectionPool.setMaxActive(10);
        connectionPool.setMaxIdle(5);
        connectionPool.setMinIdle(2);

        String ddl = new BufferedReader(new InputStreamReader(InitListener.class
                .getClassLoader()
                .getResourceAsStream("database.ddl")))
                .lines()
                .collect(Collectors.joining("\n"));

        try(Statement st = connectionPool.getConnection().createStatement()){
            st.execute(ddl);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("dataSource", connectionPool);
        servletContext.setAttribute("messageDao", new H2MessageDao(connectionPool));
        servletContext.setAttribute("userDao", new H2UserDao(connectionPool));

        ClassLoader ctcc = Thread.currentThread().getContextClassLoader();

        logger.info("Classload hashcode is " + ctcc.hashCode());
        logger.info("Initializing for ServletContext [" +
                servletContext.getServletContextName() + "]");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        logger.info("Shutting down ServletContext [" +
                servletContext.getServletContextName() + "]");
        try {
            connectionPool.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ClassLoader ctcc = Thread.currentThread().getContextClassLoader();
        logger.info("Classload hashcode is " + ctcc.hashCode());
    }


}
