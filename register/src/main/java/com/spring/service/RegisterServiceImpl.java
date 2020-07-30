package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.RegisterVO;
import com.spring.mapper.RegisterMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegisterServiceImpl implements RegisterService {
	
	@Autowired
	private RegisterMapper mapper;
	
	@Override
	public boolean regist(RegisterVO vo) {
		log.info("회원가입 서비스 호출....");
		return mapper.register(vo) > 0? true:false;
	}

	@Override
	public boolean dupId(String userid) {
		return mapper.dupId(userid) > 0 ? false:true;
	}
	
	

}
