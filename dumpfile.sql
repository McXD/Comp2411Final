--------------------------------------------------------
--  File created - Monday-November-30-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Type ANSWER_SHEET_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##FENG"."ANSWER_SHEET_T" IS OBJECT
(answer_mc VARCHAR2(100)
,answer_fb VARCHAR2(1000)
,answer_fl VARCHAR2(4000))
NOT FINAL;

/
--------------------------------------------------------
--  DDL for Type QUESTION_FB_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##FENG"."QUESTION_FB_T" IS OBJECT
(text VARCHAR2(4000)
,answer VARCHAR(1000)
,point NUMBER
,flag NUMBER)
NOT FINAL;

/
--------------------------------------------------------
--  DDL for Type QUESTION_FB_TABLE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##FENG"."QUESTION_FB_TABLE" IS TABLE OF question_fb_t;

/
--------------------------------------------------------
--  DDL for Type QUESTION_FL_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##FENG"."QUESTION_FL_T" IS OBJECT
(text VARCHAR2(4000)
,point NUMBER
,flag NUMBER)
NOT FINAL;

/
--------------------------------------------------------
--  DDL for Type QUESTION_FL_TABLE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##FENG"."QUESTION_FL_TABLE" IS TABLE OF question_fl_t; 

/
--------------------------------------------------------
--  DDL for Type QUESTION_MC_T
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##FENG"."QUESTION_MC_T" IS OBJECT
(text VARCHAR2(4000)
,option_a VARCHAR2(4000)
,option_b VARCHAR2(4000)
,option_c VARCHAR2(4000)
,option_d VARCHAR2(4000)
,answer CHAR(1)
,point NUMBER
,flag NUMBER)
NOT FINAL;

/
--------------------------------------------------------
--  DDL for Type QUESTION_MC_TABLE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "C##FENG"."QUESTION_MC_TABLE" IS TABLE OF question_mc_t;

/
--------------------------------------------------------
--  DDL for Table CLASS
--------------------------------------------------------

  CREATE TABLE "C##FENG"."CLASS" 
   (	"C_ID" CHAR(4 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table EXAM_SCHE
--------------------------------------------------------

  CREATE TABLE "C##FENG"."EXAM_SCHE" 
   (	"T_ID" CHAR(9 BYTE), 
	"E_ID" CHAR(4 BYTE), 
	"E_START" TIMESTAMP (6), 
	"E_DURA" NUMBER
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table HEAD_OF
--------------------------------------------------------

  CREATE TABLE "C##FENG"."HEAD_OF" 
   (	"T_ID" CHAR(9 BYTE), 
	"C_ID" CHAR(4 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table MEMBER_OF
--------------------------------------------------------

  CREATE TABLE "C##FENG"."MEMBER_OF" 
   (	"S_ID" CHAR(9 BYTE), 
	"C_ID" CHAR(4 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table PAPER
--------------------------------------------------------

  CREATE TABLE "C##FENG"."PAPER" 
   (	"P_ID" CHAR(4 BYTE), 
	"T_ID" CHAR(9 BYTE), 
	"E_ID" CHAR(4 BYTE), 
	"P_MC" "C##FENG"."QUESTION_MC_TABLE" , 
	"P_FB" "C##FENG"."QUESTION_FB_TABLE" , 
	"P_FL" "C##FENG"."QUESTION_FL_TABLE" 
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" 
 NESTED TABLE "P_MC" STORE AS "P_MC_TAB"
 (PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
 NOCOMPRESS 
  TABLESPACE "USERS" ) RETURN AS VALUE
 NESTED TABLE "P_FB" STORE AS "P_FB_TAB"
 (PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
 NOCOMPRESS 
  TABLESPACE "USERS" ) RETURN AS VALUE
 NESTED TABLE "P_FL" STORE AS "P_FL_TAB"
 (PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
 NOCOMPRESS 
  TABLESPACE "USERS" ) RETURN AS VALUE;
-- Unable to render TABLE DDL for object C##FENG.P_FB_TAB with DBMS_METADATA attempting internal generator.
CREATE TABLE P_FB_TAB
-- Unable to render TABLE DDL for object C##FENG.P_FL_TAB with DBMS_METADATA attempting internal generator.
CREATE TABLE P_FL_TAB
-- Unable to render TABLE DDL for object C##FENG.P_MC_TAB with DBMS_METADATA attempting internal generator.
CREATE TABLE P_MC_TAB
--------------------------------------------------------
--  DDL for Table SETS
--------------------------------------------------------

  CREATE TABLE "C##FENG"."SETS" 
   (	"T_ID" CHAR(9 BYTE), 
	"E_ID" CHAR(4 BYTE), 
	"C_ID" CHAR(4 BYTE), 
	"SUB_ID" VARCHAR2(10 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SITS
--------------------------------------------------------

  CREATE TABLE "C##FENG"."SITS" 
   (	"S_ID" CHAR(9 BYTE), 
	"T_ID" CHAR(9 BYTE), 
	"E_ID" CHAR(4 BYTE), 
	"ANSWER_SHEET" "C##FENG"."ANSWER_SHEET_T" , 
	"GRADE" NUMBER, 
	"FEEDBACK" VARCHAR2(4000 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table STUDENT
--------------------------------------------------------

  CREATE TABLE "C##FENG"."STUDENT" 
   (	"S_ID" CHAR(9 BYTE), 
	"S_PW" VARCHAR2(100 BYTE), 
	"S_FLAG" NUMBER, 
	"S_NAME" VARCHAR2(30 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SUBJECT
--------------------------------------------------------

  CREATE TABLE "C##FENG"."SUBJECT" 
   (	"SUB_ID" VARCHAR2(10 BYTE), 
	"SUB_TITLE" VARCHAR2(50 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table TEACHER
--------------------------------------------------------

  CREATE TABLE "C##FENG"."TEACHER" 
   (	"T_ID" CHAR(9 BYTE), 
	"T_PW" VARCHAR2(100 BYTE), 
	"T_NAME" VARCHAR2(30 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table TEACHES
--------------------------------------------------------

  CREATE TABLE "C##FENG"."TEACHES" 
   (	"T_ID" CHAR(9 BYTE), 
	"SUB_ID" VARCHAR2(10 BYTE), 
	"C_ID" CHAR(4 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into C##FENG.CLASS
SET DEFINE OFF;
Insert into C##FENG.CLASS (C_ID) values ('1901');
Insert into C##FENG.CLASS (C_ID) values ('1902');
Insert into C##FENG.CLASS (C_ID) values ('2001');
Insert into C##FENG.CLASS (C_ID) values ('2002');
REM INSERTING into C##FENG.EXAM_SCHE
SET DEFINE OFF;
Insert into C##FENG.EXAM_SCHE (T_ID,E_ID,E_START,E_DURA) values ('16096548d','0001',to_timestamp('01-DEC-20 12.00.00.000000000 PM','DD-MON-RR HH.MI.SSXFF AM'),90);
REM INSERTING into C##FENG.HEAD_OF
SET DEFINE OFF;
Insert into C##FENG.HEAD_OF (T_ID,C_ID) values ('15689034d','1901');
Insert into C##FENG.HEAD_OF (T_ID,C_ID) values ('16245231g','1902');
Insert into C##FENG.HEAD_OF (T_ID,C_ID) values ('15087623d','2001');
Insert into C##FENG.HEAD_OF (T_ID,C_ID) values ('15092654d','2002');
REM INSERTING into C##FENG.MEMBER_OF
SET DEFINE OFF;
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19079395d','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19079632d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19079662d','1901');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19079784d','1901');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19079853d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19079968d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19080037d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19080113d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19080189d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19080341d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19080371d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19080403d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19080433d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19080463d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19080532d','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19080684d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19081789d','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19082848d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19082878d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19083967d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19083981d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19084103d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19084186d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19084262d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19084858d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19084941d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19085314d','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19085436d','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19085595d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19085694d','1901');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19085726d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19085878d','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19085984d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19086067d','1901');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19086159d','1901');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19086235d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19086364d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19086654d','1901');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19086723d','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19086838d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19086944d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19087011d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19087103d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19087651g','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19088901d','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19089769d','1901');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19089951d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19091673g','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19091827d','1901');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19091857g','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19092274g','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19092541g','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19093768g','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19094026g','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19094284g','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19094705d','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19096142g','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19098145g','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19098214g','1901');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19098297d','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19098594d','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19099066d','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19100369g','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19100482g','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19100728g','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19101122g','2001');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19102897g','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19103689g','1901');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19103841d','1901');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19103871d','1901');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19103926d','1902');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19103956d','1901');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19104801d','2002');
Insert into C##FENG.MEMBER_OF (S_ID,C_ID) values ('19107412d','1902');
REM INSERTING into C##FENG.PAPER
SET DEFINE OFF;
Insert into C##FENG.PAPER (P_ID,T_ID,E_ID) values ('0001','16096548d','0001');
REM INSERTING into C##FENG.SETS
SET DEFINE OFF;
Insert into C##FENG.SETS (T_ID,E_ID,C_ID,SUB_ID) values ('16096548d','0001','1902','AF2108');
REM INSERTING into C##FENG.SITS
SET DEFINE OFF;
REM INSERTING into C##FENG.STUDENT
SET DEFINE OFF;
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19079395d','00000000',1,'Liam');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19079632d','00000000',1,'Noah');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19079662d','00000000',1,'Oliver');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19079784d','00000000',1,'William');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19079853d','00000000',1,'Elijah');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19079968d','00000000',1,'James');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19080037d','00000000',1,'Benjamin');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19080113d','00000000',1,'Lucas');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19080189d','00000000',1,'Mason');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19080341d','00000000',1,'Ethan');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19080371d','00000000',1,'Alexander');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19080403d','00000000',1,'Henry');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19080433d','00000000',1,'Jacob');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19080463d','00000000',1,'Michael');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19080532d','00000000',1,'Daniel');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19080684d','00000000',1,'Logan');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19081789d','00000000',1,'Jackson');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19082848d','00000000',1,'Sebastian');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19082878d','00000000',1,'Jack');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19083967d','00000000',1,'Aiden');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19083981d','00000000',1,'Owen');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19084103d','00000000',1,'Samuel');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19084186d','00000000',1,'Matthew');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19084262d','00000000',1,'Joseph');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19084858d','00000000',1,'Levi');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19084941d','00000000',1,'Mateo');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19085314d','00000000',1,'David');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19085436d','00000000',1,'John');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19085595d','00000000',1,'Wyatt');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19085694d','00000000',1,'Carter');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19085726d','00000000',1,'Julian');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19085878d','00000000',1,'Luke');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19085984d','00000000',1,'Grayson');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19086067d','00000000',1,'Isaac');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19086159d','00000000',1,'Jayden');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19086235d','00000000',1,'Theodore');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19086364d','00000000',1,'Gabriel');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19086654d','00000000',1,'Anthony');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19086723d','00000000',1,'Dylan');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19086838d','00000000',1,'Leo');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19086944d','00000000',1,'Olivia');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19087011d','00000000',1,'Emma');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19087103d','00000000',1,'Ava');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19087651g','00000000',1,'Sophia');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19088901d','00000000',1,'Isabella');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19089769d','00000000',1,'Charlotte');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19089951d','00000000',1,'Amelia');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19091673g','00000000',1,'Mia');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19091827d','00000000',1,'Harper');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19091857g','00000000',1,'Evelyn');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19092274g','00000000',1,'Abigail');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19092541g','00000000',1,'Emily');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19093768g','00000000',1,'Ella');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19094026g','00000000',1,'Elizabeth');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19094284g','00000000',1,'Camila');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19094705d','00000000',1,'Luna');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19096142g','00000000',1,'Sofia');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19098145g','00000000',1,'Avery');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19098214g','00000000',1,'Mila');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19098297d','00000000',1,'Aria');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19098594d','00000000',1,'Scarlett');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19099066d','00000000',1,'Penelope');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19100369g','00000000',1,'Layla');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19100482g','00000000',1,'Chloe');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19100728g','00000000',1,'Victoria');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19101122g','00000000',1,'Madison');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19102897g','00000000',1,'Eleanor');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19103689g','00000000',1,'Grace');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19103841d','00000000',1,'Nora');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19103871d','00000000',1,'Riley');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19103926d','00000000',1,'Zoey');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19103956d','00000000',1,'Hannah');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19104801d','00000000',1,'Hazel');
Insert into C##FENG.STUDENT (S_ID,S_PW,S_FLAG,S_NAME) values ('19107412d','00000000',1,'Lily');
REM INSERTING into C##FENG.SUBJECT
SET DEFINE OFF;
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('AP10005','PHYSICS I');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('AP10006','PHYSICS II');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('AMA1100','BASIC MATHEMATICS');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('AMA1104','INTRODUCTORY PROBABILITY');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('BME11101','BIONIC HUMAN AND THE FUTURE OF BEING HUMAN');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('BME11108','BIOMEDICAL ENGINEERING IN SOCIETY');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('AF1605','INTRODUCTION TO ECONOMICS');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('AF2108','FINANCIAL ACCOUNTING');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('COMP2011','DATA STRUCTURES');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('COMP2012','DISCRETE MATHEMATICS');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('COMP2021','OBJECT-ORIENTED PROGRAMMING');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('COMP2121','E-BUSINESS');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('CBS2904','ACADEMIC AND TECHNICAL CHINESE WRITING');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('ENGL2000','ENGLISH FOR EFFECTIVE COMMUNICATION');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('ENGL2003','ENGLISH FOR ADVANCED ACADEMIC WRITING');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('AF2504','INTRODUCTION TO BUSINESS LAW');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('AF2602','GLOBAL ECONOMIC ENVIRONMENT');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('AF3110','INTERMEDIATE ACCOUNTING');
Insert into C##FENG.SUBJECT (SUB_ID,SUB_TITLE) values ('AF3507','COMPANY LAW');
REM INSERTING into C##FENG.TEACHER
SET DEFINE OFF;
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('16096548d','00000000','Cora');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('16055210d','00000000','Madelyn');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('16093976d','00000000','Alice');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('16049001d','00000000','Kinsley');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('16021553d','00000000','Hailey');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('15087623d','00000000','Gabriella');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('15098723d','00000000','Allison');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('14082912d','00000000','Gianna');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('12987412g','00000000','Serenity');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('16245231g','00000000','Samantha');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('15487291d','00000000','Mayson');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('15092654d','00000000','Niklaus');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('13459834h','00000000','Simeon');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('13578634h','00000000','Colter');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('12480989h','00000000','Davion');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('14567898d','00000000','Leroy');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('14789002d','00000000','Ayan');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('15689034d','00000000','Dilan');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('15880923d','00000000','Ephraim');
Insert into C##FENG.TEACHER (T_ID,T_PW,T_NAME) values ('14821341d','00000000','Anson');
REM INSERTING into C##FENG.TEACHES
SET DEFINE OFF;
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('14082912d','BME11101','1902');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('14082912d','BME11101','2001');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('14082912d','COMP2021','1901');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('14789002d','AF2108','1901');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('15087623d','AF3507','2002');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('15092654d','COMP2011','2001');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('15098723d','AMA1100','2002');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('15098723d','AMA1104','1902');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('15487291d','AP10005','1902');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('15487291d','AP10005','2001');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('15487291d','AP10005','2002');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('16021553d','COMP2012','1901');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('16049001d','AF3110','1901');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('16049001d','AP10006','2001');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('16055210d','AF2504','2002');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('16055210d','AMA1100','2001');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('16055210d','ENGL2003','1901');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('16055210d','ENGL2003','1902');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('16093976d','CBS2904','1901');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('16093976d','CBS2904','2001');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('16093976d','COMP2012','1902');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('16093976d','COMP2121','2002');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('16096548d','AF2108','1902');
Insert into C##FENG.TEACHES (T_ID,SUB_ID,C_ID) values ('16096548d','AMA1104','1901');
--------------------------------------------------------
--  DDL for Index PK_CLASS
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##FENG"."PK_CLASS" ON "C##FENG"."CLASS" ("C_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_EXAM_SCHE
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##FENG"."PK_EXAM_SCHE" ON "C##FENG"."EXAM_SCHE" ("T_ID", "E_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_HEAD_OF
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##FENG"."PK_HEAD_OF" ON "C##FENG"."HEAD_OF" ("C_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_MEMBER_OF
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##FENG"."PK_MEMBER_OF" ON "C##FENG"."MEMBER_OF" ("S_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_PAPER
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##FENG"."PK_PAPER" ON "C##FENG"."PAPER" ("P_ID", "E_ID", "T_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_SETS
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##FENG"."PK_SETS" ON "C##FENG"."SETS" ("T_ID", "C_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_SITS
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##FENG"."PK_SITS" ON "C##FENG"."SITS" ("S_ID", "T_ID", "E_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_STUDENT
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##FENG"."PK_STUDENT" ON "C##FENG"."STUDENT" ("S_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_SUBJECT
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##FENG"."PK_SUBJECT" ON "C##FENG"."SUBJECT" ("SUB_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_TEACHER
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##FENG"."PK_TEACHER" ON "C##FENG"."TEACHER" ("T_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_TEACHES
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##FENG"."PK_TEACHES" ON "C##FENG"."TEACHES" ("T_ID", "SUB_ID", "C_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_FK0000074806N00004$
--------------------------------------------------------

  CREATE INDEX "C##FENG"."SYS_FK0000074806N00004$" ON "C##FENG"."P_MC_TAB" ("NESTED_TABLE_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_FK0000074806N00008$
--------------------------------------------------------

  CREATE INDEX "C##FENG"."SYS_FK0000074806N00008$" ON "C##FENG"."P_FL_TAB" ("NESTED_TABLE_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_FK0000074806N00006$
--------------------------------------------------------

  CREATE INDEX "C##FENG"."SYS_FK0000074806N00006$" ON "C##FENG"."P_FB_TAB" ("NESTED_TABLE_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Procedure ADD_FB_TO_PAPER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."ADD_FB_TO_PAPER" (p_idin IN CHAR, t_idin IN CHAR, e_idin IN CHAR, fb IN QUESTION_fb_T) IS
BEGIN
    insert into table(select p_fb from paper where p_id = p_idin and e_id = e_idin and t_id = t_idin) values(fb);
END;

/
--------------------------------------------------------
--  DDL for Procedure ADD_FL_TO_PAPER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."ADD_FL_TO_PAPER" (p_idin IN CHAR, t_idin IN CHAR, e_idin IN CHAR, fl IN QUESTION_fl_T) IS
BEGIN
    insert into table(select p_fl from paper where p_id = p_idin and e_id = e_idin and t_id = t_idin) values(fl);
END;

/
--------------------------------------------------------
--  DDL for Procedure ADD_MC_TO_PAPER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."ADD_MC_TO_PAPER" (p_idin IN CHAR,t_idin IN CHAR, e_idin IN CHAR, mc IN QUESTION_MC_T) IS
BEGIN
    insert into table(select p_mc from paper where p_id = p_idin and e_id = e_idin and t_id = t_idin) values(mc);
END;

/
--------------------------------------------------------
--  DDL for Procedure C_REGISTER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."C_REGISTER" (c_id IN CHAR) IS
BEGIN
    insert into class values(c_id);
END c_register;

/
--------------------------------------------------------
--  DDL for Procedure MAKE_EMPTY_PAPER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."MAKE_EMPTY_PAPER" (p_id IN CHAR, e_id IN CHAR, t_id IN CHAR) IS
BEGIN
    insert into paper values(p_id, t_id, e_id, question_mc_table(), question_fb_table(), question_fl_table());
END make_empty_paper;

/
--------------------------------------------------------
--  DDL for Procedure MAKE_HEAD
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."MAKE_HEAD" (t_id IN CHAR, c_id IN CHAR) IS
BEGIN
    insert into head_of values(t_id, c_id);
END make_head;

/
--------------------------------------------------------
--  DDL for Procedure MAKE_MEMBER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."MAKE_MEMBER" (c_id IN CHAR, s_id IN CHAR) IS
BEGIN 
    insert into member_of values(s_id, c_id);
END make_member;

/
--------------------------------------------------------
--  DDL for Procedure MAKE_SCHE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."MAKE_SCHE" (t_id IN CHAR, e_id IN CHAR, e_start IN DATE, e_dura IN NUMBER) IS
BEGIN
    INSERT INTO exam_sche values(t_id, e_id, e_start, e_dura);
END make_sche;

/
--------------------------------------------------------
--  DDL for Procedure MAKE_TEACH
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."MAKE_TEACH" (t_id IN CHAR, sub_id IN CHAR, c_id IN CHAR) IS
BEGIN
    INSERT INTO teaches values(t_id, sub_id, c_id);
END make_teach;

/
--------------------------------------------------------
--  DDL for Procedure SET_EXAM
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."SET_EXAM" (t_id IN CHAR, e_id IN CHAR, c_id IN CHAR, sub_id IN CHAR) IS
BEGIN
    insert into sets values(t_id, e_id, c_id, sub_id);
END;

/
--------------------------------------------------------
--  DDL for Procedure STORE_ANSWER_SHEET
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."STORE_ANSWER_SHEET" (s_idin IN CHAR,e_idin IN CHAR, t_idin IN CHAR,answer_mc IN VARCHAR2, answer_fb IN VARCHAR2, answer_fl IN VARCHAR2) IS
sheet ANSWER_SHEET_T;
BEGIN
    sheet := answer_sheet_t(answer_mc, answer_fb, answer_fl);
    UPDATE sits s
    SET answer_sheet = sheet
    WHERE s.s_id = s_idin AND s.e_id = e_idin AND s.t_id = t_idin;
END;

/
--------------------------------------------------------
--  DDL for Procedure STUDENT_GETS_FEEDBACK
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."STUDENT_GETS_FEEDBACK" (s_idin IN CHAR,t_idin IN CHAR, e_idin IN CHAR, feedbackin IN VARCHAR2) IS
BEGIN
    UPDATE sits st
    SET st.feedback = feedbackin
    where st.s_id = s_idin and st.t_id = t_idin and st.e_id = e_idin;
END;

/
--------------------------------------------------------
--  DDL for Procedure STUDENT_GETS_GRADE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."STUDENT_GETS_GRADE" (s_idin IN CHAR,t_idin IN CHAR, e_idin IN CHAR, gradein IN NUMBER) IS
BEGIN
    UPDATE sits st
    SET st.grade = gradein
    where st.s_id = s_idin and st.t_id = t_idin and st.e_id = e_idin;
END;

/
--------------------------------------------------------
--  DDL for Procedure STUDENT_SITS_EXAM
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."STUDENT_SITS_EXAM" (s_id IN CHAR, t_id IN CHAR, e_id IN CHAR 
    ,answer_mc IN VARCHAR2, answer_fb IN VARCHAR2, answer_fl IN VARCHAR2) IS
BEGIN
    insert into sits values(s_id,t_id,e_id,null,null,null);
    store_answer_sheet(s_id,e_id,t_id,answer_mc,answer_fb,answer_fl);
END;

/
--------------------------------------------------------
--  DDL for Procedure SUB_REGISTER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."SUB_REGISTER" (sub_id IN CHAR, sub_title IN VARCHAR) IS
BEGIN
    insert into subject values(sub_id,sub_title);
END sub_register;

/
--------------------------------------------------------
--  DDL for Procedure S_REGISTER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."S_REGISTER" (s_id IN CHAR, s_pw IN VARCHAR2, s_name VARCHAR) IS
BEGIN
    insert into student values(s_id, s_pw, 1, s_name);
END s_register;

/
--------------------------------------------------------
--  DDL for Procedure T_REGISTER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "C##FENG"."T_REGISTER" (t_id IN CHAR, t_pw IN VARCHAR2, t_name VARCHAR) IS
BEGIN
    insert into teacher values(t_id, t_pw, t_name);
END t_register;

/
--------------------------------------------------------
--  DDL for Function MAKE_FB
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##FENG"."MAKE_FB" (text IN VARCHAR2 ,answer CHAR ,point NUMBER ,flag NUMBER)
    RETURN question_fb_t IS
question_fb question_fb_t := question_fb_t(text,answer,point,flag);
BEGIN
    return question_fb;
END make_fb;

/
--------------------------------------------------------
--  DDL for Function MAKE_FL
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##FENG"."MAKE_FL" (text IN VARCHAR2,point NUMBER ,flag NUMBER)
    RETURN question_fl_t IS
question_fl question_fl_t := question_fl_t(text,point,flag);
BEGIN
    return question_fl;
END make_fl;

/
--------------------------------------------------------
--  DDL for Function MAKE_MC
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##FENG"."MAKE_MC" (text IN VARCHAR2, option_a VARCHAR2 ,option_b VARCHAR2 ,option_c VARCHAR2 ,option_d VARCHAR2 ,answer CHAR ,point NUMBER ,flag NUMBER)
    RETURN question_mc_t IS
question_mc question_mc_t := question_mc_t(text,option_a,option_b,option_c,option_d,answer,point,flag);
BEGIN
    return question_mc;
END make_mc;

/
--------------------------------------------------------
--  Constraints for Table PAPER
--------------------------------------------------------

  ALTER TABLE "C##FENG"."PAPER" ADD CONSTRAINT "PK_PAPER" PRIMARY KEY ("P_ID", "E_ID", "T_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##FENG"."PAPER" ADD UNIQUE ("P_FL") RELY
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##FENG"."PAPER" ADD UNIQUE ("P_FB") RELY
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##FENG"."PAPER" ADD UNIQUE ("P_MC") RELY
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table STUDENT
--------------------------------------------------------

  ALTER TABLE "C##FENG"."STUDENT" ADD CONSTRAINT "PK_STUDENT" PRIMARY KEY ("S_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table EXAM_SCHE
--------------------------------------------------------

  ALTER TABLE "C##FENG"."EXAM_SCHE" ADD CONSTRAINT "PK_EXAM_SCHE" PRIMARY KEY ("T_ID", "E_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table SUBJECT
--------------------------------------------------------

  ALTER TABLE "C##FENG"."SUBJECT" ADD CONSTRAINT "PK_SUBJECT" PRIMARY KEY ("SUB_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table MEMBER_OF
--------------------------------------------------------

  ALTER TABLE "C##FENG"."MEMBER_OF" ADD CONSTRAINT "PK_MEMBER_OF" PRIMARY KEY ("S_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table HEAD_OF
--------------------------------------------------------

  ALTER TABLE "C##FENG"."HEAD_OF" ADD CONSTRAINT "PK_HEAD_OF" PRIMARY KEY ("C_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table SITS
--------------------------------------------------------

  ALTER TABLE "C##FENG"."SITS" ADD CONSTRAINT "PK_SITS" PRIMARY KEY ("S_ID", "T_ID", "E_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table TEACHES
--------------------------------------------------------

  ALTER TABLE "C##FENG"."TEACHES" ADD CONSTRAINT "PK_TEACHES" PRIMARY KEY ("T_ID", "SUB_ID", "C_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table SETS
--------------------------------------------------------

  ALTER TABLE "C##FENG"."SETS" ADD CONSTRAINT "PK_SETS" PRIMARY KEY ("T_ID", "C_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table TEACHER
--------------------------------------------------------

  ALTER TABLE "C##FENG"."TEACHER" ADD CONSTRAINT "PK_TEACHER" PRIMARY KEY ("T_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table CLASS
--------------------------------------------------------

  ALTER TABLE "C##FENG"."CLASS" ADD CONSTRAINT "PK_CLASS" PRIMARY KEY ("C_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table EXAM_SCHE
--------------------------------------------------------

  ALTER TABLE "C##FENG"."EXAM_SCHE" ADD CONSTRAINT "FK_EXAM_SCHE" FOREIGN KEY ("T_ID")
	  REFERENCES "C##FENG"."TEACHER" ("T_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table HEAD_OF
--------------------------------------------------------

  ALTER TABLE "C##FENG"."HEAD_OF" ADD CONSTRAINT "FK_HEAD_OF_1" FOREIGN KEY ("T_ID")
	  REFERENCES "C##FENG"."TEACHER" ("T_ID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "C##FENG"."HEAD_OF" ADD CONSTRAINT "FK_HEAD_OF_2" FOREIGN KEY ("C_ID")
	  REFERENCES "C##FENG"."CLASS" ("C_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table MEMBER_OF
--------------------------------------------------------

  ALTER TABLE "C##FENG"."MEMBER_OF" ADD CONSTRAINT "FK_MEMBER_OF_2" FOREIGN KEY ("C_ID")
	  REFERENCES "C##FENG"."CLASS" ("C_ID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "C##FENG"."MEMBER_OF" ADD CONSTRAINT "FK_MEMBER_OF_1" FOREIGN KEY ("S_ID")
	  REFERENCES "C##FENG"."STUDENT" ("S_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PAPER
--------------------------------------------------------

  ALTER TABLE "C##FENG"."PAPER" ADD CONSTRAINT "FK_PAPER" FOREIGN KEY ("T_ID", "E_ID")
	  REFERENCES "C##FENG"."EXAM_SCHE" ("T_ID", "E_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SETS
--------------------------------------------------------

  ALTER TABLE "C##FENG"."SETS" ADD CONSTRAINT "FK_SETS_1" FOREIGN KEY ("C_ID")
	  REFERENCES "C##FENG"."CLASS" ("C_ID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "C##FENG"."SETS" ADD CONSTRAINT "FK_SETS_2" FOREIGN KEY ("SUB_ID")
	  REFERENCES "C##FENG"."SUBJECT" ("SUB_ID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "C##FENG"."SETS" ADD CONSTRAINT "FK_SETS_3" FOREIGN KEY ("T_ID", "E_ID")
	  REFERENCES "C##FENG"."EXAM_SCHE" ("T_ID", "E_ID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "C##FENG"."SETS" ADD CONSTRAINT "FK_SETS_4" FOREIGN KEY ("T_ID", "SUB_ID", "C_ID")
	  REFERENCES "C##FENG"."TEACHES" ("T_ID", "SUB_ID", "C_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SITS
--------------------------------------------------------

  ALTER TABLE "C##FENG"."SITS" ADD CONSTRAINT "FK_SITS" FOREIGN KEY ("S_ID")
	  REFERENCES "C##FENG"."STUDENT" ("S_ID") ENABLE;
  ALTER TABLE "C##FENG"."SITS" ADD CONSTRAINT "FK_SITS_2" FOREIGN KEY ("T_ID", "E_ID")
	  REFERENCES "C##FENG"."EXAM_SCHE" ("T_ID", "E_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table TEACHES
--------------------------------------------------------

  ALTER TABLE "C##FENG"."TEACHES" ADD CONSTRAINT "FK_TEACHES_1" FOREIGN KEY ("T_ID")
	  REFERENCES "C##FENG"."TEACHER" ("T_ID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "C##FENG"."TEACHES" ADD CONSTRAINT "FK_TEACHES_2" FOREIGN KEY ("SUB_ID")
	  REFERENCES "C##FENG"."SUBJECT" ("SUB_ID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "C##FENG"."TEACHES" ADD CONSTRAINT "FK_TEACHES_3" FOREIGN KEY ("C_ID")
	  REFERENCES "C##FENG"."CLASS" ("C_ID") ON DELETE CASCADE ENABLE;
