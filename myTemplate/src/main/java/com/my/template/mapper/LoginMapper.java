package com.my.template.mapper;

import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface LoginMapper {
	Map<String, String> loginUserValidation(Map<String, Object> param) throws Exception;
	Map<String, String> login(Map<String, ?> param) throws Exception;
	int insertLoginLog(Map<String, ?> param) throws Exception;
	int updateLogoutLog(Map<String, String> param) throws Exception;
}
