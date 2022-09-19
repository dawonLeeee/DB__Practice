-- SYS 관리자 계정
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE; -- 계정명을 쉽게, 예전 방식으로 사용할 수 있도록 하는 명령어

-- 사용자 계정 생성(아이디, 비밀번호)
CREATE USER member_ldw IDENTIFIED BY member1234;

-- 생성한 사용자 계정에 권한 부여(접속권한, 자원, 뷰 생성)
GRANT CONNECT, RESOURCE, CREATE VIEW TO member_ldw;

-- 테이블 스페이스 할당
ALTER USER member_ldw DEFAULT
TABLESPACE SYSTEM QUOTA UNLIMITED ON SYSTEM;


---------------------------------------------------------------
-- member_ldw 계정

-- 회원 테이블
-- 회원 번호, 아이디, 비밀번호, 이름, 성별, 가입일, 탈퇴여부
CREATE TABLE "MEMBER" (
	MEMBER_NO NUMBER PRIMARY KEY,
	MEMBER_ID VARCHAR2(30) NOT NULL,
	MEMBER_PW VARCHAR2(30) NOT NULL,
	MEMBER_NM VARCHAR2(30) NOT NULL,
	MEMBER_GENDER CHAR(1) CHECK(MEMBER_GENDER IN ('M', 'F')),
	ENROLL_DATE DATE DEFAULT SYSDATE,
	SECESSION_FL CHAR(1) DEFAULT 'N' CHECK(SECESSION_FL IN ('Y', 'N'))
);

COMMENT ON COLUMN "MEMBER".MEMBER_NO IS '회원번호';
COMMENT ON COLUMN "MEMBER".MEMBER_ID IS '회원 아이디';
COMMENT ON COLUMN "MEMBER".MEMBER_PW IS '회원 비밀번호';
COMMENT ON COLUMN "MEMBER".MEMBER_NM IS '회원 이름';
COMMENT ON COLUMN "MEMBER".MEMBER_GENDER IS '회원 성별(M/F)';
COMMENT ON COLUMN "MEMBER".ENROLL_DATE IS '회원 가입일';
COMMENT ON COLUMN "MEMBER".SECESSION_FL IS '탈퇴여부(Y/N)';

-- 회원 번호 시퀀스
CREATE SEQUENCE SEQ_MEMBER_NUMBER
START WITH 1
INCREMENT BY 1
NOCYCLE -- 붙여써야한다!
NOCACHE;


CREATE TABLE 테이블명 (


);

CREATE TABLE 테이블명 (


);