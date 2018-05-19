package com.fomina;

import com.fomina.dao.MessageDao;
import com.fomina.dao.UserDao;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by wiki_fi on 05/19/2018.
 * @author Vika Belka aka wiki_fi
 * @link https://vk.com/wiki_fi
 */
@WebServlet( name = "MyServlet", urlPatterns = {"/chat/*"})
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

        HttpSession session = request.getSession();
        String username = request.getParameter( "user_id" );

        if (username == null || username.isEmpty()) {
            session.setAttribute("user_id", "alice");
            request.setAttribute("user_id", "alice");
        } else {
            session.setAttribute("user_id", username);
        }



        String nextJSP = "/WEB-INF/jsp/index.jsp";
        request.getSession().getAttribute("user");
        request.getRequestDispatcher(nextJSP).forward(request, response);;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        controller.doAction(req,resp);
    }

}
