// 테이블 및 시퀀스 생성 쿼리
CREATE TABLE MEMBER (
	member_id varchar2(20) PRIMARY KEY,
	password varchar2(20) NOT NULL,
	name varchar2(50)
);

CREATE TABLE guestbook (
	guestbook_id NUMBER PRIMARY KEY,
	contents varchar2(4000) NOT NULL,
	member_id REFERENCES MEMBER(member_id),
	created_time DATE DEFAULT sysdate
);


CREATE SEQUENCE seq_guestbook;
