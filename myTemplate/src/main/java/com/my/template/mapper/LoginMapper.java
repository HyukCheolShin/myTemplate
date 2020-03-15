package com.my.template.mapper;

import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface LoginMapper {
	Map<String, String> dbTest(Map<?, ?> param) throws Exception;
}
