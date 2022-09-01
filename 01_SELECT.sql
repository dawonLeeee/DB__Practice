-- EMPLOYEE에서 모든 컬럼 조회
SELECT EMP_NAME FROM EMPLOYEE;

/*
 * SELECT (DQL 또는 DML) : 조회
 * 
 * - 데이터를 조회(SELECT)하면 조건에 맞는 행들이 조회됨
 * 		이 때, 조회된 행들의 집합을 "RESULT SET(조회결과 집합)"이라고 한다.
 * 
 * - RESULT SET은 0개 이상의 행을 포함할 수 있다.
 * 
 * */

-- [작성법]
-- SELECT 컬럼명 FROM 테이블명;
-- —>어떤 테이블의 특정 컬럼을 조회하겠다

SELECT * FROM EMPLOYEE;
-- '*' : ALL, 모든, 전부

SELECT EMP_ID, EMP_NAME, PHONE FROM EMPLOYEE;

------------------------------------------------------------

-- <컬럼 값 산술연산> 
-- 컬럼 값 : 테이블 내 한 칸( == 한 셀)에 작성된 값(DATA)

-- EMPLOYEE 테이블에서 모든 사원의 사번, 이름, 급여, 급여 * 12(연봉) 조회
SELECT EMP_ID, EMP_NAME, SALARY, SALARY*12 FROM EMPLOYEE;

-- ORA-01722: 수치가 부적합합니다
-- 산술 연산은 숫자(NUMBER 타입)만 가능
SELECT EMP_NAME + 10 FROM EMPLOYEE;

--------------------------------------------------------

-- 날짜(DATE) 타입 조회

-- EMPLOYE 테이블에서 이름, 입사일, 오늘 날짜조회
-- SYSDATE : 시스템상의 현재 시간(날짜) 나타내는 상수
SELECT EMP_NAME, HIRE_DATE, SYSDATE  FROM EMPLOYEE;
-- 1990-02-06 00:00:00.000 -->DATE타입의 기본 저장단위

-- 현재 시간만 조회하기
-- DUAL(DUmmy tAbLe) 테이블 : 가짜 테이블(임시 조회용 테이블)
SELECT SYSDATE FROM DUAL;

-- 날짜 + 산술연산(+, -)
--> 날짜에 +/- 연산시 일 단위로 계산이 진행된다
SELECT SYSDATE -1, SYSDATE, SYSDATE + 1 FROM DUAL;

-- <컬럼 별칭 지정>
-- SELECT 조회 결과의 집합인 RESULT SET에 출력되는 컬럼명을 지정
/*
 * 컬럼명 AS 별칭 : 별칭 띄어쓰기X, 특수문자X, 문자만0
 * 
 * 컬럼명 AS "별칭" : 별칭 띄어쓰기O, 특수문자O, 문자0
 * 
 * AS는 생략 가능
 */

SELECT SYSDATE-1 "하루 전", SYSDATE+1 AS 내일시간
FROM DUAL;

----------------------------------------------
-- 리터럴 : 값 자체를 의미
-- DB리터럴 : 임의로 지정한 값을 기존 테이블에 존재하는 값처럼 사용하는 것
--> (필수) DB의 리터럴 표기법은 '' 홑따옴표
-->"" 쌍따옴표는 특수문자, 기호, 대소문자, 기호 등을 구분여 나타낼 때 사용하는 표기법
-->쌍따옴표 안에 작성되는 것들이 하나임을 의미

SELECT EMP_NAME, SALARY, '원' FROM EMPLOYEE;
-------------------------------------------------
-- DISTINCT : 조회 시 컬럼에 중복된 값을 한 번만 표시
-- 주의사항 : 
-- 		1. DISTINCT구문은 SELECT마다 딱 1번씩만 작성 가능
--		2. DISTINCT구문은 SELECT 제일 앞에 작성되어야 한다

SELECT DISTINCT DEPT_CODE, DISTINCT  JOB_CODE  FROM EMPLOYEE;

------------------------------------------------------------------

-- SELECT절 : SELECT 컬럼명 
-- FROM절 : FROM 테이블명
-- WHERE절(조건절) : WHERE 컬럼명 연산자 값

-- EMPLOYEE 테이블에서 급여가 300만원 초과인 사원의 사번, 이름, 급여, 부서코드 조회
SELECT EMP_ID, EMP_NAME, SALARY, DEPT_CODE
FROM EMPLOYEE
WHERE SALARY > 3000000;

-- EMPLOYEE테이블에서 부서코드가 'D9'인 사원의 사번, 이름, 부서코드, 직급코드 조회
SELECT EMP_ID, EMP_NAME, DEPT_CODE, JOB_CODE
FROM EMPLOYEE
WHERE DEPT_CODE = 'D9';
-- 비교연산자 : (=) , 대입연산자 : (:=)

-- 비교 연산자 : >, <, >=, <=, =(같다), != || <>(같지 않다)

-----------------------------------------------------
-- 논리 연산자 : (AND, OR)
-- EMPLOYEE 테이블에서 급여가 300만 이하 또는 500만 이상인 사원의 
-- 사번, 이름, 급여, 전화번호를 조회
SELECT EMP_ID, EMP_NAME, SALARY, PHONE
FROM EMPLOYEE
WHERE SALARY <= 3000000 OR SALARY >= 5000000;

-- EMPLOYEE 테이블에서 급여가 300만 이상 ~500만 미만인 사원의 
-- 사번, 이름, 급여, 전화번호를 조회
SELECT EMP_ID, EMP_NAME, SALARY, PHONE
FROM EMPLOYEE
WHERE SALARY >= 3000000 AND SALARY <= 5000000;

-----------------------------------------------------

-- BETWEEN A AND B : A이상 B이하
-- NOT BETWEEN A AND B : A이상 B이하가 아닌 경우(A미만 또는 B초과)

-- EMPLOYEE 테이블에서 급여가 300만 이상 ~500만 미만인 사원의 
-- 사번, 이름, 급여, 전화번호를 조회
SELECT EMP_ID, EMP_NAME, SALARY, PHONE
FROM EMPLOYEE
WHERE SALARY NOT BETWEEN 3000000 AND 6000000;

-- 날짜(DATE)에 BETWEEN 이용하기
-- EMPLOYEE 테이블에서 입사일이 1990-01-01일부터 1999-12-31일 사이인 직원의
-- 이름, 입사일 조회
SELECT EMP_NAME, HIRE_DATE
FROM EMPLOYEE
WHERE HIRE_DATE BETWEEN '1990-01-01' AND '1999-12-31';
-- 오라클은 데이터 타입이 달라도 형태가 일치하면 자동으로 타입을 변환시킴
-- EX) 1 == '1'
SELECT '같음' 
FROM DUAL
WHERE 1 = 0;

-----------------------------------------------------
-- LIKE
 -- 비교하려는 값이 특정한 패턴을 만족시키면 조회하는 연산자

-- [작성법]
-- WHERE 컬럼명 LIKE '패턴이 적용된 값'
-- LIKE의 패턴을 나타내는 문자(와일드 카드)
-- > '%' : 포함(’A%’ = A로 시작하는 문자열, ‘%A%’ = A가 포함된 문자열)
-- > '_' : 글자수(’A_’ = A로 시작하는 두 글자 문자열,  ‘___A’ = A로 끝나는 네 글자 문자열)

-- EMPLOYEE 테이블에서 성이 '전'씨인 사원의 사번, 이름 조회
SELECT EMP_ID, EMP_NAME
FROM EMPLOYEE
WHERE EMP_NAME LIKE '전%';

-- EMPLOYEE 테이블에서 전화번호가 010으로 시작하는 사원의 사번, 이름, 전화번호 조회
SELECT EMP_ID, EMP_NAME, PHONE
FROM EMPLOYEE
WHERE PHONE LIKE '010%';

SELECT EMP_NAME, EMAIL
FROM EMPLOYEE;

-- EMAIL에서 _ 앞에 글자가 세 글자인 사원 조회
SELECT EMP_NAME, EMAIL
FROM EMPLOYEE
WHERE '---_%'; -- 4글자 이상
-- 


-- LIKE의 ESCAPE OPTION을 이용하여 구분한다
-- LIKE의 ESCAPE OPTION : 일반 문자로 처리할 '_' / '%' 앞에
-- 아무 특수기호를 첨부해서 구분하게 함

SELECT EMP_NAME, EMAIL
FROM EMPLOYEE
WHERE '___#_%' ESCAPE '#'; -- 4글자 이상
-- '#' 뒤에 작성된 _는 일반 문자로 탈출


-----------------------------------------------------
-- 연습문제
-- EMPLOYEE 테이블에서 이메일 '_'앞이 4글자 이면서
-- 부서코드가 'D9' 또는 'D6'이고
-- 입사일이 1990-01-01~2000-12-31일 이고
-- 급여가 270만 이상인 사원의
-- 사번, 이름, 이메일, 부서코드, 입사일, 급여 조회
SELECT EMP_ID, EMP_NAME, DEPT_CODE, HIRE_DATE, SALARY
FROM EMPLOYEE
WHERE (EMAIL LIKE '____#_%' ESCAPE '#') AND
(DEPT_CODE = 'D9' OR DEPT_CODE = 'D6' ) AND
 (HIRE_DATE BETWEEN '1990-01-01' AND '2000-12-31') AND
 (SALARY >= 2700000);
 
-----------------------------------------------------

-- IN 연산자
/*
 * - 비교하려는 값과 목록에 작성된 값 중 일치하는 것이 있으면
 * 		조회하는 연산자
 * 
 * [작성법]
 * WHERE 컬럼명 IN(값1, 값2, 값3 ...)
 * 
 * WHERE 컬럼명 = 값1
 * OR	 컬럼명 = 값2
 * OR	 컬럼명 = 값3
 * >> 과 동일하다
 */

-- EMPLOYEE테이브에서 부서코드가 D1, D6, D9인 사원의
-- 사번, 이름, 주소코드 조회
SELECT EMP_ID, EMP_NAME, DEPT_CODE
FROM EMPLOYEE
WHERE DEPT_CODE IN('D1', 'D6', 'D9');



-- EMPLOYEE테이브에서 부서코드가 D1, D6, D9이 아닌 사원의
-- 사번, 이름, 주소코드 조회
SELECT EMP_ID, EMP_NAME, DEPT_CODE
FROM EMPLOYEE
WHERE DEPT_CODE NOT IN('D1', 'D6', 'D9'); -- 12명
OR DEPT_CODE IS NULL;

-----------------------------------------------------
/*
 * JAVA에서 NULL : 참조하는 객체가 없음을 의미하는 값
 * DB에서 NULL : 컬럼에 값이 없음을 의미하는 값 
 * 
 * [NULL 처리 함수]
 * 1) IS NULL : NULL인 경우 조회하겠다
 * 2) IS NOT NULL : NULL이 아닌 경우
 * 
 * 
 */

-- EMPLOYEE 테이블에서 보너스가 있는 사원의 이름, 보너스 조회
SELECT EMP_NAME, BONUS
FROM EMPLOYEE
WHERE BONUS IS NOT NULL;

-- EMPLOYEE 테이블에서 보너스가 없는 사원의 이름, 보너스 조회
SELECT EMP_NAME, BONUS
FROM EMPLOYEE
WHERE BONUS IS NULL;

-----------------------------------------------------
/*
 * ORDER BY 절
 * 
 * - SELECT문의 조회 결과(RESULT SET)을 정렬할 때 사용하는 구문
 * - SELECT문 해석시 가장 마지막에 해석된다
 * 
 * 해석 순서 : 
 * 3. SELECT절
 * 1. FROM절
 * 2. WHERE절
 * 4. ORDER BY 컬럼명 | 별칭 | 컬럼 순서 [ASC | DESC] [NULLS FIRST | LAST]
 * (대괄호 : 생략가능)
 */

-- EMPLOYEE 테이블 급여 오름차순으로
-- 사번, 이름, 급여 조회
SELECT EMP_ID, EMP_NAME, SALARY
FROM EMPLOYEE
ORDER BY SALARY ASC;

-- 급여 200만 이상인 사원의
-- 사번, 이름, 급여 조회
-- 단, 급여 내림차순으로 조회
SELECT EMP_ID, EMP_NAME, SALARY
FROM EMPLOYEE
WHERE SALARY >= 2000000
ORDER BY SALARY DESC;

-- 입사일 순서대로 이름, 입사일 조회(별칭 사용)
SELECT EMP_NAME, HIRE_DATE 입사일
FROM EMPLOYEE
ORDER BY 입사일;

/*
 * 정렬 중첩 : 대분류 정렬 후 소분류 정렬
 * 
 * 
*/
-- 부서코드 오름차순 정렬 후 급여 내림차순 정렬
SELECT EMP_NAME, DEPT_CODE, SALARY
FROM EMPLOYEE
ORDER BY DEPT_CODE, SALARY DESC;