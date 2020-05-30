package com.my.common.session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.my.template.mapper.LoginMapper;

public class SessionListener implements HttpSessionListener {
 
    @SuppressWarnings("unused")
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    private final AtomicInteger activeSessions;

    public SessionListener() {
        super();
 
        activeSessions = new AtomicInteger();
    }
 
    public int getTotalActiveSession() {
        return activeSessions.get();
    }
 
    public void sessionCreated(final HttpSessionEvent event) {

        activeSessions.incrementAndGet();
    }

    public void sessionDestroyed(final HttpSessionEvent event) {

    	HttpSession session = event.getSession();
    	SessionVO sessionVO = (SessionVO)session.getAttribute("sessionVO");

    	if(sessionVO!=null) {
    		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
    		LoginMapper loginMapper = (LoginMapper) ctx.getBean("loginMapper");
    		
    		String sessionId = event.getSession().getId();
    		
    		try {
    			Map<String, String> loginMap = new HashMap<String, String>();
    			loginMap.put("sessionId", sessionId);
    			loginMapper.updateLogoutLog(loginMap);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}

        activeSessions.decrementAndGet();
    }
}