// 테이블 생성문
CREATE TABLE MEMBER (
                        member_id varchar2(20) PRIMARY KEY,
                        password varchar2(20) NOT NULL,
                        name varchar2(50)
);

CREATE TABLE guetbook (
                          guestbook_id NUMBER PRIMARY KEY,
                          contents varchar2(4000) NOT NULL,
                          member_id REFERENCES MEMBER(member_id),
                          hit NUMBER DEFAULT 0,
                          created_time DATE DEFAULT sysdate,
                          lastModified_time DATE DEFAULT sysdate
);

CREATE SEQUENCE seq_guestbook;
