package com.doc.cloud.base.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by majun on 02/02/2018.
 */
public class GetRequestAndResponseFilter implements Filter {

    static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<>();

    static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        requestLocal.set(request);
        responseLocal.set(response);

        chain.doFilter(request,response);

        clearLocal();

    }

    private void clearLocal(){
        requestLocal.remove();
        responseLocal.remove();
    }

    public static HttpServletRequest getCurrentRequest(){
        return requestLocal.get();
    }

    public static HttpServletResponse getCurrentResponse(){
        return responseLocal.get();
    }

    @Override
    public void destroy() {

    }
}
