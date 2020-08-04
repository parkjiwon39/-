package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/*")
public class MemberController {
			
// 회원 가입 폼 이동
@RequestMapping(value = "/memberJoinForm.do")
public String memberJoinForm() throws Exception{
	return ("/member/memberJoinForm");
	}
}



