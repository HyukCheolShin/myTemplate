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

    public Map<String, String> dbTest( Map<?, ?> param) throws Exception {
        try {
            Map<String, String> dbTestMap = loginMapper.dbTest(param);
            return dbTestMap;
        } catch(Exception e) {
            LOGGER.error(" LoginService Exception===============>" + e);
            return null;
        }
    }
}