-- String id,pw,ssn,name,regdate;
CREATE SEQUENCE article_seq
START WITH 1000
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE SEQUENCE grade_seq
START WITH 1000
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE TABLE Member(
	id VARCHAR2(10),
	name VARCHAR2(10),
	password VARCHAR2(10),
	birth VARCHAR2(15),
	phone VARCHAR2(15),
	gender VARCHAR2(15),
	major VARCHAR2(15),
	subject VARCHAR2(15),
	email VARCHAR2(50),
	addr VARCHAR2(100),
	regdate DATE,
	PRIMARY KEY(id)
);

--articleSeq,hitcount;
--id,title,content,
--regdate;
 
 CREATE TABLE Board(
	article_seq NUMBER,
	id VARCHAR2(10),
	title VARCHAR2(50),
	content VARCHAR2(1000),
	hitcount NUMBER,
	regdate DATE,
	PRIMARY KEY(article_seq),
	FOREIGN KEY(id) REFERENCES Member(id) ON DELETE CASCADE
);

drop table Board;
drop SEQUENCE article_seq;
drop table Member;

drop SEQUENCE grade_seq;
drop table grade;
drop table subject;
drop table Major;
SELECT * FROM tab;

-- 추가

ALTER TABLE Member ADD   profile VARCHAR2(50);
CREATE TABLE Major(
	major_id VARCHAR2(10),
	title VARCHAR2(10),
	PRIMARY KEY(major_id)
);

CREATE TABLE Subject(
	subj_id VARCHAR2(10),
	title VARCHAR2(10),
	PRIMARY KEY(subj_id)
	
);
CREATE TABLE Grade(
	grade_seq NUMBER,
	score VARCHAR2(10),
	exam_date VARCHAR2(10),
	id VARCHAR2(10),
	sub_id VARCHAR2(10),
	PRIMARY KEY(grade_seq)

	
);
ALTER TABLE Member ADD   major_id VARCHAR2(10);
ALTER TABLE Member
ADD CONSTRAINT member_fk_major
FOREIGN KEY (major_id)
REFERENCES Major(major_id);
ALTER TABLE Subject ADD major_id VARCHAR2(10);
ALTER TABLE Subject
ADD CONSTRAINT subject_fk_major
FOREIGN KEY (major_id)
REFERENCES Major(major_id)
ON DELETE CASCADE;

ALTER TABLE Grade ADD id VARCHAR2(10);
ALTER TABLE Grade
ADD CONSTRAINT grade_fk_member
FOREIGN KEY (id)
REFERENCES member(id)
ON DELETE CASCADE;

ALTER TABLE Grade ADD subj_id VARCHAR2(10);
ALTER TABLE Grade
ADD CONSTRAINT grade_fk_subject
FOREIGN KEY (subj_id)
REFERENCES subject(subj_id)
ON DELETE CASCADE;

