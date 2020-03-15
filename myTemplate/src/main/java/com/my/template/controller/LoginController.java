package com.my.template.controller;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.my.template.service.LoginService;

@Controller
public class LoginController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private LoginService loginService;

	@RequestMapping(value = { "/", "/index.do" })
	public String index(Locale lang, @RequestParam Map<String, String> paramMap,  ModelMap modelMap) throws Exception {

		LOGGER.debug("index.do paramMap =====> " + paramMap);

		 Map<?, ?> dbTestMap = loginService.dbTest(paramMap);

         LOGGER.debug("dbTestMap =====> " + dbTestMap);

		return "home";
	}
}
