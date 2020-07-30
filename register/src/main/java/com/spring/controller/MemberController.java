package com.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.domain.AuthVO;
import com.spring.domain.ChangeVO;
import com.spring.domain.LoginVO;
import com.spring.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	//�α���
	@GetMapping("/login")
	public void login() {
		log.info("login ��û��");		
	}
	
	//�α��� ó��
	@PostMapping("/login")
	public String loginPost(LoginVO login,HttpSession session) {
		//login.jsp���� �ѱ� �� ��������
		log.info("�α��� ��û"+login);
		//�α��� Ȯ�� => ������ index �����ֱ� / ���� �α��� ������
		AuthVO auth = service.login(login);
		if(auth!=null) {
			//���ǿ� �� ���
			session.setAttribute("auth", auth);
			return "redirect:/";
		}else {
			return "/member/login";
		}
		
	}
	//�α׾ƿ� => ���� ���� �� �ε��� ������ �����ֱ�
	@GetMapping("/logout") //localhost:8080/member/logout
	public String logout(HttpSession session) {
		log.info("�α׾ƿ� ��û");
		//���ǿ� �ִ� ��� ���� ����
		session.invalidate();
		//���ǿ� �ִ� Ư���� ���� ����
		//session.removeAttribute("auth");
		return "redirect:/";
	}
	//��й�ȣ ���� ���� �����ִ� ��Ʈ�ѷ� ����
	/*@GetMapping("/changePwd")
	public String chagePwd() {
		log.info("��й�ȣ ���� �� �����ֱ�");
		return ("/member/changePwd");
	}*/
	@GetMapping("/changePwd")
	public void chagePwd() {
		log.info("��й�ȣ ���� �� �����ֱ�");
		
	}
	//��й�ȣ ���� ��û ó�� ��Ʈ�ѷ� ����
		@PostMapping("/changePwd")
		public String changePwdPost(ChangeVO change,HttpSession session,RedirectAttributes rttr) {
		log.info("��й�ȣ ���� "+change);
		
		//1. userid �˾Ƴ���
		AuthVO auth = (AuthVO)session.getAttribute("auth");
		//2. userid�� ��ġ�ϴ� ��й�ȣ ����
		String current_password = service.getPwd(auth.getUserid());
		//3. ����ڰ� �Է��� ���� ��й�ȣ�� ��ġ�ϴ��� Ȯ��
		if(current_password.equals(change.getPassword())) {
			//��ġ�Ѵٸ� �����۾� ����
			//new_password�� confirm_password�� ��ġ�ϴ��� Ȯ��
			if(change.newPasswordEqualsConfirm()) {
				//userid ����ֱ�
				change.setUserid(auth.getUserid());
				if(service.updatePwd(change)) {
					//�����۾��� �����ϸ� �������� �� �α��� �������� �̵�
					session.removeAttribute("auth");
					rttr.addFlashAttribute("info", "��й�ȣ������ �����߽��ϴ�.�ٽ� �α����� �ּ���");
					return "redirect:/";
				}
			}else { //�ΰ��� ��й�ȣ�� ��ġ���� ���� �� changePwd
				rttr.addFlashAttribute("error", "�� ���� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				return "redirect:/member/changePwd";
			}
		}else { //���� ��й�ȣ�� ��ġ���� ���� �� changePwd
			rttr.addFlashAttribute("error", "���� ��й�ȣ�� Ȯ���� �ּ���");
			return "redirect:/member/changePwd";
		}
		return "redirect:/";
	}
	//ȸ��Ż�� ��ư�� Ŭ���� leaveForm �����ֱ�
	@GetMapping("/leave")
	public void leaveForm() {
		log.info("ȸ��Ż�� �� ��û");
	}
	/*//ȸ��Ż��
	@PostMapping("/leave")
	public String leavePost(LeaveVO leave,HttpSession session,RedirectAttributes rttr) {
	AuthVO auth = (AuthVO)session.getAttribute("auth");
	//userid�� ��ġ�ϴ� ��й�ȣ ����
	String current_password = service.getPwd(auth.getUserid());
	//��ġ�Ѵٸ� �����۾� ����
	if(leave.PasswordEqualsConfirm()) {
		session.removeAttribute("auth");
		rttr.addFlashAttribute("leave", "ȸ��Ż��");
		return "redirect:/";
	//������ �����ϸ� ���� ���� �ε��� �������� �̵�
	//���� ���н� leave ������ �����ֱ�
	}else {
		rttr.addFlashAttribute("error", "ȸ��Ż�� ����");
		return "redirect:/member/leave";
	}
	}*/
	//ȸ��Ż��
	@PostMapping("/leave")
	public String leave(String userid,String current_password,HttpSession session) {
		log.info("Ż�� ��û...");
		log.info("userid : "+userid);
		log.info("current_password : "+current_password);
		
		//userid�� ��ġ�ϴ� ��й�ȣ ����
		String password = service.getPwd(userid);
		
		if(password.equals(current_password)) {
			//��ġ�Ѵٸ� �����۾� ����
			if(service.delete(userid)) {
			//������ �����ϸ� ���� ���� �ε��� �������� �̵�
				session.removeAttribute("auth");
				return "redirect:/";
			}else {
				//���� ���н� leave ������ �����ֱ�
				return "redirect:/member/leave";
			}
		}
		return "redirect:/";	
	}
}

