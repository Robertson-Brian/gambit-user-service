--------------------------------------------------------
--  DDL for Sequence USER_ID_SEQ
--------------------------------------------------------

   CREATE SEQUENCE "USER_ID_SEQ" INCREMENT BY 1 START WITH 1;
--------------------------------------------------------
--  DDL for Table REVATURE_USER
--------------------------------------------------------

  CREATE TABLE "REVATURE_USER" 
   (	"DTYPE" VARCHAR2(31 CHAR) NOT NULL, 
	"USER_ID" NUMBER(10,0) NOT NULL, 
	"BACKUP_PASSWORD" VARCHAR2(255 CHAR), 
	"EMAIL" VARCHAR2(255 CHAR) NOT NULL, 
	"FIRST_NAME" VARCHAR2(255 CHAR) NOT NULL, 
	"HOME_PHONE" VARCHAR2(255 CHAR), 
	"LAST_NAME" VARCHAR2(255 CHAR) NOT NULL, 
	"MIDDLE_NAME" VARCHAR2(255 CHAR), 
	"MOBILE_PHONE" VARCHAR2(255 CHAR), 
	"PASSWORD" VARCHAR2(255 CHAR), 
	"TOKEN" VARCHAR2(255 CHAR), 
	"TITLE" VARCHAR2(255 CHAR), 
	"CLIENT" VARCHAR2(255 CHAR), 
	"COLLEGE" VARCHAR2(255 CHAR), 
	"DEGREE" VARCHAR2(255 CHAR), 
	"END_CLIENT" VARCHAR2(255 CHAR), 
	"FLAG_NOTES" VARCHAR2(4000 CHAR), 
	"FLAG_STATUS" VARCHAR2(255 CHAR), 
	"MAJOR" VARCHAR2(255 CHAR), 
	"MARKETING_STATUS" NUMBER(10,0), 
	"PROFILE_URL" VARCHAR2(255 CHAR), 
	"PROJECT_COMPLETION" VARCHAR2(255 CHAR), 
	"RECRUITER_NAME" VARCHAR2(255 CHAR), 
	"RESOURCE_ID" VARCHAR2(255 CHAR), 
	"TECH_SCREENER" VARCHAR2(255 CHAR), 
	"TRAINING_STATUS" VARCHAR2(255 CHAR), 
	"ROLE_ID" NUMBER(10,0)
   );
--------------------------------------------------------
--  DDL for Table TRAINEE_BATCHES
--------------------------------------------------------

  CREATE TABLE "TRAINEE_BATCHES" 
   (	"TRAINEE_USER_ID" NUMBER(10,0) NOT NULL, 
	"BATCHES" NUMBER(10,0)
   );
--------------------------------------------------------
--  DDL for Table USER_ROLE
--------------------------------------------------------

  CREATE TABLE "USER_ROLE" 
   (	"ROLE_ID" NUMBER(10,0) NOT NULL, 
	"ROLE" VARCHAR2(255 CHAR)
   );

--------------------------------------------------------
--  Constraints for Table REVATURE_USER
--------------------------------------------------------

  ALTER TABLE "REVATURE_USER" ADD CONSTRAINT "EMAIL_UNQ" UNIQUE ("EMAIL");
  
  ALTER TABLE "REVATURE_USER" ADD CONSTRAINT "PK_USER" PRIMARY KEY ("USER_ID");

--------------------------------------------------------
--  Constraints for Table USER_ROLE
--------------------------------------------------------

  ALTER TABLE "USER_ROLE" ADD CONSTRAINT "PK_ROLE" PRIMARY KEY ("ROLE_ID");
  
--------------------------------------------------------
--  Ref Constraints for Table REVATURE_USER
--------------------------------------------------------

  ALTER TABLE "REVATURE_USER" ADD CONSTRAINT "FK_ROLE" FOREIGN KEY ("ROLE_ID")
	  REFERENCES "USER_ROLE" ("ROLE_ID");
--------------------------------------------------------
--  Ref Constraints for Table TRAINEE_BATCHES
--------------------------------------------------------

  ALTER TABLE "TRAINEE_BATCHES" ADD CONSTRAINT "FK_TRAINEE" FOREIGN KEY ("TRAINEE_USER_ID")
	  REFERENCES "REVATURE_USER" ("USER_ID");
