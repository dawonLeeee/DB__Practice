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
CREATE SEQUENCE SEQ_MEMBER_NO
START WITH 1
INCREMENT BY 1
NOCYCLE -- 붙여써야한다!
NOCACHE;



-- 회원가입 INSERT 
INSERT INTO "MEMBER" VALUES(
	SEQ_MEMBER_NO.NEXTVAL, 	'user01', 'pass01',	'유저일',	'M', DEFAULT, DEFAULT);

INSERT INTO "MEMBER" VALUES(
	SEQ_MEMBER_NO.NEXTVAL, 	'user02', 'pass02',	'유저이',	'F', DEFAULT, DEFAULT);

INSERT INTO "MEMBER" VALUES(
	SEQ_MEMBER_NO.NEXTVAL, 	'user03', 'pass03',	'유저삼',	'F', DEFAULT, DEFAULT);

SELECT * FROM MEMBER;
COMMIT;

-- 아이디 중복확인
-- (중복되는 아이디가 입력되어도 탈퇴한 계정이면 중복이 아니라고 판별)
SELECT COUNT(*) -- 조회되는 행의 갯수 셈(중복->1, 아니면->0)
FROM "MEMBER"
WHERE MEMBER_ID = 'user01'
AND SECESSION_FL = 'N'; 
-- 아이디가 user1이면서 탈퇴하지 않은 회원(활동계정) 조회(->아이디 쓰는사람 있다!)


-- login(아이디, 비밀번호가 일치하고 탈퇴를 안 한 회원)
SELECT MEMBER_NO, MEMBER_ID, MEMBER_NM, MEMBER_GENDER, 
	TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일" HH24:MI:SS') ENROLL_DATE
FROM "MEMBER"
WHERE MEMBER_ID = 'user01'
AND MEMBER_PW = 'pass01'
AND SECESSION_FL = 'N';

-- secession
UPDATE "MEMBER" 
SET SECESSION_FL = 'Y'
WHERE MEMBER_ID = ?;

-- 회원 목록 조회(아이디, 이름, 성별)
-- 탈퇴 회원 미포함
-- 가입일 내림차순
SELECT MEMBER_ID, MEMBER_NM, MEMBER_GENDER
FROM "MEMBER"
WHERE SECESSION_FL = 'N'
ORDER BY MEMBER_ID DESC; 
-- ENROLL_DATE(날짜) 비교보다 MEMBER_NO(단순 숫자) 비교를 하는게 더 빠름


-- 내 정보 수정
UPDATE "MEMBER" SET 
MEMBER_NM = '수정된이름', MEMBER_GENDER = 'F'
WHERE MEMBER_NO = 3;

SELECT * FROM "MEMBER";

ROLLBACK;


-- 비밀번호 변경
UPDATE "MEMBER" SET
MEMBER_PW = '새비밀번호'
WHERE MEMBER_ID = '1'
AND MEMBER_PW = '현재비밀번호'


--회원 탈퇴
UPDATE "MEMBER" SET
SECESSION_FL = 'Y'
WHERE MEMBER_NO = ?


UPDATE "MEMBER" SET
SECESSION_FL = 'N'
WHERE MEMBER_ID = 'user02';

COMMIT;

-----------------------------------------------------------
-- BOARD 테이블
CREATE TABLE "BOARD" (
	BOARD_NO NUMBER CONSTRAINT BOARD_PK PRIMARY KEY,
	BOARD_TITLE VARCHAR2(500) NOT NULL,
	BOARD_CONTENT VARCHAR2(4000) NOT NULL,
	CRATE_DT DATE DEFAULT SYSDATE,
	READ_COUNT NUMBER DEFAULT 0,
	DELETE_FL CHAR(1) DEFAULT 'N' CHECK(DELETE_FL IN('Y', 'N')),
	MEMBER_NO NUMBER CONSTRAINT BOARD_WRITER_FK REFERENCES "MEMBER"
);



COMMENT ON COLUMN "BOARD".BOARD_NO IS '게시글 번호';
COMMENT ON COLUMN "BOARD".BOARD_TITLE IS '게시글 제목';
COMMENT ON COLUMN "BOARD".BOARD_CONTENT IS '게시글 내용';
COMMENT ON COLUMN "BOARD".CRATE_DT IS '게시글 작성일';
COMMENT ON COLUMN "BOARD".READ_COUNT IS '게시글 조회수';
COMMENT ON COLUMN "BOARD".DELETE_FL IS '게시글 삭제여부';
COMMENT ON COLUMN "BOARD".MEMBER_NO IS '작성자 회원번호';

-- 게시판 번호 시퀀스
CREATE SEQUENCE SEQ_BOARD_NO NOCACHE;

--게시판 샘플데이터

SELECT * FROM "MEMBER"
WHERE SECESSION_FL = 'N';

INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, '샘플 제목1', '샘플 내용1입니다',
	TO_DATE('2022-09-10 10:30:12', 'YYYY-MM-DD HH24:MI:SS'), DEFAULT, DEFAULT, 1);

INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, '샘플 제목2', '샘플 내용1입니다',
	TO_DATE('2022-09-21 10:30:12', 'YYYY-MM-DD HH24:MI:SS'), DEFAULT, DEFAULT, 1);

INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, '샘플 제목3', '샘플 내용1입니다',
	DEFAULT, DEFAULT, DEFAULT, 1);

INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, '샘플 제목4', '샘플 내용1입니다',
	DEFAULT, DEFAULT, DEFAULT, 1);

INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, '오늘도 산책', 
'오늘도 즐거운 산책 감사합니다~❤️ 철수가 산책하다가 종종 거위소리를 냅니다~ 살이쪄서 기관지가 좁아져서 그렇다고하네요..ㅠㅠ 짧게 할때도 있고 길게 할때도 있어요..ㅠㅠ 사진이 굉장히 늠름해 보이네요 ㅎㅎㅎ 감사합니다🥰
', DEFAULT, DEFAULT, DEFAULT, 1);



SELECT * FROM BOARD;
COMMIT;
ROLLBACK;
---------------------------------------------------------
--댓글 테이블
CREATE TABLE "COMMENT"(
	COMMENT_NO NUMBER,
	COMMENT_CONTENT VARCHAR2(900) NOT NULL,
	CREATE_DT DATE DEFAULT SYSDATE,
	DELETE_FL CHAR(1) DEFAULT 'N' CHECK(DELETE_FL IN('Y','N')),
	MEMBER_NO NUMBER REFERENCES "MEMBER",
	BOARD_NO NUMBER REFERENCES "BOARD",
	CONSTRAINT COMMENT_PK PRIMARY KEY(COMMENT_NO)
);

COMMENT ON COLUMN "COMMENT".COMMENT_NO IS '댓글 번호';
COMMENT ON COLUMN "COMMENT".COMMENT_CONTENT IS '댓글 내용';
COMMENT ON COLUMN "COMMENT".CREATE_DT IS '댓글 작성일';
COMMENT ON COLUMN "COMMENT".DELETE_FL IS '댓글 삭제 여부';
COMMENT ON COLUMN "COMMENT".MEMBER_NO IS '댓글 작성자 회원 번호';
COMMENT ON COLUMN "COMMENT".BOARD_NO IS '댓글이 작성된 게시글 번호';


--댓글 테이블 시퀀스
CREATE SEQUENCE SEQ_COMMENT_NO NOCACHE;

SELECT * FROM BOARD WHERE DELETE_FL = 'N';

--댓글 테이블 샘플데이터 삽입
INSERT INTO "COMMENT"
VALUES(SEQ_COMMENT_NO.NEXTVAL, '댓글 샘플1번', DEFAULT, DEFAULT, 1, 1);

INSERT INTO "COMMENT"
VALUES(SEQ_COMMENT_NO.NEXTVAL, '댓글 샘플2번', DEFAULT, DEFAULT, 1, 1);

INSERT INTO "COMMENT"
VALUES(SEQ_COMMENT_NO.NEXTVAL, '댓글 샘플2번', DEFAULT, DEFAULT, 1, 1);

SELECT * FROM "COMMENT";



SELECT * FROM BOARD;


-- 게시글 목록 상세 조회(댓글 수 포함)
SELECT BOARD_NO, BOARD_TITLE, BOARD_CONTENT, MEMBER_NM, READ_COUNT, 
	CASE 
		WHEN SYSDATE - CREATE_DT < 1/24/60 --1분 미만
		THEN FLOOR((SYSDATE - CREATE_DT)*24*60*60) || '초 전'
		WHEN SYSDATE - CREATE_DT < 1/24 -- 1시간 미만
		THEN FLOOR((SYSDATE - CREATE_DT)*24*60) || '분 전'
		WHEN SYSDATE - CREATE_DT < 1/24 -- 하루 미만
		THEN FLOOR((SYSDATE - CREATE_DT)*24) || '시간 전'
		ELSE TO_CHAR(CREATE_DT, 'YYYY-MM-DD')
	END CREATE_DT,
	(SELECT COUNT(*) FROM "COMMENT" C WHERE C.BOARD_NO = B.BOARD_NO) COMMENT_COUNT
FROM "BOARD" B
JOIN "MEMBER" USING(MEMBER_NO)
WHERE DELETE_FL = 'N'
ORDER BY BOARD_NO DESC;

SELECT FLOOR((SYSDATE - TO_DATE('2022-09-22'))*24*60*60)
FROM DUAL;


-- 게시글 상세 조회(게시글 내용 조회)
SELECT BOARD_NO, BOARD_TITLE, BOARD_CONTENT, MEMBER_NO, MEMBER_NM, READ_COUNT, 
	TO_CHAR(CREATE_DT, 'YYYY-MM-DD HH24:MI:SS') CREATE_DT
FROM "BOARD"
JOIN "MEMBER" USING(MEMBER_NO)
WHERE DELETE_FL = 'N'
AND BOARD_NO = 3;

-- selectCommentList
-- 특정 게시글의 댓글 목록 조회(작성일순 오름차순)
SELECT COMMENT_NO, COMMENT_CONTENT, 
	MEMBER_NO, MEMBER_NM, TO_CHAR(CREATE_DT, 'YYYY-MM-DD HH24:MI:SS') CREATE_DT
FROM "COMMENT"
JOIN "MEMBER" USING(MEMBER_NO)
WHERE DELETE_FL = 'N'
AND BOARD_NO = 1
ORDER BY COMMENT_NO;

-----------------------------------------------
-- COUNT UPDATE코드

-- 상세 조회된 게시글의 조회수 증가
UPDATE "BOARD" SET
READ_COUNT = READ_COUNT+1
WHERE BOARD_NO = 3;

COMMIT;


SELECT * FROM "BOARD";
SELECT * FROM "COMMENT";
-----------------------------------------------
-- SQL문 작성


-- 게시글 목록 조회(selectAllBoard)
SELECT BOARD_NO, BOARD_TITLE, BOARD_CONTENT, TO_CHAR(CREATE_DT, 'YYYY-MM-DD HH24:MI:SS') CREATE_DT, 
	READ_COUNT, MEMBER_NM
FROM "BOARD" 
JOIN "MEMBER" USING(MEMBER_NO)
WHERE DELETE_FL = 'N';



-- 댓글 삽입(insertComment)
INSERT INTO "COMMENT"
VALUES(SEQ_COMMENT_NO.NEXTVAL, ?, DEFAULT, DEFAULT, ?, ?)

SELECT * FROM "COMMENT";
