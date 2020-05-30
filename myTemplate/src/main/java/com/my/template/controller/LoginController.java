package com.my.template.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.common.session.SessionUtil;
import com.my.common.session.SessionVO;
import com.my.common.util.MessageUtil;
import com.my.template.service.LoginService;

@Controller
public class LoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private LoginService loginService;

    @Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;

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
     * 사용자 체크
     * @param map
     * @param sessionVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/loginUserValidation.json")
    public @ResponseBody Map<String, String> loginUserValidation(@RequestBody Map<String, Object> map) throws Exception {

    	LOGGER.debug("loginUserValidation.do map =====> " + map);

    	Map<String, String> rtnMap = new HashMap<String, String>();

    	Map<String, String> resultMap = loginService.loginUserValidation(map);

    	if(resultMap!=null) {
    		rtnMap.put("returnCd", "Y");
    	} else {
    		rtnMap.put("returnCd", "NOT_FOUND_USER");
    	}

        return rtnMap;
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
            	String userPassword = (String) paramMap.get("userPassword");
                String dbUserPassword = (String) loginMap.get("userPassword");

                if(bcryptPasswordEncoder.matches(userPassword, dbUserPassword)) {
                	SessionUtil.setSession(loginMap, req, res);

                	loginMap.put("sessionId", req.getSession().getId());
                	loginService.insertLoginLog(loginMap);
                } else {
                	throw new Exception("PASSWORD_DO_NOT_MATCH");
                }
            } else {
                throw new Exception("NOT_FOUND_USER");
            }
            modelMap.put("url", servletContextPath+"/loginConfirm.do");

            return "forward";
        } catch(Exception e) {
            String errCd = e.getMessage();
            LOGGER.error("login Exception errCd==>" + errCd);
            String returnMsg = messageUtil.getMessage("login.message.loginFailed");
            if(errCd!=null && errCd.equals("NOT_FOUND_USER")) {
                returnMsg = messageUtil.getMessage("login.message.userIdNotFound");
            } else if(errCd!=null && errCd.equals("PASSWORD_DO_NOT_MATCH")) {
                returnMsg = messageUtil.getMessage("login.message.passwordsDoNotMatch");
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
