package com.my.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service("messageUtil")
public class MessageUtil implements MessageSourceAware  {

    @SuppressWarnings("unused")
    private final static Logger logger = LoggerFactory.getLogger(MessageUtil.class);

    private static MessageSource  messageSource = null;

    public void setMessageSource(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    public String getMessage(String code, Object[] objs) {
        return messageSource.getMessage(code, objs, LocaleContextHolder.getLocale());
    }
}
