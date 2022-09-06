 -- 한줄주석
 /*
  * 범위주석
  */

 
 -- SQL 하나 수행 : ctrl + entr
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;

-- 실습용 사용자계정 생성(계정만 만들었지 데이터 저장공간이나 권한 아직 할당X)
CREATE USER kh_ldw IDENTIFIED BY kh1234; 

-- 사용자 계정 권한부여
GRANT RESOURCE, CONNECT TO kh_ldw;

--객체 생성(테이블 등) 공간 할당량 지정
ALTER USER kh_ldw DEFAULT TABLESPACE SYSTEM
QUOTA UNLIMITED ON SYSTEM;
