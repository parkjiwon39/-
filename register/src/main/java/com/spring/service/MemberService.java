package com.spring.service;

import com.spring.domain.AuthVO;
import com.spring.domain.ChangeVO;
import com.spring.domain.LoginVO;

public interface MemberService {
	public AuthVO login(LoginVO login);
	//비밀번호추출
	public String getPwd(String userid);//서비스에서는 이름 달라도 상관없음
	//비밀번호변경
	public boolean updatePwd(ChangeVO vo);
	//회원탈퇴
	public boolean delete(String userid);

}
