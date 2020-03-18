package com.my.common.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        LOGGER.debug("********************  LoginInterceptor preHandle  ***********************");

        HttpSession session = (HttpSession) request.getSession(false);

        if(session == null || session.getAttribute("sessionVO") == null) {

            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('세션이 만료되었습니다. 다시 로그인하여 주십시오.'); top.location.href='/sessionExpire.do';</script>");
            out.flush();

        	return false;
        }

        return true;
    }

}
