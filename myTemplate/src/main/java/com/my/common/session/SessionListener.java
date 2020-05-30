package com.my.common.session;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

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

        activeSessions.decrementAndGet();
    }
}