package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.domain.Criteria;
import com.spring.domain.ReplyPageVO;
import com.spring.domain.ReplyVO;
import com.spring.service.ReplyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/replies/*")
public class ReplyController {
	
	@Autowired
	private ReplyService service;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/new") //http://localhost:8080/replies/new + post
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
		log.info("��� ���..."+vo.toString());
		
		return service.replyInsert(vo)?
				new ResponseEntity<String>("success",HttpStatus.OK):
				new ResponseEntity<String>("fail",HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	//��� �ϳ� �������� http://localhost:8080/replies/3
	@GetMapping("/{rno}")
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") int rno) {
		log.info("��� ��������"+rno);
		return new ResponseEntity<>(service.replyRead(rno),HttpStatus.OK);
		}
	//��� �����ϱ� http://localhost:8080/replies/3 + put
	@PreAuthorize("principal.username == #vo.replyer")
	@PutMapping("/{rno}")
	public ResponseEntity<String> modify(@PathVariable("rno") int rno,@RequestBody ReplyVO vo) {
		log.info("��� ���� : "+rno+"���� : "+vo.getReply()+"��� �ۼ��� : "+vo.getReplyer());
		
		//rno�� vo�� ����ֱ�
		vo.setRno(rno);
		return service.replyUpdate(vo)?
				new ResponseEntity<String>("success",HttpStatus.OK):
				new ResponseEntity<String>("fail",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	//��� ���� http://localhost:8080/replies/3 + delete
	@PreAuthorize("Principal.username == #vo.replyer")
	@DeleteMapping("/{rno}")
	public ResponseEntity<String> delete(@PathVariable("rno") int rno,
			@RequestBody ReplyVO vo) {
		log.info("��� ���� : "+rno+"��� �ۼ���"+vo.getReplyer());
		
		//rno�� vo�� ����ֱ�
		
		return service.replyDelete(rno)?
				new ResponseEntity<String>("success",HttpStatus.OK):
				new ResponseEntity<String>("fail",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//�۹�ȣ�� �ش��ϴ� ��� ����Ʈ ��������
	//http://localhost:8080/replies/pages/{bno}/{pageNum}
	//http://localhost:8080/replies/pages/1158/1
	//1158���� �ش��ϴ� ù��° ������ ��� ��������
	@GetMapping("/pages/{bno}/{page}")
	public ResponseEntity<ReplyPageVO> getlist(@PathVariable("bno") int bno,
		@PathVariable("page") int page){
		log.info("��� �������� "+bno+" page = "+page);
		
		Criteria cri = new Criteria(page,10);
		return new ResponseEntity<ReplyPageVO>(service.replyList(cri, bno),HttpStatus.OK);
		}
		
	}
























