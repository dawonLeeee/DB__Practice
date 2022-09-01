/* SELECT문 해석 순서
  5 : SELECT 컬럼명 AS 별칭, 계산식, 함수식
  1 : FROM 참조할 테이블명
  2 : WHERE 컬럼명 | 함수식 비교연산자 비교값
  3 : GROUP BY 그룹을 묶을 컬럼명
  4 : HAVING 그룹함수식 비교연산자 비교값
  6 : ORDER BY 컬럼명 | 별칭 | 컬럼순번 정렬방식 [NULLS FIRST | LAST];
*/

-----------------------------------------------------------------------------------------------------------------------------------------

-- * GROUP BY절 : 같은 값들이 여러개 기록된 컬럼을 가지고 같은 값들을
--              하나의 그룹으로 묶음
-- GROUP BY 컬럼명 | 함수식, ....
-- 여러개의 값을 묶어서 하나로 처리할 목적으로 사용함
-- 그룹으로 묶은 값에 대해서 SELECT절에서 그룹함수를 사용함

-- 그룹 함수는 단 한개의 결과 값만 산출하기 때문에 그룹이 여러 개일 경우 오류 발생
-- 여러 개의 결과 값을 산출하기 위해 그룹 함수가 적용된 그룹의 기준을 ORDER BY절에 기술하여 사용


-- EMPLOYEE테이블에서 부서코드, 부서별 급여 합 조회
SELECT DEPT_CODE, SUM(SALARY)
FROM EMPLOYEE
GROUP BY DEPT_CODE; -->DEPT_CODE가 같은 

SELECT DEPT_CODE, SUM(SALARY)
FROM EMPLOYEE;
-- ORA-00937: 단일 그룹의 그룹 함수가 아닙니다

-- EMPLOYEE 테이블에서 직급 코드가 같은 사람의 급여 평균, 인원수
SELECT ROUND(AVG(SALARY)), COUNT(EMP_ID)
FROM EMPLOYEE
GROUP BY JOB_CODE
ORDER BY JOB_CODE;

-- EMPLOYEE테이블에서
-- 성별과 각 성별 별 인원수, 급여 합을 
-- 인원수 오름차순으로 조히

SELECT 
DECODE(SUBSTR(EMP_NO, 8, 1), '1', '남', '2', '여') 성별, 
	COUNT(*) "인원 수",
	SUM(SALARY) "급여 합" 
FROM EMPLOYEE
GROUP BY DECODE(SUBSTR(EMP_NO, 8, 1), '1', '남', '2', '여') -- 별칭사용X(해석X)
ORDER BY "인원 수"; -- 별칭사용O


-----------------------------------------------------------------------------------------------------------------------------------------

-- * WHERE절 GROUP BY절을 혼합하여 사용
--> WHERE절은 각 컬럼 값에 대한 조건 (SELECT문 해석 순서를 잘 기억하는 것이 중요!!)

/*
5. SELECT : 어떤 행들을 보여줄건지
1. FROM : 어떤 테이블에서
2. WHERE : 어떤 조건이 일치하는 행들만
3. GROUP BY : 어떤 그룹마다
4. HAVING : 그룹들 사이에 조건을 걺
6. ORDER BY : 무엇을 기준으로 정렬?
*/

-- EMPLOYEE테이블에서 부서코드가 D5, D6인 부서의 평균 급여, 인원수 조회
SELECT DEPT_CODE, ROUND(AVG(SALARY)) "평균", COUNT(*) "인원수"
FROM EMPLOYEE
WHERE DEPT_CODE IN('D5', 'D6')
GROUP BY DEPT_CODE
ORDER BY DEPT_CODE;

-- EMPLOYEE 테이블에서 직급별 2000년도 이후 입사자들의 급여 합을 조회
SELECT JOB_CODE, SUM(SALARY) "급여 합"
FROM EMPLOYEE
WHERE EXTRACT(YEAR FROM HIRE_DATE) >= 2000
	-- HIRE_DATE >= TO_DATE('2000-01-01')
	-- TO_CHAR(HIRE_DATE, 'YYYY') >= '2000'
GROUP BY JOB_CODE
ORDER BY JOB_CODE;


-- EMPLOYEE 테이블에서 부서별로 같은 직급인 사원의 수를 조회
-- (부서코드 오름차순, 직급코드 내림차순)
SELECT DEPT_CODE, JOB_CODE, COUNT(*) "사원 수"
FROM EMPLOYEE
GROUP BY DEPT_CODE, JOB_CODE 
-- DEPT_CODE로 그룹을 나누고, 나눠진 그룹 내에서 JOB_CODE로 또 그룹 분류 -> 분류 세분화
ORDER BY 1 ASC, 2 DESC;

-----------------------------------------------------------------------------------------------------------------------------------------

-- * 여러 컬럼을 묶어서 그룹으로 지정 가능 --> 그룹내 그룹 이 가능하다!
-- *** GROUP BY 사용시 주의사항
--> SELECT문에 GROUP BY절을 사용할 경우
--  SELECT절에 명시한 조회할려면 컬럼 중
--  그룹함수가 적용되지 않은 컬럼을 
--  모두 GROUP BY절에 작성해야함.



--------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------


-- * HAVING 절 : 그룹함수로 구해 올 그룹에 대한 조건을 설정할 때 사용
-- HAVING 컬럼명 | 함수식 비교연산자 비교값

-- 부서별 평균 급여가 300만원 이상인 부서를 조회(부서코드 오름차순)
SELECT DEPT_CODE, ROUND(AVG(SALARY))
FROM EMPLOYEE
-- WHERE SALARY > 3000000 -- 한 사람의 급여가 300만원 이상이라는 조건(요구사항 충족X)
GROUP BY DEPT_CODE
HAVING AVG(SALARY) >= 3000000 --> DEPT_CODE 그룹 중 급여 평균이 300만원 이상인 그룹만 남음
ORDER BY DEPT_CODE;

-- EMPLOYEE 테이블에서 직급별 인원수가 5명 이하인 직급코드, 인원수 조회(직급코드 오름차순)
SELECT JOB_CODE, COUNT(*)
FROM EMPLOYEE
GROUP BY JOB_CODE 
HAVING COUNT(*) <= 5
ORDER BY 1;