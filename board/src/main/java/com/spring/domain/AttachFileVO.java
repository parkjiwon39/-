package com.spring.domain;

import lombok.Data;

@Data
public class AttachFileVO {
	
	private String uuid;//uuid
	private String uploadPath; //��¥���� ���� ����
	private String fileName; //���� ���ϸ�
	private boolean fileType; //�̹��� ����
	private int bno; //���� �� ��ȣ
}
