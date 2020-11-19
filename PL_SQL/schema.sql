DROP TABLE paper;
DROP TABLE sits;
DROP TABLE sets;
DROP TABLE exam_sche;
DROP TABLE head_of;
DROP TABLE teaches;
DROP TABLE teacher;
DROP TABLE subject;
DROP TABLE member_of;
DROP TABLE class;
DROP TABLE student;
DROP TYPE question_mc_table;
DROP TYPE question_fb_table;
DROP TYPE question_fl_table;
DROP TYPE question_mc_t;
DROP TYPE question_fb_t;
DROP TYPE question_fl_t;
DROP TYPE answer_sheet_t;

CREATE TABLE student
(s_id CHAR(9) CONSTRAINT pk_student PRIMARY KEY
,s_pw VARCHAR(100)
,s_flag NUMBER
,s_name VARCHAR(30));

CREATE TABLE class
(c_id CHAR(4) CONSTRAINT pk_class PRIMARY KEY);

CREATE TABLE member_of
(s_id CHAR(9) CONSTRAINT pk_member_of PRIMARY KEY
,c_id CHAR(4) CONSTRAINT fk_member_of_2 REFERENCES class(c_id) ON DELETE CASCADE
,CONSTRAINT fk_member_of_1 FOREIGN KEY (s_id) REFERENCES student(s_id) ON DELETE CASCADE);

CREATE TABLE TEACHER
(t_id CHAR(9) CONSTRAINT pk_teacher PRIMARY KEY
,t_pw VARCHAR(100)
,t_name VARCHAR(30));

CREATE TABLE head_of
(t_id CHAR(9) CONSTRAINT fk_head_of_1 REFERENCES teacher(t_id) ON DELETE CASCADE
,c_id CHAR(4) CONSTRAINT fk_head_of_2 REFERENCES class(c_id) ON DELETE CASCADE
,CONSTRAINT pk_head_of PRIMARY KEY (c_id));

CREATE TABLE subject
(sub_id VARCHAR2(10) CONSTRAINT pk_subject PRIMARY KEY
,sub_title VARCHAR2(50));

CREATE TABLE teaches
(t_id CHAR(9) CONSTRAINT fk_teaches_1 REFERENCES teacher(t_id)
,sub_id VARCHAR2(10) CONSTRAINT fk_teaches_2 REFERENCES subject(sub_id)
,c_id CHAR(4) CONSTRAINT fk_teaches_3 REFERENCES class(c_id)
,CONSTRAINT pk_teaches PRIMARY KEY (t_id, sub_id, c_id));

CREATE TABLE exam_sche
(t_id CHAR(9) CONSTRAINT fk_exam_sche REFERENCES teacher(t_id)
,e_id CHAR(4)
,e_start DATE
,e_dura INTERVAL DAY TO SECOND
,CONSTRAINT pk_exam_sche PRIMARY KEY (t_id,e_id));

CREATE TABLE sets
(t_id CHAR(9)
,e_id CHAR(4)
,c_id CHAR(4) CONSTRAINT fk_sets_1 REFERENCES class(c_id) ON DELETE CASCADE
,sub_id VARCHAR2(10) CONSTRAINT fk_sets_2 REFERENCES subject(sub_id) ON DELETE CASCADE
, CONSTRAINT fk_sets_3 FOREIGN KEY (t_id, e_id) REFERENCES exam_sche(t_id, e_id) ON DELETE CASCADE
, CONSTRAINT fk_sets_4 FOREIGN KEY (t_id, c_id, sub_id) REFERENCES teaches(t_id, c_id, sub_id) ON DELETE CASCADE
, CONSTRAINT pk_sets PRIMARY KEY (t_id, c_id));



CREATE OR REPLACE TYPE answer_sheet_t IS OBJECT
(answer_mc VARCHAR2(100)
,answer_fb VARCHAR2(1000)
,answer_fl CLOB)
NOT FINAL;
/

--Student records are found here
/* To delete a record:
    check if the record has been kept for 10 years already
    if not:
        do not delete
    else:
        check if the student is still active
        if not:
            delete
            delete from student table
        else:
            do not delete
*/
CREATE TABLE sits
(s_id CHAR(9) CONSTRAINT fk_sits REFERENCES student(s_id) --cannot delete from student without deleting the records
,t_id CHAR(9)
,e_id CHAR(4)
,answer_sheet answer_sheet_t
,grade NUMBER
,feedback CLOB
,CONSTRAINT pk_sits PRIMARY KEY (s_id, t_id, e_id)
,CONSTRAINT fk_sits_2 FOREIGN KEY (t_id, e_id) REFERENCES exam_sche(t_id,e_id) ON DELETE CASCADE);

CREATE OR REPLACE TYPE question_mc_t IS OBJECT
(text CLOB
,option_a CLOB
,option_b CLOB
,option_c CLOB
,option_d CLOB
,answer CHAR(1)
,point NUMBER
,flag NUMBER)
NOT FINAL;
/
 
CREATE OR REPLACE TYPE question_fb_t IS OBJECT
(text CLOB
,answer VARCHAR(1000)
,point NUMBER
,flag NUMBER)
NOT FINAL;
/

CREATE OR REPLACE TYPE question_fl_t IS OBJECT
(text CLOB
,point NUMBER
,flag NUMBER)
NOT FINAL;
/

CREATE OR REPLACE TYPE question_mc_table IS TABLE OF question_mc_t;
/
CREATE OR REPLACE TYPE question_fb_table IS TABLE OF question_fb_t;
/
CREATE OR REPLACE TYPE question_fl_table IS TABLE OF question_fl_t; 
/

CREATE TABLE paper
(p_id CHAR(4)
,t_id CHAR(9)
,e_id CHAR(4)
,p_mc QUESTION_MC_TABLE
,p_fb QUESTION_FB_TABLE
,p_fl QUESTION_FL_TABLE
,CONSTRAINT pk_paper PRIMARY KEY (p_id ,e_id, t_id)
,CONSTRAINT fk_paper FOREIGN KEY (e_id, t_id) REFERENCES exam_sche(e_id,t_id) ON DELETE CASCADE)
NESTED TABLE p_mc STORE AS p_mc_tab return as value
NESTED TABLE p_fb STORE AS p_fb_tab return as value
NESTED TABLE p_fl STORE AS p_fl_tab return as value;


CREATE OR REPLACE PROCEDURE s_register(s_id IN CHAR, s_pw IN VARCHAR2, s_name VARCHAR) IS
BEGIN
    insert into student values(s_id, s_pw, 1, s_name);
END s_register;
/

CREATE OR REPLACE PROCEDURE t_register(t_id IN CHAR, t_pw IN VARCHAR2, t_name VARCHAR) IS
BEGIN
    insert into teacher values(t_id, t_pw, t_name);
END t_register;
/

CREATE OR REPLACE PROCEDURE c_register(c_id IN CHAR) IS
BEGIN
    insert into class values(c_id);
END c_register;
/

CREATE OR REPLACE PROCEDURE sub_register(sub_id IN CHAR, sub_title IN VARCHAR) IS
BEGIN
    insert into subject values(sub_id,sub_title);
END sub_register;
/

CREATE OR REPLACE PROCEDURE make_head(t_id IN CHAR, c_id IN CHAR) IS
BEGIN
    insert into head_of values(t_id, c_id);
END make_head;
/

CREATE OR REPLACE PROCEDURE make_member(c_id IN CHAR, s_id IN CHAR) IS
BEGIN 
    insert into member_of values(s_id, c_id);
END make_member;
/

CREATE OR REPLACE PROCEDURE make_teach(t_id IN CHAR, sub_id IN CHAR, c_id IN CHAR) IS
BEGIN
    INSERT INTO teaches values(t_id, sub_id, c_id);
END make_teach;
/

CREATE OR REPLACE PROCEDURE make_sche(t_id IN CHAR, e_id IN CHAR, e_start IN DATE, e_dura IN INTERVAL DAY TO SECOND) IS
BEGIN
    INSERT INTO exam_sche values(t_id, e_id, e_start, e_dura);
END make_sche;
/

CREATE OR REPLACE FUNCTION make_mc(text IN CLOB, option_a CLOB ,option_b CLOB ,option_c CLOB ,option_d CLOB ,answer CHAR ,point NUMBER ,flag NUMBER)
    RETURN question_mc_t IS
question_mc question_mc_t := question_mc_t(text,option_a,option_b,option_c,option_d,answer,point,flag);
BEGIN
    return question_mc;
END make_mc;
/

CREATE OR REPLACE FUNCTION make_fb(text IN CLOB ,answer CHAR ,point NUMBER ,flag NUMBER)
    RETURN question_fb_t IS
question_fb question_fb_t := question_fb_t(text,answer,point,flag);
BEGIN
    return question_fb;
END make_fb;
/

CREATE OR REPLACE FUNCTION make_fl(text IN CLOB,point NUMBER ,flag NUMBER)
    RETURN question_fl_t IS
question_fl question_fl_t := question_fl_t(text,point,flag);
BEGIN
    return question_fl;
END make_fl;
/
    
create or replace PROCEDURE make_empty_paper(p_id IN CHAR, e_id IN CHAR, t_id IN CHAR) IS
BEGIN
    insert into paper values(p_id, t_id, e_id, question_mc_table(), question_fb_table(), question_fl_table());
END make_empty_paper;
/

create or replace PROCEDURE add_mc_to_paper(p_id IN CHAR, e_id IN CHAR, mc IN QUESTION_MC_T) IS
mc_table QUESTION_MC_TABLE;
BEGIN
    select p_mc into mc_table from paper p where p.p_id = p_id and p.e_id = e_id;
    mc_table.EXTEND;
    mc_table(mc_table.LAST) := mc;
    update paper p
    SET p.p_mc = mc_table
    where p.p_id = p_id and p.e_id = e_id;
END;
/

create or replace PROCEDURE add_fb_to_paper(p_id IN CHAR, e_id IN CHAR, fb IN QUESTION_fb_T) IS
fb_table QUESTION_FB_TABLE;
BEGIN
    select p_fb into fb_table from paper p where p.p_id = p_id and p.e_id = e_id;
    fb_table.EXTEND;
    fb_table(fb_table.LAST) := fb;
    update paper p
    SET p.p_fb = fb_table
    where p.p_id = p_id and p.e_id = e_id;
END;
/

create or replace PROCEDURE add_fl_to_paper(p_id IN CHAR, e_id IN CHAR, fl IN QUESTION_fl_T) IS
fl_table QUESTION_FL_TABLE;
BEGIN
    select p_fl into fl_table from paper p where p.p_id = p_id and p.e_id = e_id;
    fl_table.EXTEND;
    fl_table(fl_table.LAST) := fl;
    update paper p
    SET p.p_fl = fl_table
    where p.p_id = p_id and p.e_id = e_id;
END;
/

CREATE OR REPLACE PROCEDURE set_exam(t_id IN CHAR, e_id IN CHAR, c_id IN CHAR, sub_id IN CHAR) IS
BEGIN
    insert into sets values(t_id, e_id, c_id, sub_id);
END;
/

CREATE OR REPLACE PROCEDURE store_answer_sheet(s_id IN CHAR ,answer_mc IN VARCHAR2, answer_fb IN VARCHAR2, answer_fl IN CLOB) IS
sheet ANSWER_SHEET_T;
BEGIN
    sheet := answer_sheet_t(answer_mc, answer_fb, answer_fl);
    UPDATE sits s
    SET answer_sheet = sheet
    WHERE s.s_id = s_id;
END;
/

CREATE OR REPLACE PROCEDURE student_sits_exam(s_id IN CHAR, t_id IN CHAR, e_id IN CHAR 
    ,answer_mc IN VARCHAR2, answer_fb IN VARCHAR2, answer_fl IN CLOB) IS
BEGIN
    insert into sits values(s_id,t_id,e_id,null,null,null);
    store_answer_sheet(s_id,answer_mc,answer_fb,answer_fl);
END;
/

CREATE OR REPLACE PROCEDURE student_gets_grade(s_idin IN CHAR,t_idin IN CHAR, e_idin IN CHAR, gradein IN NUMBER) IS
BEGIN
    UPDATE sits st
    SET st.grade = gradein
    where st.s_id = s_idin and st.t_id = t_idin and st.e_id = e_idin;
END;
/

CREATE OR REPLACE PROCEDURE student_gets_feedback(s_idin IN CHAR,t_idin IN CHAR, e_idin IN CHAR, feedbackin IN CLOB) IS
BEGIN
    UPDATE sits st
    SET st.feedback = feedbackin
    where st.s_id = s_idin and st.t_id = t_idin and st.e_id = e_idin;
END;
/

commit;