package com.my.common.interceptor;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        LOGGER.debug("********************  LoginInterceptor preHandle  ***********************");

        HttpSession session = (HttpSession) request.getSession(false);

        if(session == null || session.getAttribute("sessionVO") == null) {
        	if(request.getRequestURI().indexOf(".json") > -1) {
				Map<String, String> errorMap = new HashMap<String, String>();
				errorMap.put("sessionValidYn", "N");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");	
				try (Writer writer = new OutputStreamWriter(response.getOutputStream())) {
					new Gson().toJson(errorMap, writer);
				} catch(Exception e) {
					LOGGER.info("*******  LoginInterceptor e =>" +e);
					e.printStackTrace();
				}
        	} else {
        		response.setContentType("text/html; charset=UTF-8");
        		PrintWriter out = response.getWriter();
        		out.println("<script>alert('Your session has expired. Please log in again.'); top.location.href='/sessionExpire.do';</script>");
        		out.flush();
        	}

        	return false;
        }
        return true;
    }

}
