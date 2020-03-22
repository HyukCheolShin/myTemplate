package com.my.template.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("sessionVO")
@RequestMapping("/common")
public class CommonController {

    @SuppressWarnings("unused")
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    /********************************************************************************************************************
     * 공통 include
     ********************************************************************************************************************/      
    @RequestMapping(value="/ajaxCommon.do")
    public String ajaxCommon() throws Exception {
    	return "include/ajaxCommon";
    }
}