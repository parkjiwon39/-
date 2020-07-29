package com.spring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.domain.AttachFileVO;
import com.spring.domain.BoardVO;
import com.spring.domain.Criteria;
import com.spring.domain.PageVO;
import com.spring.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board/*")
public class BoardController {
	@Autowired
	private BoardService service;

	@PreAuthorize("isAuthenticated()") //������ ������� ��� true
	@GetMapping("/register")
	public void registerGet() {
		log.info("register form ��û");
	}
	
	//�۵���ϱ�
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/register")
	public String registerPost(BoardVO vo,RedirectAttributes rttr) {
		log.info("�� ��� ��û"+vo);
		
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(attach+""));
		}
		if(service.insertBoard(vo)) {
			rttr.addFlashAttribute("result",vo.getBno());
			return "redirect:list";			
		}else {
			return "register";
		}
		
	}
	
	@GetMapping("/list")
	public void list(Model model,@ModelAttribute("cri") Criteria cri) {
		log.info("list ��û");
		//���� �������� ������ �Խù�
		model.addAttribute("list", service.getList(cri));
		//�ϴ��� ������ ������� ���õ� ����
		model.addAttribute("pageVO",new PageVO(cri,service.totalRows(cri)));
	}
	@GetMapping(value={"/read","/modify"})
	public void readGet(int bno,@ModelAttribute("cri") Criteria cri,Model model) {
		log.info("�� ����"+bno+"...."+cri);
		BoardVO vo=service.getBoard(bno);
		model.addAttribute("vo",vo);
		//http://localhost:8080/board/read
		//http://localhost:8080/board/modify
	}
	//�������
	@PreAuthorize("principal.username == #vo.writer")
	@PostMapping("/modify")
	public String modifyPost(BoardVO vo,Criteria cri,RedirectAttributes rttr) {
		log.info("���� ����"+vo+"...."+cri);
				
		if(service.updateBoard(vo)) {
		//rttr.addFlashAttribute("",""); $
		rttr.addAttribute("bno",vo.getBno());//�̰� ���� ������ �Ʒ��� ������� �����̴�.parameter�� ������� ����� ������ ���߱⶧���� ��
		rttr.addAttribute("pageNum", cri.getPageNum());//�ּ��ٿ� ���󰡴°� add
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:read";//read?bno=
	}else {
		rttr.addAttribute("bno",vo.getBno());
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:modify";
	}
}
	//���� ����
	@PreAuthorize("principal.username == #writer")
	@PostMapping("/remove")
	public String deletePost(int bno,String writer,Criteria cri,RedirectAttributes rttr) {
		log.info("�� ����"+bno);
		//���� �۹�ȣ�� �ش��ϴ� ÷������ ����� �������� �����ϱ� ���ؼ�
		//bno�� �ش��ϴ� ÷������ ����Ʈ ��������
		List<AttachFileVO> attachList=service.attachList(bno);
		
		
		service.deleteBoard(bno);
		deleteFiles(attachList);
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addFlashAttribute("result","success");
		return "redirect:list";
		
	}
	
	//÷�ι� �������� ��Ʈ�ѷ� �ۼ�
	@GetMapping("/getAttachList")
	public ResponseEntity<List<AttachFileVO>> getAttachList(int bno){
		log.info("÷�ι� �������� "+bno);
		return new ResponseEntity<List<AttachFileVO>>(service.attachList(bno),HttpStatus.OK);
	}
	
	//�Խñ� ���� �� ���� ������ ÷�ι� ����
	private void deleteFiles(List<AttachFileVO> attachList) {
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		for(AttachFileVO vo : attachList) {
			Path file = Paths.get("d:\\upload\\",vo.getUploadPath()+"\\"+vo.getUuid()+"_"+vo.getFileName());
			
			try {
				//�Ϲ�����, �̹��� ���� ���� ����
				Files.deleteIfExists(file);
				
				//����� ����
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumb = Paths.get("d:\\upload\\",vo.getUploadPath()+"\\s_"+vo.getUuid()+"_"+vo.getFileName());
					Files.delete(thumb);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}












