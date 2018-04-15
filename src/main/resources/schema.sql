--------------------------------------------------------
--  DDL for Sequence TRAINEE_ID_SEQUENCE
--------------------------------------------------------
   CREATE SEQUENCE  "TRAINEE_ID_SEQ" INCREMENT BY 1 START WITH 1;
--------------------------------------------------------
--  DDL for Sequence TRAINER_ID_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "TRAINER_ID_SEQ" INCREMENT BY 1 START WITH 1;
--------------------------------------------------------
--  DDL for Sequence USER_ID_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "USER_ID_SEQ" INCREMENT BY 1 START WITH 1;
--------------------------------------------------------
--  DDL for Table TRAINEE
--------------------------------------------------------
  CREATE TABLE "TRAINEE"(	
	"RESOURCE_ID" VARCHAR2(40), 
	"TRAINING_STATUS" VARCHAR2(40),  
	"SKYPE_ID" VARCHAR2(256), 
	"RECRUITER_NAME" VARCHAR2(256), 
	"PROFILE_URL" VARCHAR2(256), 
	"COLLEGE" VARCHAR2(200), 
	"DEGREE" VARCHAR2(200), 
	"MAJOR" VARCHAR2(100), 
	"TECH_SCREENER" VARCHAR2(256), 
	"PROJECT_COMPLETION" VARCHAR2(30), 
	"FLAG_STATUS" VARCHAR2(30), 
	"FLAG_NOTES" VARCHAR2(256), 
	"MARKETING_STATUS" VARCHAR2(50), 
	"CLIENT" VARCHAR2(200), 
	"END_CLIENT" VARCHAR2(200),
	"USER_ID" NUMBER(10) NOT NULL
   );
--------------------------------------------------------
--  DDL for Table _USER
--------------------------------------------------------

  CREATE TABLE "REVATURE_USER"(	
    "USER_ID" NUMBER(10) NOT NULL, 
	"PASSWORD" VARCHAR2(30), 
	"BACKUP_PASSWORD" VARCHAR2(30), 
	"EMAIL" VARCHAR2(30) NOT NULL, 
	"FIRST_NAME" VARCHAR2(30), 
	"MIDDLE_NAME" VARCHAR2(30), 
	"LAST_NAME" VARCHAR2(30), 
	"ROLE" VARCHAR2(30), 
	"HOME_PHONE" VARCHAR2(30), 
	"MOBILE_PHONE" VARCHAR2(30), 
	"TOKEN" VARCHAR2(260)
   );
--------------------------------------------------------
--  DDL for Table ROLES
--------------------------------------------------------

  CREATE TABLE "USER_ROLE" (	
    "ROLE_ID" NUMBER(10) NOT NULL, 
	"ROLE" VARCHAR2(30)
   );
--------------------------------------------------------
--  DDL for Table TRAINER
--------------------------------------------------------

  CREATE TABLE "TRAINER"(	
	"TITLE" VARCHAR2(30) NOT NULL, 
	"USER_ID" NUMBER(10) NOT NULL
   );
   --------------------------------------------------------
--  DDL for Table TRAINER
--------------------------------------------------------

  CREATE TABLE "TRAINEE_BATCH"(	
    "BATCH_ID" NUMBER(10) NOT NULL,  
	"USER_ID" NUMBER(10) NOT NULL
   );
--------------------------------------------------------
--  Constraints for Table TRAINEE_BATCH
--------------------------------------------------------

  ALTER TABLE "TRAINEE_BATCH" ADD CONSTRAINT "TRAINEE_BATCH_PRIMARY_KEY" PRIMARY KEY ("BATCH_ID", "USER_ID");
--------------------------------------------------------
--  Constraints for Table TRAINER
--------------------------------------------------------

  ALTER TABLE "TRAINER" ADD PRIMARY KEY ("USER_ID");
--------------------------------------------------------
--  Constraints for Table _USER
--------------------------------------------------------

  ALTER TABLE "REVATURE_USER" ADD UNIQUE ("EMAIL");
  ALTER TABLE "REVATURE_USER" ADD PRIMARY KEY ("USER_ID");
--------------------------------------------------------
--  Constraints for Table ROLES
--------------------------------------------------------

  ALTER TABLE "USER_ROLE" ADD PRIMARY KEY ("ROLE_ID");
--------------------------------------------------------
--  Constraints for Table _TRAINEE
--------------------------------------------------------

  ALTER TABLE "TRAINEE" ADD PRIMARY KEY ("USER_ID");
--------------------------------------------------------
--  Ref Constraints for Table _TRAINEE
--------------------------------------------------------

  ALTER TABLE "TRAINEE" ADD FOREIGN KEY ("USER_ID")
	  REFERENCES "REVATURE_USER" ("USER_ID");

--------------------------------------------------------
--  Ref Constraints for Table TRAINER
--------------------------------------------------------

  ALTER TABLE "TRAINER" ADD FOREIGN KEY ("USER_ID")
	  REFERENCES "REVATURE_USER" ("USER_ID");
