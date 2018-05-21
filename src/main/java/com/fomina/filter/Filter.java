package com.fomina.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by wiki_fi on 05/19/2018.
 * @author Vika Belka aka wiki_fi
 * @link https://vk.com/wiki_fi
 */
@WebFilter(filterName = "ChatFilter", urlPatterns = {"/*"})
public class Filter implements javax.servlet.Filter {

    private FilterConfig filterConfig;
    private final Logger logger = LoggerFactory.getLogger(Filter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        HttpSession session = httpServletRequest.getSession();
        String uri = httpServletRequest.getRequestURI()
                .substring(httpServletRequest.getContextPath().length());

        String queryString = httpServletRequest.getQueryString();

        String  query = queryString == null || queryString.isEmpty() ? "" : "?" + httpServletRequest.getQueryString();

        logger.info(uri + query);
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
