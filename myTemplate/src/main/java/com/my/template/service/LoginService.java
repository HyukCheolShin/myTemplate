package com.my.template.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.template.mapper.LoginMapper;

@Service("loginService")
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

	public Map<String, String> loginUserValidation(Map<String, Object> param) throws Exception {
        LOGGER.debug(" LoginService loginUserValidation param ===============>" + param);

		try {
			Map<String, String> resultMap = loginMapper.loginUserValidation(param);
			return resultMap;
		} catch(Exception e) {

			LOGGER.error(" LoginService loginUserValidation Exception===============>" + e);
			return null;
		}
	}

	public Map<String, String> login(Map<String, ?> param) throws Exception {
        LOGGER.debug(" LoginService login param ===============>" + param);
		try {
			Map<String, String> loginMap = loginMapper.login(param);

			return loginMap;
		} catch(Exception e) {
			LOGGER.error(" LoginService login Exception===============>" + e);
			return null;
		}
	}

	public void insertLoginLog(Map<String, ?> param) throws Exception {
		LOGGER.debug(" LoginService insertLoginLog param ===============>" + param);
		try {
			loginMapper.insertLoginLog(param);
		} catch(Exception e) {
			LOGGER.error(" LoginService insertLoginLog Exception===============>" + e);
		}
	}

}