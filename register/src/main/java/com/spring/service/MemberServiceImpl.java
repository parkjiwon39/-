package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.AuthVO;
import com.spring.domain.ChangeVO;
import com.spring.domain.LoginVO;
import com.spring.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper mapper;
	
	@Override
	public AuthVO login(LoginVO login) {		
		return mapper.login(login);
	}

	@Override
	public String getPwd(String userid) {		
		return mapper.getpassword(userid);
	}

	@Override
	public boolean updatePwd(ChangeVO vo) {		
		return mapper.updatePwd(vo)==1 ? true:false;
	}

	@Override
	public boolean delete(String userid) {
		return mapper.deleteMember(userid) == 1? true:false;
	}

}
