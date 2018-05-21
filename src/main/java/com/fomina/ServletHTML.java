package com.fomina;

import com.fomina.dao.MessageDao;
import com.fomina.dao.UserDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wiki_fi on 05/19/2018.
 * @author Vika Belka aka wiki_fi
 * @link https://vk.com/wiki_fi
 */
@WebServlet( name = "MyServlet", urlPatterns = {""}, asyncSupported=true)
public class ServletHTML extends HttpServlet {

    private Controller controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        controller = new Controller((MessageDao) sc.getAttribute("messageDao"),
                (UserDao) sc.getAttribute("userDao"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        controller.doGetAction(request,response);
    }



}
