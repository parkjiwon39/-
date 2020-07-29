create table spring_reply(
	rno number(10) constraint pk_reply primary key,-- ´ñ±Û ±Û¹øÈ£
	bno number(10) not null,-- ¿øº» ±Û ¹øÈ£
	reply varchar2(1000) not null, -- ´ñ±Û ³»¿ë
	replyer varchar2(50) not null, -- ´ñ±Û ÀÛ¼ºÀÚ
	replydate date default sysdate, -- ´ñ±Û ÀÛ¼ºÀÏ
	updatedate date default sysdate,-- ´ñ±Û ¼öÁ¤ÀÏ
	constraint fk_spring_reply foreign key(bno) references spring_board(bno) -- ¿Ü·¡Å°
);


create sequence seq_reply;