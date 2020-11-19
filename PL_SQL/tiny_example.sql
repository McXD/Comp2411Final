--Tiny Sample For Demenstration

--Student
EXECUTE s_register('19076509d','00000000', 'Brandon Stark');
EXECUTE s_register('19084109d','00000000', 'Jaime Lannister');
EXECUTE s_register('19053098d','00000000', 'Joffery Baratheon');

--Teacher
EXECUTE t_register('16098743d','11111111','Pycell');
EXECUTE t_register('15046578d','11111111','Benjon');
EXECUTE t_register('17024176d','11111111','Tyrion');

--Class
EXECUTE c_register('2001');

--Subject
EXECUTE sub_register('LITE-001','FRENCH BASIC');
EXECUTE sub_register('MILL-101','SWORD SKILL');
EXECUTE sub_register('POLI-101','INTRODUCTION TO POLITICS');

--MAKE_MEMBER
EXECUTE make_member('2001','19076509d');
EXECUTE make_member('2001','19084109d');
EXECUTE make_member('2001','19053098d');

--MAKE_HEAD
EXECUTE make_head('17024176d','2001');

--MAKE_TEACH
EXECUTE make_teach('17024176d','POLI-101','2001');
EXECUTE make_teach('16098743d','LITE-001','2001');
EXECUTE make_teach('15046578d','MILL-101','2001');

--MAKE_SCHE
EXECUTE make_sche('15046578d','2001',null,null);

--SET_EXAM
EXECUTE set_exam('15046578d','2001','2001','MILL-101'); 

--MAKE PAPER
EXECUTE make_empty_paper('0000','2001','15046578d');

--ADD QUESTIONS TO PAPER
EXECUTE add_mc_to_paper('0000','2001', make_mc('1+1=?','1','2','3','4','B',3,1));
EXECUTE add_mc_to_paper('0000','2001', make_mc('1+2=?','1','2','3','4','C',3,1));
EXECUTE add_fb_to_paper('0000','2001', make_fb('1+1=__','2',3,1));
EXECUTE add_fb_to_paper('0000','2001', make_fb('1+2=__','3',3,1));
EXECUTE add_fl_to_paper('0000','2001', make_fl('what 1 + 1 yields?', 3 ,1));
EXECUTE add_fl_to_paper('0000','2001', make_fl('what 1 + 2 yields?', 3 ,1));

--STUDENT SITS EXAM
EXECUTE student_sits_exam('19076509d','15046578d','2001','BC','2/3','2/3');

--STUDENT GETS GRADE AND FEEDBACK
EXECUTE student_gets_grade('19076509d','15046578d','2001', 18);
EXECUTE student_gets_feedback('19076509d','15046578d','2001', 'EXCELLENT!');

commit;

--Check execution result
SELECT * FROM student;
SELECT * FROM teacher;
SELECT * FROM class;
SELECT * FROM subject;
SELECT * FROM member_of;
SELECT * FROM teaches;
select * from head_of;
select * from exam_sche;
select * from paper;
--check questions in paper
select * from table(select p_mc from paper where p_id = '0000');
select * from table(select p_fb from paper where p_id = '0000');
select * from table(select p_fl from paper where p_id = '0000');

select * from sits;
--check answer_sheet
select s.answer_sheet.answer_mc, s.answer_sheet.answer_fb, s.answer_sheet.answer_fl
from sits s where s_id = '19076509d';