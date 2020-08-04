package com.spring.mapper;

import org.apache.ibatis.annotations.Param;

import com.spring.domain.AuthVO;
import com.spring.domain.ChangeVO;
import com.spring.domain.LoginVO;

public interface MemberMapper {
	public AuthVO login(LoginVO login);
	public String getpassword(String userid);
//	public int updatePwd(@Param("userid")String userid,@Param("new_password") String new_password);
	public int updatePwd(ChangeVO vo);
	public int deleteMember(String userid);

}
