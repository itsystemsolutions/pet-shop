package com.thesis.petshop.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestFilterConfig extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse returnResponse = (HttpServletResponse) response;

        returnResponse.setHeader("Access-Control-Allow-Origin",  "*");
        returnResponse.setHeader("Access-Control-Allow-Methods",  "GET, HEAD, OPTIONS, POST, PUT, DELETE, PATCH");
        returnResponse.setHeader("Access-Control-Allow-Headers",  "Origin, X-Requested-With, Content-Type, Accept, Authorization");

        chain.doFilter(request, response);
    }
}