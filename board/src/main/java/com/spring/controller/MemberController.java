package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	@GetMapping("/login")
	public void loginForm() {
		log.info("�α��� �� ��û");
	}
	
	@GetMapping("/admin")
	public void adminForm() {
		log.info("������ ������ ��û");
	}

}