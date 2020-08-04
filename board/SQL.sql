create table spring_reply(
	rno number(10) constraint pk_reply primary key,
	bno number(10) not null,
	reply varchar2(1000) not null, 
	replyer varchar2(50) not null,
	replydate date default sysdate, 
	updatedate date default sysdate,
	constraint fk_spring_reply foreign key(bno) references spring_board(bno)
);


create sequence seq_reply;