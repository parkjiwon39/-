package com.spring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Criteria {
	private int pageNum; //������ ��ȣ
	private int amount;	//���������� ������ �Խù� ��
	
	private String type;//�˻�����
	private String keyword;//�˻���
	
	public Criteria() {
		this(1,10);
	}
	public Criteria(int pageNum,int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	//type ���� �޾Ƽ� �迭�� return
	//type : T {"T"}, C, W, TC, TW{"T","W"}, TCW{"T","C","W"}
	public String[] getTypeArr() {
		return type == null? new String[]{}:type.split("");
	}
}
