package com.fomina;

import com.fomina.dao.MessageDao;
import com.fomina.dao.UserDao;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wiki_fi on 05/19/2018.
 * @author Vika Belka aka wiki_fi
 * @link https://vk.com/wiki_fi
 */
@WebServlet( name = "MyServlet", urlPatterns = {"/chat/*",""}, asyncSupported=true)
public class Servlet extends HttpServlet {

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        controller.doPostAction(req,resp);
    }

}
