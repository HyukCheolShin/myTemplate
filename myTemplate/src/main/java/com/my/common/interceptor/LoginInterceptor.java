package com.my.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        LOGGER.debug("********************  LoginInterceptor preHandle  ***********************");

        return true;
    }

}
