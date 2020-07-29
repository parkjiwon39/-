package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.domain.AttachFileVO;
import com.spring.domain.BoardVO;
import com.spring.domain.Criteria;
import com.spring.mapper.AttachMapper;
import com.spring.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper mapper;
	@Autowired
	private AttachMapper attach;
	
	@Transactional
	@Override
	public boolean insertBoard(BoardVO vo) {	
		//게시글 db 저장 요청
		boolean result = mapper.insert(vo)==1?true:false;
		//첨부파일 db 저장 요청
		if(vo.getAttachList() == null || vo.getAttachList().size() <= 0) {
			return result;
		}
		vo.getAttachList().forEach(attach1 -> {
			attach1.setBno(vo.getBno());
			attach.insert(attach1);
		});
		return true;
	}
	@Transactional
	@Override
	public boolean updateBoard(BoardVO vo) {
		//현재 bno의 첨부파일 db에서 삭제
		attach.delete(vo.getBno());
		//첨부파일 삽입		
		if(vo.getAttachList() != null && vo.getAttachList().size() >= 0) {
			for(AttachFileVO attach1 : vo.getAttachList()) {
				attach1.setBno(vo.getBno());
				attach.insert(attach1);
			}
		}
		return mapper.update(vo)==1?true:false;
	}

	@Transactional
	@Override
	public boolean deleteBoard(int bno) {
		attach.delete(bno);
		return mapper.delete(bno)==1?true:false;
	}

	@Override
	public BoardVO getBoard(int bno) {		
		return mapper.read(bno);		
	}
	

	@Override
	public List<BoardVO> getList(Criteria cri) {		
		return mapper.list(cri);
	}

	@Override
	public int totalRows(Criteria cri) {
		return mapper.total(cri);
	}

	@Override
	public List<AttachFileVO> attachList(int bno) {
		return attach.select(bno);
	}

	

}






