--***************
--2017.08.02
--[1]MAJOR_TAB
--[2]SUBJECT_TAB
--[3]MEMBER_TAB
--[4]PROF_TAB
--[5]STUDENT_TAB
--[6]GRADE_TAB
--[7]BOARD_TAB
--****************

--************
--시퀀스
--DDL
CREATE SEQUENCE seq
START WITH 2000
INCREMENT BY 1
NOCACHE
NOCYCLE;
--drop SEQUENCE seq;

--*****************
-- Major TABLE
-- 2017.08.02
-- 회원관리 테이블
-- (member_id,name,password,ssn,regdate,
--  major_id,phone,email,profile)
--******************

-- DDL
CREATE TABLE Major(
major_id VARCHAR2(10),
title VARCHAR2(10),
PRIMARY KEY(major_id)
);
--drop table Major;

--DML
INSERT INTO Major(major_id,title) VALUES('','');
UPDATE Major SET %s=''WHERE %s='';
SELECT * FROM Major;
DELETE FROM Major WHERE %s='';

--*****************
-- Subject TABLE
-- 2017.08.02
-- 회원관리 테이블
-- (member_id,name,password,ssn,regdate,
--  major_id,phone,email,profile)
--******************

-- DDL
CREATE TABLE Subject(
subj_id VARCHAR2(10),
title VARCHAR2(10),
major_id VARCHAR2(10),
PRIMARY KEY(subj_id),
FOREIGN KEY(major_id) REFERENCES Major(major_id) ON DELETE CASCADE
);

--DML
INSERT INTO Subject(subj_id,title,major_id) VALUES('','','');
SELECT * FROM Subject;
UPDATE Subject SET %s=''WHERE %s='';
DELETE FROM Subject WHERE %s='';

--*****************
-- MEMBER TABLE
-- 2017.08.02
-- 회원관리 테이블
-- (member_id,name,password,ssn,regdate,
--  major_id,phone,email,profile)
--******************

--DDL
CREATE TABLE Member(
	member_id VARCHAR2(10),
	name VARCHAR2(10),
	password VARCHAR2(10),
	ssn VARCHAR2(15),
	regdate DATE,
	major_id VARCHAR2(10),
	phone VARCHAR2(20),
	email VARCHAR2(20),
	profile VARCHAR2(20),
	PRIMARY KEY(member_id),
	FOREIGN KEY(major_id) REFERENCES Major(major_id) ON DELETE CASCADE
);
--ALTER TABLE Member ADD major_id VARCHAR2(10); --컬럼생성
--DROP TABLE Member;
--ALTER TABLE Member
--ADD CONSTRAINT member_fk_major
--FOREIGN KEY (major_id)
--REFERENCES Major (major_id) 
--ON DELETE CASCADE; --폴인키 설정
--ALTER TABLE Member
--DROP COLUMN subject; --컬럼 삭제

--DML
INSERT INTO MEMBER(member_id,name,password,ssn,regdate,major_id,phone,email,profile)
VALUES('mankiew','멘큐','1','701201-123456',SYSDATE,'economics','010-1234-1234','mankiew@naver.com','mankiew.jpg');
SELECT * from MEMBER;
SELECT COUNT(*) AS COUNT FROM MEMBER;
SELECT * FROM Member WHERE id='dsd';
UPDATE Member SET password='1'WHERE id='dsd';
DELETE FROM Member WHERE id='dsd';

--*****************
-- Prof TABLE
-- 2017.08.02
-- 회원관리 테이블
-- (member_id,name,password,ssn,regdate,
--  major_id,phone,email,profile)
--******************

--DDL
CREATE TABLE Prof(
member_id VARCHAR2(10),
salary VARCHAR2(10),
PRIMARY KEY(member_id),
FOREIGN KEY(member_id) REFERENCES Member (member_id)--식별관계이기 때문에 멤버아이디가 프라이머리키이면서 폴인키이다.
ON DELETE CASCADE
);

--DML
INSERT INTO Prof(member_id,salary) VALUES('kim','3000');
SELECT * FROM Prof;
UPDATE Prof SET %s='' WHERE %s='';
DELETE FROM Prof WHERE %s='';


--*****************
-- Student TABLE
-- 2017.08.02
-- 회원관리 테이블
-- (member_id,name,password,ssn,regdate,
--  major_id,phone,email,profile)
--******************

--DDL
CREATE TABLE Student(
member_id VARCHAR2(10),
stu_no VARCHAR2(8),
PRIMARY KEY(member_id),
FOREIGN KEY(member_id) REFERENCES Member (member_id)
ON DELETE CASCADE
);

--DML
INSERT INTO Student(member_id,stu_no) VALUES('','');
SELECT * FROM Student;
UPDATE Student SET %s=''WHERE %s='';
DELETE FROM Student WHERE %s='';


--*****************
-- Grade TABLE
-- 2017.08.02
-- 회원관리 테이블
-- (member_id,name,password,ssn,regdate,
--  major_id,phone,email,profile)
--******************

--DDL
CREATE TABLE Grade(
	grade_seq NUMBER,
	score VARCHAR2(3),
	exam_date VARCHAR2(12),
	subj_id VARCHAR2(10),
	member_id VARCHAR2(10),
	PRIMARY KEY(grade_seq),
	FOREIGN KEY(member_id) REFERENCES Member(member_id) ON DELETE CASCADE,
	FOREIGN KEY(subj_id) REFERENCES Subject(subj_id) ON DELETE CASCADE
);
ALTER TABLE Grade
RENAME COLUMN id TO member_id; --테이블명 변경
DROP TABLE Grade;

--DML
INSERT INTO Grade(grade_seq,score,exam_date,subj_id,member_id) VALUES(seq.nextval,'90','2017-03','자바','mem1');
SELECT * FROM Grade;
UPDATE Grade SET %s=''WHERE %s='';
DELETE FROM Grade WHERE %s='';

--*****************
-- Board TABLE
-- 2017.08.02
-- 회원관리 테이블
-- (member_id,name,password,ssn,regdate,
--  major_id,phone,email,profile)
--******************

--DDL
 CREATE TABLE Board(
	article_seq NUMBER,
	member_id VARCHAR2(10),
	title VARCHAR2(50),
	content VARCHAR2(1000),
	hitcount NUMBER,
	regdate DATE,
	PRIMARY KEY(article_seq),
	FOREIGN KEY(member_id) REFERENCES Member(member_id) ON DELETE CASCADE
);
--drop table board;

--DML
INSERT INTO Board(article_seq,member_id,title,content,hitcount,regdate) VALUES(seq.nextval,'kia','날씨가 좋다',
'무한한 가슴이 기쁘며, 힘있다..',57,SYSDATE);
SELECT * FROM BOARD;
UPDATE Board SET %s=''WHERE %s='';
DELETE FROM Board WHERE %s='';

--***********
SELECT * FROM tab; --전체 테이블 표시

SELECT * FROM USER_SEQUENCES;

SELECT id FROM Board WHERE title LIKE '%o%'; --일부문자로 찾기

SELECT * FROM Member m,Board b WHERE m.id=b.id; --멤버아이디와 보드 아이디가 일치하는 테이블 표시

SELECT COUNT(*) FROM Student s,

<!-- ALTER TABLE Member ADD phone varchar2(13) 이미 정해져 있는 테이블 변수를 추가하는 구문 -->


--************
--조인
--************
SELECT * 
FROM (SELECT DISTINCT
m.member_id, m.name, mj.title AS Major,
g.score, sj.title AS Subject, g.exam_date
FROM Member m, Student s, Grade g, Subject sj, Major mj 
WHERE 
m.member_id=s.member_id
AND m.member_id=g.member_id
AND sj.major_id=mj.major_id
AND sj.subj_id=g.subj_id)
WHERE member_id='mem1';

SELECT avg(score) --equi-join WHERE조건에다가  조인을 걸었다 (OLD한 스타일)
FROM (SELECT DISTINCT 
m.member_id, m.name,mj.title AS Major,
g.score, sj.title AS Subject, g.exam_date
FROM Member m, Student s, Grade g, Subject sj, Major mj
WHERE
m.member_id=s.member_id
AND m.member_id=g.member_id
AND sj.major_id=mj.major_id
AND sj.subj_id=g.subj_id)t
WHERE t.member_id='mem1'

SELECT ROWNUM NO,t2.*
FROM(SELECT t.id id, avg(score) avg --inner join(교집합 조건을 모두 만족해야 나온다) --리턴하는 값  
FROM (SELECT DISTINCT 
	m.member_id id, 
	g.score score
FROM Grade g
	INNER JOIN Subject s ON g.subj_id=s.subj_id
	INNER JOIN Member m ON m.member_id=g.member_id)t
group by t.id
having avg(score) >= 50
order by avg(score) desc)t2 --내림차순
WHERE ROWNUM <4
;


select * from STUDENT;

-- 문제 : 1페이지에 해당하는 5개
select * from student where  num > (select count(num)-10 from student);
-- rownum은 가상의 컬럼이기때문에 할당을 하기전에는 중간 번호는 부를수 없다.
select * from student where  rownum between 1 and 5;
-- 문제 : 2페이지에 해당하는 5개 추출

select rownum num,s.* from student s where  num between 6 and 10;
select rownum num,s.*
from(select * from student)s where num between 6 and 10;

select rownum num,s.*
from(select * from student)s where num between 1 and 5;

select * from student;


select t2.*
from (select rownum seq,t.*
	from (select *
	from STUDENT
	where name like '%이%'
	order by num desc) t) t2
	where t2.seq between 1 and 5;
	


select * from student
where name like '%%';

select * from student;










