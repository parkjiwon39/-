create table spring_reply(
	rno number(10) constraint pk_reply primary key,-- ��� �۹�ȣ
	bno number(10) not null,-- ���� �� ��ȣ
	reply varchar2(1000) not null, -- ��� ����
	replyer varchar2(50) not null, -- ��� �ۼ���
	replydate date default sysdate, -- ��� �ۼ���
	updatedate date default sysdate,-- ��� ������
	constraint fk_spring_reply foreign key(bno) references spring_board(bno) -- �ܷ�Ű
);


create sequence seq_reply;