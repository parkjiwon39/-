package com.spring.service;

import java.util.List;

import com.spring.domain.Criteria;
import com.spring.domain.ReplyPageVO;
import com.spring.domain.ReplyVO;

/* ������, �޼ҵ�� ��Ģ(ī�����̽�)
 * �ҹ��ڷ� ����
 * �δܾ ����Ǿ��� �� �ι�° �ܾ��� ù ���ۺκ��� �빮�ڷ� �ֱ�
 * 
 * �����ͺ��̽� ��Ģ(������ũ���̽�)
 * reply_service / reply_insert 
 */


public interface ReplyService {
	public boolean replyInsert(ReplyVO vo);
	public ReplyVO replyRead(int rno);
	public boolean replyUpdate(ReplyVO vo);
	public boolean replyDelete(int rno);
	public ReplyPageVO replyList(Criteria cri,int bno);

}
