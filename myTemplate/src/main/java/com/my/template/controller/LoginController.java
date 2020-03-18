package com.my.template.controller;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.my.common.session.SessionUtil;
import com.my.common.session.SessionVO;
import com.my.template.service.LoginService;

@Controller
public class LoginController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private LoginService loginService;

    @Value("#{servletContext.contextPath}")
    private String servletContextPath;

    /**
     * 로그인 페이지 이동
     * @param RequestParam
     * @param ModelMap
     * @param HttpSession
     * @param Locale
     * @throws Exception
     */
	@RequestMapping(value = { "/", "/index.do" })
	public String index(@RequestParam Map<String, String> paramMap,  ModelMap modelMap, HttpSession session, Locale lang) throws Exception {
        SessionVO sessionVO = (SessionVO)session.getAttribute("sessionVO");

        if(sessionVO!=null) {
            modelMap.put("sessionUserId", sessionVO.getUserId());
        }

		return "login";
	}

    /**
     * 로그인
     * @param RequestParam
     * @param ModelMap
     * @param HttpServletRequest
     * @param HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value="/login.do")
    public String login(@RequestParam Map<String, ?> paramMap, ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) throws Exception {

    	LOGGER.debug("login.do paramMap =====> " + paramMap);

        try {

        	Map<String, String> loginMap = loginService.login(paramMap);
    		LOGGER.debug("loginMap =====> " + loginMap);

    		if(loginMap!=null && !"".equals(loginMap.get("userId"))) {
    			SessionUtil.setSession(loginMap, req, res);
    		} else {
                throw new Exception("LOGIN_01");
            }

    		modelMap.put("url", servletContextPath+"/loginConfirm.do");

        	return "forward";
        } catch(Exception e) {
            String errCd = e.getMessage();
            LOGGER.error("login Exception errCd==>" + errCd);
            String returnMsg = "로그인을 실패하였습니다.";
            if(errCd!=null && errCd.equals("LOGIN_01")) {
                returnMsg = "아이디 혹은 비밀번호가 일치하지 않습니다.";
            }

            modelMap.put("message", returnMsg);
            modelMap.put("script", "history.back();");
            return "loginFail";
        }
    }

    /**
     * 로그인 후 이동 경로 선택
     * @param RequestParam
     * @param ModelMap
     * @param HttpSession
     * @throws Exception
     */
    @RequestMapping(value="/loginConfirm.do")
    public String loginConfirm(@RequestParam Map<String, ?> paramMap, ModelMap modelMap, HttpSession session) throws Exception {
        LOGGER.debug("############  loginConfirm  ##########");

        String rtnPage = "";
        SessionVO sessionVO = (SessionVO)session.getAttribute("sessionVO");

        if(sessionVO!=null) {
            rtnPage = "main";
        } else {
        	modelMap.put("url", servletContextPath+"/logout.do");
        	rtnPage = "forward";
        }

        return rtnPage;
    }

    /**
     * 로그아웃
     * @param HttpServletRequest
     * @param ModelMap
     * @throws Exception
     */
    @RequestMapping(value="/logout.do")
    public String logout(HttpServletRequest req, ModelMap modelMap) throws Exception {
        HttpSession session = req.getSession(false);
        if( session != null ) {
            session.invalidate();
        }
        return "logout";
    }

    /**
     * 세션만료
     * @param HttpServletRequest
     * @param ModelMap
     * @throws Exception
     */
    @RequestMapping(value = "/sessionExpire.do")
    public String sessionExpire(HttpServletRequest req, ModelMap modelMap) throws Exception {
        LOGGER.debug("################# sessionExpire ####################");
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        modelMap.put("url", servletContextPath+"/index.do");
        return "forward";
    }
}
