DROP TABLE TraineeBatch;
DROP TABLE Trainee;
DROP TABLE Trainer;
DROP TABLE User;

CREATE TABLE TraineeBatch(
trainee_id NUMBER(15) REFERENCES Trainee(trainee_id),
batch_id NUMBER(25),
CONSTRAINT PRIMARY KEY (trainee_id, batch_id)
);

CREATE TABLE Trainee(
trainee_id NUMBER(15) PRIMARY KEY,
resource_id NUMBER(15),
training_status VARCHAR2(40),
phone_number VARCHAR2(20),
skype_id VARCHAR2(150),
profile_url VARCHAR2(150),
recruiter_name VARCHAR2(80),
college VARCHAR2(100),
degree VARCHAR2(100),
major VARCHAR2(100),
tech_screener VARCHAR2(80),
project_completion VARCHAR2(20),
flag_status VARCHAR2(20),
flag_notes VARCHAR2(255),
grades VARCHAR2(255),
notes VARCHAR2(255),
interview_date VARCHAR2(255),
marketing_status VARCHAR2(20),
client VARCHAR2(100),
end_client VARCHAR2(100)
);

CREATE TABLE Trainer(
trainer_id NUMBER(15) PRIMARY KEY,
user_id NUMBER(15)
);

CREATE TABLE User(
user_id NUMBER(15) PRIMARY KEY,
first_name VARCHAR2(80),
middle_name VARCHAR2(80),
last_name VARCHAR2(80),
email VARCHAR2(150),
password VARCHAR2(100),
backup_password VARCHAR2(100),
role VARCHAR2(30)
);

CREATE TABLE roles(
role_id NUMBER(10) PRIMARY KEY,
role VARCHAR2(30)
);