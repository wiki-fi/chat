package com.fomina.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by wiki_fi on 05/19/2018.
 * @author Vika Belka aka wiki_fi
 * @link https://vk.com/wiki_fi
 */
@WebFilter(filterName = "ChatFilter", urlPatterns = {"/*"})
public class Filter implements javax.servlet.Filter {

    private FilterConfig filterConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        HttpSession session = httpServletRequest.getSession();
//        String uri = httpServletRequest.getRequestURI()
//                .substring(httpServletRequest.getContextPath().length());

//        String queryString = httpServletRequest.getQueryString();

//        String  query = queryString == null || queryString.isEmpty() ? "" : "?" + httpServletRequest.getQueryString();

//        System.out.println(uri + query);
        chain.doFilter(request, response);

//        if (uri.startsWith("/chat/")) chain.doFilter(request, response);
//
//        else {
//            // Not logged in, show login page.
//            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/chat" + uri + query);
//        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy(){
        filterConfig = null;
    }


}
