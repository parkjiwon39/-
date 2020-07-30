package com.spring.service;

import com.spring.domain.AuthVO;
import com.spring.domain.ChangeVO;
import com.spring.domain.LoginVO;

public interface MemberService {
	public AuthVO login(LoginVO login);
	//��й�ȣ����
	public String getPwd(String userid);//���񽺿����� �̸� �޶� �������
	//��й�ȣ����
	public boolean updatePwd(ChangeVO vo);
	//ȸ��Ż��
	public boolean delete(String userid);

}
