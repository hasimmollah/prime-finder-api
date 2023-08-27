package com.nw.primefinder.security;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class XssFilter extends OncePerRequestFilter {
    private final IXssFilterService filterService;

    public XssFilter(final IXssFilterService filterService) {

        this.filterService = filterService;

    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        Enumeration<String> headers = httpServletRequest.getHeaderNames();
        if (null != headers) {
            while (headers.hasMoreElements()) {
                String headerName = headers.nextElement();
                filterService.filterString(httpServletRequest.getHeader(headerName));
                filterService.filterString(headerName);
            }
        }
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");

       httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        filterChain.doFilter(httpServletRequest, httpServletResponse );
    }

}
