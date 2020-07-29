package com.spring.domain;

import lombok.Getter;

@Getter
public class PageVO {
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private int total;
	private Criteria cri;
	
	public PageVO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		//������ ������ ���
		endPage = (int)(Math.ceil(cri.getPageNum()/10.0))*10;
		//���������� ���
		startPage = this.endPage-9;
		//������ �������� 10���� �ȵ� ���� �ֱ� ������
		//������ ������ ������ ���ϱ�
		int realEnd = (int)(Math.ceil((total/1.0) / cri.getAmount()));
		if(realEnd < this.endPage) {
			endPage = realEnd;
		}
		this.prev = startPage > 1;
		this.next = endPage < realEnd;
	}
}
