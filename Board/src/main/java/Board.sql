Create table id_sequence(
	sequence_name varchar(10) not null,
	next_value int not null,
	primary key (sequence_name)
);

insert into id_sequence values('article',0);

create table article(
	article_id int not null auto_increment,
	group_id int not null,
	sequence_no char(16) not null,
	posting_date datetime not null,
	read_count int not null,
	writer_name varchar(20) not null,
	password varchar(10),
	title varchar(10),
	content text,
	primary key (article_id),
	index (sequence_no)
);

select * from article
/*
article 테이블 
article_id		int			게시글번호(PK)
group_id		int			그룹번호
sequence_no		char(16) 	순서번호
posting_date	datetime	게시글 등록일
read_count		int			그룹번호
writer_name		varchar(20)	작성자 이름
password		varchar(10)	게시글 암호
title			varchar(10)	게시글 제목
content			text		게시글 내용

id_sequence 테이블

sequence_name	varchar(10) 시퀀스 이름(PK)
next_value		int			시퀀스 값


sequence_no의 경우
그룹번호 + 순서번호로 저장이 될것 이유는 sequence_no 칼럼 하나만 내림차순으로 정렬하면 됨
id_sequence테이블은 게시글의 그룹번호 값을 생성할때 사용된다. 그룹번호는 새글이 써질때마다 1씩 증가 되는 값이며
마지막에 사용된 값을 저장 될때 사용한다.

*/

delete From article;