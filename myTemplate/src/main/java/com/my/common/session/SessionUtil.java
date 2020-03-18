package com.my.common.session;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static void setSession(Map<String, String> loginMap, HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession oldSession = req.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        HttpSession session = req.getSession();

        SessionVO vo = new SessionVO(loginMap);

        session.removeAttribute("sessionVO");
        session.setAttribute("sessionVO", vo);
    }
}
