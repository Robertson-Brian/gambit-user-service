--------------------------------------------------------
--  DDL for Sequence TRAINEE_ID_SEQUENCE
--------------------------------------------------------

   CREATE SEQUENCE  "HYDRATRAINEE"."TRAINEE_ID_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1;
--------------------------------------------------------
--  DDL for Sequence TRAINER_ID_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "HYDRATRAINEE"."TRAINER_ID_SEQ"  MINVALUE 3 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1;
--------------------------------------------------------
--  DDL for Sequence TRAINER_ID_SEQUENCE
--------------------------------------------------------

   CREATE SEQUENCE  "HYDRATRAINEE"."TRAINER_ID_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1;
--------------------------------------------------------
--  DDL for Sequence USER_ID_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "HYDRATRAINEE"."USER_ID_SEQ"  MINVALUE 3 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1;
--------------------------------------------------------
--  DDL for Sequence USER_ID_SEQUENCE
--------------------------------------------------------

   CREATE SEQUENCE  "HYDRATRAINEE"."USER_ID_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1;
--------------------------------------------------------
--  DDL for Table HYDRA_TRAINEE
--------------------------------------------------------

  CREATE TABLE "HYDRATRAINEE"."HYDRA_TRAINEE"(	
    "BATCH_ID" NUMBER(10,0), 
	"RESOURCE_ID" NUMBER(10,0), 
	"TRAINING_STATUS" VARCHAR2(40), 
	"PHONE_NUMBER" VARCHAR2(20), 
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
	"GRADES" VARCHAR2(256), 
	"NOTES" VARCHAR2(256), 
	"PANEL_INTERVIEWS" VARCHAR2(256), 
	"MARKETING_STATUS" VARCHAR2(50), 
	"CLIENT" VARCHAR2(200), 
	"END_CLIENT" VARCHAR2(200), 
	"TRAINEE_ID" NUMBER(10,0), 
	"USER_ID" NUMBER(10,0)
   );
--------------------------------------------------------
--  DDL for Table HYDRA_USER
--------------------------------------------------------

  CREATE TABLE "HYDRATRAINEE"."HYDRA_USER"(	
    "USER_ID" NUMBER(10,0), 
	"PASSWORD" VARCHAR2(30), 
	"BACKUP_PASSWORD" VARCHAR2(30), 
	"EMAIL" VARCHAR2(30), 
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

  CREATE TABLE "HYDRATRAINEE"."ROLES" (	
    "ROLE_ID" NUMBER(10,0), 
	"ROLE" VARCHAR2(30)
   );
--------------------------------------------------------
--  DDL for Table TRAINEE_BATCH
--------------------------------------------------------

  CREATE TABLE "HYDRATRAINEE"."TRAINEE_BATCH"(	
    "TRAINEE_ID" NUMBER(10,0), 
	"BATCH_ID" NUMBER(10,0)
   );
--------------------------------------------------------
--  DDL for Table TRAINER
--------------------------------------------------------

  CREATE TABLE "HYDRATRAINEE"."TRAINER"(	
    "TRAINER_ID" NUMBER(10,0), 
	"TITLE" VARCHAR2(30), 
	"USER_ID" NUMBER(10,0)
   );
--------------------------------------------------------
--  Constraints for Table TRAINEE_BATCH
--------------------------------------------------------

  ALTER TABLE "HYDRATRAINEE"."TRAINEE_BATCH" ADD CONSTRAINT "TRAINEE_BATCH_PRIMARY_KEY" PRIMARY KEY ("BATCH_ID", "TRAINEE_ID");
--------------------------------------------------------
--  Constraints for Table TRAINER
--------------------------------------------------------

  ALTER TABLE "HYDRATRAINEE"."TRAINER" ADD PRIMARY KEY ("TRAINER_ID");
  ALTER TABLE "HYDRATRAINEE"."TRAINER" MODIFY ("TITLE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table HYDRA_USER
--------------------------------------------------------

  ALTER TABLE "HYDRATRAINEE"."HYDRA_USER" ADD UNIQUE ("EMAIL");
  ALTER TABLE "HYDRATRAINEE"."HYDRA_USER" ADD PRIMARY KEY ("USER_ID");
  ALTER TABLE "HYDRATRAINEE"."HYDRA_USER" MODIFY ("EMAIL" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ROLES
--------------------------------------------------------

  ALTER TABLE "HYDRATRAINEE"."ROLES" ADD PRIMARY KEY ("ROLE_ID");
  ALTER TABLE "HYDRATRAINEE"."ROLES" MODIFY ("ROLE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table HYDRA_TRAINEE
--------------------------------------------------------

  ALTER TABLE "HYDRATRAINEE"."HYDRA_TRAINEE" ADD PRIMARY KEY ("TRAINEE_ID");
--------------------------------------------------------
--  Ref Constraints for Table HYDRA_TRAINEE
--------------------------------------------------------

  ALTER TABLE "HYDRATRAINEE"."HYDRA_TRAINEE" ADD FOREIGN KEY ("USER_ID")
	  REFERENCES "HYDRATRAINEE"."HYDRA_USER" ("USER_ID");
--------------------------------------------------------
--  Ref Constraints for Table TRAINEE_BATCH
--------------------------------------------------------

  ALTER TABLE "HYDRATRAINEE"."TRAINEE_BATCH" ADD FOREIGN KEY ("TRAINEE_ID")
	  REFERENCES "HYDRATRAINEE"."HYDRA_TRAINEE" ("TRAINEE_ID");
--------------------------------------------------------
--  Ref Constraints for Table TRAINER
--------------------------------------------------------

  ALTER TABLE "HYDRATRAINEE"."TRAINER" ADD FOREIGN KEY ("USER_ID")
	  REFERENCES "HYDRATRAINEE"."HYDRA_USER" ("USER_ID");
