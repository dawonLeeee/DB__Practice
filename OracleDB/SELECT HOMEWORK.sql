------------------------- SELECT(BASIC)----------------------------------------


-- 1. 춘 기술대학교의 학과 이름과 계열을 표시하시오.
-- 단, 출력 헤더는 "학과 명", "계열"로 표시하도록 한다
SELECT DEPARTMENT_NAME "학과 명", CATEGORY "계열"
FROM TB_DEPARTMENT;



-- 2. 학과의 학과 정원을 다음과 같은 형태로 화면에 출력한다
SELECT  DEPARTMENT_NAME || '의 정원은 ' || CAPACITY || '명 입니다.' "학과별 정원"
FROM TB_DEPARTMENT;


--3.
SELECT STUDENT_NAME, ABSENCE_YN, DEPARTMENT_NAME
FROM TB_STUDENT
JOIN TB_DEPARTMENT USING(DEPARTMENT_NO)
WHERE ABSENCE_YN = 'Y'
 AND DEPARTMENT_NAME  = '국어국문학과'
 AND SUBSTR(STUDENT_SSN, 8, 1) = '2';
 


-- 4.
SELECT STUDENT_NAME 
FROM TB_STUDENT
WHERE STUDENT_NO IN ('A513079', 'A513090', 'A513091', 'A513110', 'A513119');


--5.
SELECT DEPARTMENT_NAME, CATEGORY 
FROM TB_DEPARTMENT
WHERE CAPACITY BETWEEN 20 AND 30;


--6. 
SELECT PROFESSOR_NAME
FROM TB_PROFESSOR
WHERE DEPARTMENT_NO IS NULL;


--7. 
SELECT STUDENT_NAME 
FROM TB_STUDENT
WHERE DEPARTMENT_NO IS NULL;

--8.
SELECT CLASS_NO 
FROM TB_CLASS
WHERE PREATTENDING_CLASS_NO IS NOT NULL;


--9.
SELECT CATEGORY 
FROM TB_DEPARTMENT
GROUP BY CATEGORY;

--10.
SELECT STUDENT_NO , STUDENT_NAME , STUDENT_SSN 
FROM TB_STUDENT
WHERE ABSENCE_YN <> 'Y'
	AND STUDENT_NO LIKE '_21%'
	AND STUDENT_ADDRESS LIKE '%전주%';
	

------------------------- SELECT(FUNCTION)----------------------------------------

-- 1. 
SELECT STUDENT_NO 학번, STUDENT_NAME 이름, TO_CHAR(ENTRANCE_DATE, 'YYYY-MM-DD') 입학년도
FROM TB_STUDENT
WHERE DEPARTMENT_NO = '002'
ORDER BY 3;

--2. 
SELECT PROFESSOR_NAME , PROFESSOR_SSN 
FROM TB_PROFESSOR 
WHERE LENGTH(PROFESSOR_NAME) <> 3;
-- 다르게 나올수있는 이유 : 컴퓨터가 멍청해서 이게 이름인지 성인지 구분 못해서


--3. 
SELECT PROFESSOR_NAME 교수이름, CEIL(MONTHS_BETWEEN(SYSDATE , TO_DATE(19 || SUBSTR(PROFESSOR_SSN, 1, 6), 'YYYYMMDD'))/12) 나이 
FROM TB_PROFESSOR;
--WHERE SUBSTR(PROFESSOR_SSN,8,1) = '1'
--ORDER BY 나이;


--4. 
SELECT SUBSTR(PROFESSOR_NAME, 2, LENGTH(PROFESSOR_NAME)-1) 이름
FROM TB_PROFESSOR;



SELECT TO_DATE('2022-09-01') FROM DUAL;




--5. 
SELECT STUDENT_NO, STUDENT_NAME
-- EXTRACT(YEAR FROM ENTRANCE_DATE) - SUBSTR(STUDENT_SSN, 1, 2) - 1900 "입학시나이"
FROM TB_STUDENT
WHERE EXTRACT(YEAR FROM ENTRANCE_DATE) - SUBSTR(STUDENT_SSN, 1, 2) - 1900 >= 20;

--6. 
SELECT TO_CHAR( TO_DATE(20221225) , 'dy') FROM DUAL;

--7
SELECT TO_DATE('99/10/11', 'YY/MM/DD') FROM DUAL; -- 2099-10-11 00:00:00.000
SELECT TO_DATE('49/10/11','YY/MM/DD') FROM DUAL; -- 2049-10-11 00:00:00.000
SELECT TO_DATE('99/10/11','RR/MM/DD') FROM DUAL; -- 1999-10-11 00:00:00.000
SELECT TO_DATE('49/10/11','RR/MM/DD') FROM DUAL; -- 2049-10-11 00:00:00.000


--8.
SELECT STUDENT_NO , STUDENT_NAME
FROM TB_STUDENT
WHERE STUDENT_NO NOT LIKE 'A%';


--9
SELECT ROUND(AVG(TB_GRADE.POINT), 1) 평점
FROM TB_GRADE
JOIN TB_STUDENT USING(STUDENT_NO)
WHERE STUDENT_NO  = 'A517178';


--10
SELECT DEPARTMENT_NO 학과번호, COUNT(*) "학생수(명)"
FROM TB_STUDENT 
GROUP BY DEPARTMENT_NO
ORDER BY 1;


--11.
SELECT COUNT(*)
FROM TB_STUDENT
WHERE COACH_PROFESSOR_NO IS NULL;



--12.
SELECT SUBSTR(TERM_NO, 1, 4), ROUND(AVG(POINT),1) "년도 별 평점"
FROM TB_GRADE
WHERE STUDENT_NO = 'A112113'
GROUP BY SUBSTR(TERM_NO, 1, 4)
ORDER BY 1;


-- 13.
 SELECT DEPARTMENT_NO "학과코드명", 
--  SUM(DECODE(ABSENCE_YN, 'Y', 1, 0)) " 휴학생 수"
 	COUNT(DECODE(ABSENCE_YN, 'Y', 1, NULL)) "휴학생 수" -- NULL제외하고 갯수 셈
FROM TB_STUDENT
--WHERE ABSENCE_YN = 'Y'
GROUP BY DEPARTMENT_NO
ORDER BY 1;


--14. 
SELECT STUDENT_NAME "동일이름", COUNT(*) "동명인 수"
FROM TB_STUDENT
GROUP BY STUDENT_NAME
HAVING COUNT(*) >= 2; -- 그룹에 대한 조건->HAVING



-- 15. 
SELECT NVL(SUBSTR(TERM_NO, 1, 4), ' ') 년도, 
		NVL(SUBSTR(TERM_NO, 5, 2), ' ') 학기,  
		ROUND(AVG(POINT), 1) 평점
FROM TB_GRADE
WHERE STUDENT_NO = 'A112113'
GROUP BY ROLLUP(SUBSTR(TERM_NO, 1, 4), SUBSTR(TERM_NO, 5, 2))
ORDER BY SUBSTR(TERM_NO, 1, 4), SUBSTR(TERM_NO, 5, 2);
-- NVL(SUBSTR(TERM_NO, 1, 4), ' ')이면 NULL이 우선순위가 높아 위쪽으로 정렬된다



-------------------------------------------------------------------
--1.
SELECT STUDENT_NAME "학생 이름", STUDENT_ADDRESS "주소지"
FROM TB_STUDENT
ORDER BY STUDENT_NAME;


--2.
SELECT STUDENT_NAME, STUDENT_SSN
FROM TB_STUDENT
WHERE ABSENCE_YN = 'Y'
ORDER BY 2 DESC;


---3.
SELECT STUDENT_NAME, STUDENT_NO, STUDENT_ADDRESS
FROM TB_STUDENT 
WHERE (STUDENT_ADDRESS LIKE '강원도%' OR STUDENT_ADDRESS LIKE '경기도%')
AND (STUDENT_NO LIKE '9%')
ORDER BY 1;


--4.
SELECT PROFESSOR_NAME , PROFESSOR_SSN 
FROM TB_PROFESSOR 
JOIN TB_DEPARTMENT USING(DEPARTMENT_NO)
WHERE DEPARTMENT_NAME = '법학과'
ORDER BY PROFESSOR_SSN;


--5.
SELECT STUDENT_NO , TO_CHAR(POINT, 'FM9.00') 학점
FROM TB_GRADE
WHERE TERM_NO = '200402'
	AND CLASS_NO = 'C3118100'
ORDER BY 2 DESC, 1;


--6.
SELECT STUDENT_NO, STUDENT_NAME , DEPARTMENT_NAME
FROM TB_STUDENT 
NATURAL JOIN TB_DEPARTMENT
ORDER BY STUDENT_NAME;


--7.
SELECT CLASS_NAME, DEPARTMENT_NAME
FROM TB_CLASS 
NATURAL JOIN TB_DEPARTMENT td ;


--8.
SELECT CLASS_NAME, PROFESSOR_NAME
FROM TB_CLASS_PROFESSOR
JOIN TB_CLASS USING(CLASS_NO)
JOIN TB_PROFESSOR tp USING(PROFESSOR_NO);

--9.
SELECT CLASS_NAME, PROFESSOR_NAME
FROM TB_CLASS_PROFESSOR
JOIN TB_CLASS USING(CLASS_NO)
JOIN TB_PROFESSOR P USING(PROFESSOR_NO)
JOIN TB_DEPARTMENT D ON(P.DEPARTMENT_NO = D.DEPARTMENT_NO)
WHERE CATEGORY = '인문사회';

--10.
SELECT STUDENT_NO, STUDENT_NAME, ROUND(AVG(POINT), 1)
FROM TB_GRADE 
JOIN TB_STUDENT USING(STUDENT_NO)
JOIN TB_DEPARTMENT USING(DEPARTMENT_NO)
WHERE DEPARTMENT_NAME = '음악학과'
GROUP BY STUDENT_NO, STUDENT_NAME
ORDER BY STUDENT_NO ;


--11. 
SELECT DEPARTMENT_NAME, STUDENT_NAME , PROFESSOR_NAME
FROM TB_STUDENT ts 
JOIN TB_DEPARTMENT td USING(DEPARTMENT_NO)
JOIN TB_PROFESSOR P ON(COACH_PROFESSOR_NO = PROFESSOR_NO)
WHERE STUDENT_NO = 'A313047';


--12. 
SELECT STUDENT_NAME , TERM_NO "TERM_NAME"
FROM TB_STUDENT
JOIN TB_GRADE USING(STUDENT_NO)
JOIN TB_CLASS USING(CLASS_NO)
WHERE SUBSTR(TERM_NO, 1, 4) = '2007'
AND CLASS_NAME = '인간관계론'
ORDER BY 1;

--13. 
SELECT CLASS_NAME, DEPARTMENT_NAME
FROM TB_CLASS tc 
JOIN TB_DEPARTMENT td USING(DEPARTMENT_NO)
LEFT JOIN TB_CLASS_PROFESSOR tcp USING(CLASS_NO)
WHERE CATEGORY = '예체능'
AND PROFESSOR_NO IS NULL;

SELECT CLASS_NAME, DEPARTMENT_NAME
FROM TB_CLASS 
JOIN TB_DEPARTMENT USING(DEPARTMENT_NO)
WHERE CATEGORY ='예체능'
AND CLASS_NO NOT IN 
(SELECT CLASS_NO FROM TB_CLASS_PROFESSOR); 

--14. 
SELECT STUDENT_NAME AS"학생이름", NVL(PROFESSOR_NAME, '지도교수 미지정') AS "지도교수"
FROM TB_STUDENT ts 
JOIN TB_DEPARTMENT td USING(DEPARTMENT_NO)
LEFT JOIN TB_PROFESSOR ON(COACH_PROFESSOR_NO = PROFESSOR_NO)
WHERE DEPARTMENT_NAME = '서반아어학과';


--15. 
SELECT S.STUDENT_NO "학번", S.STUDENT_NAME "이름", DEPARTMENT_NO "학과 이름", AVG(POINT)
FROM TB_STUDENT S
JOIN TB_GRADE G ON(S.STUDENT_NO = G.STUDENT_NO)
WHERE ABSENCE_YN = 'N' 
HAVING AVG(POINT) >= 4
GROUP BY S.STUDENT_NO, S.STUDENT_NAME, DEPARTMENT_NO
ORDER BY 1;


--16. 
SELECT CLASS_NO, CLASS_NAME, AVG(POINT)
FROM TB_CLASS tc 
JOIN TB_DEPARTMENT td USING(DEPARTMENT_NO)
JOIN TB_GRADE tg USING(CLASS_NO)
GROUP BY CLASS_NO, CLASS_NAME
WHERE DEPARTMENT_NAME = '환경조경학과'
	AND CLASS_TYPE LIKE '전공%';


--17. 
SELECT STUDENT_NAME, STUDENT_ADDRESS
FROM TB_STUDENT ts 
WHERE DEPARTMENT_NO  = (SELECT DEPARTMENT_NO 
FROM TB_STUDENT ts 
WHERE STUDENT_NAME  = '최경희');

--18.
SELECT STUDENT_NO, STUDENT_NAME FROM
	(SELECT STUDENT_NO, STUDENT_NAME, AVG(POINT) 평점
	FROM TB_GRADE
	JOIN TB_STUDENT ts USING(STUDENT_NO)
	JOIN TB_DEPARTMENT  USING(DEPARTMENT_NO)
	WHERE DEPARTMENT_NAME = '국어국문학과'
	GROUP BY STUDENT_NO, STUDENT_NAME
	ORDER BY 평점 DESC)
WHERE ROWNUM = 1;

-- 서브쿼리 3번
SELECT STUDENT_NO, STUDENT_NAME
FROM(
   SELECT STUDENT_NO, STUDENT_NAME, AVG(POINT) 평점
   FROM TB_GRADE
   JOIN TB_STUDENT USING(STUDENT_NO)
   WHERE DEPARTMENT_NO = (SELECT DEPARTMENT_NO
                     FROM TB_DEPARTMENT
                     WHERE DEPARTMENT_NAME = '국어국문학과')
   GROUP BY STUDENT_NO, STUDENT_NAME
   ORDER BY 평점 DESC )
WHERE ROWNUM = 1;

--19. 
SELECT DEPARTMENT_NAME  "계열 학과명", ROUND(AVG(POINT), 1) 전공평점
FROM TB_DEPARTMENT td 
JOIN TB_CLASS tc USING(DEPARTMENT_NO)
JOIN TB_GRADE tg USING(CLASS_NO)
WHERE CATEGORY = 
	(SELECT CATEGORY
	FROM TB_DEPARTMENT td 
	WHERE DEPARTMENT_NAME = '환경조경학과')
AND CLASS_TYPE LIKE '전공%'
GROUP BY DEPARTMENT_NAME
ORDER BY 1;
