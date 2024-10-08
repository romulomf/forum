-- estrutura do banco de dados

CREATE TABLE PROFILES
(
	ID BIGINT NOT NULL AUTO_INCREMENT,
	NAME NVARCHAR(255) NOT NULL,
	CONSTRAINT PK_PROFILE_ID PRIMARY KEY(ID),
	CONSTRAINT UN_PROFILE_NAME UNIQUE(NAME)
);

CREATE TABLE USERS
(
	ID BIGINT NOT NULL AUTO_INCREMENT,
	MAIL VARCHAR(255) NOT NULL,
	USERNAME NVARCHAR(255) NOT NULL,
	PASSWORD NVARCHAR(255) NOT NULL,
	CONSTRAINT PK_USER_ID PRIMARY KEY(ID),
	CONSTRAINT UN_USERNAME UNIQUE(USERNAME)
);

CREATE TABLE USERS_PROFILES
(
	ID_USER BIGINT NOT NULL,
	ID_PROFILE BIGINT NOT NULL,
	CONSTRAINT FK_USER_ID FOREIGN KEY(ID_USER) REFERENCES USERS(ID),
	CONSTRAINT FK_PROFILE_ID FOREIGN KEY(ID_PROFILE) REFERENCES PROFILES(ID),
	CONSTRAINT PK_USER_PROFILE PRIMARY KEY(ID_USER, ID_PROFILE)
);

CREATE TABLE COURSES
(
	ID BIGINT NOT NULL AUTO_INCREMENT,
	NAME VARCHAR(255),
	CATEGORY NVARCHAR(255),
	CONSTRAINT PK_COURSE_ID PRIMARY KEY(ID)
);

CREATE TABLE TOPICS
(
	ID BIGINT NOT NULL AUTO_INCREMENT,
	TITLE NVARCHAR(255) NOT NULL,
	MESSAGE NVARCHAR(1000000000) NOT NULL,
	ID_COURSE BIGINT NOT NULL,
	CREATED DATETIME,
	STATUS NVARCHAR(255) NOT NULL,
	ID_AUTHOR BIGINT NOT NULL,
	CONSTRAINT PK_TOPIC_ID PRIMARY KEY(ID),
	CONSTRAINT FK_TOPIC_COURSE_ID FOREIGN KEY(ID_COURSE) REFERENCES COURSES(ID),
	CONSTRAINT FK_TOPIC_AUTHOR_ID FOREIGN KEY(ID_AUTHOR) REFERENCES USERS(ID)
);

CREATE TABLE ANSWERS
(
	ID BIGINT NOT NULL AUTO_INCREMENT,
	MESSAGE NVARCHAR(1000000000) NOT NULL,
	ID_TOPIC BIGINT NOT NULL,
	ID_USER BIGINT NOT NULL,
	CREATED DATETIME NOT NULL,
	SOLUTION BOOLEAN,
	CONSTRAINT PK_ANSWER_ID PRIMARY KEY(ID),
	CONSTRAINT FK_ANSWER_TOPIC_ID FOREIGN KEY(ID_TOPIC) REFERENCES TOPICS(ID),
	CONSTRAINT FK_ANSWER_USER_ID FOREIGN KEY(ID_USER) REFERENCES USERS(ID)
);

-- inserção de dados iniciais

INSERT INTO
	PROFILES (NAME)
VALUES
	-- O padrão do spring security é prefixar o nome do perfil com ROLE_ seguido do nome (tudo escrito em maiúsculo).
	('ROLE_ADMIN'),
	('ROLE_GUEST');

INSERT INTO
	USERS (MAIL, USERNAME, PASSWORD)
VALUES
	-- As senhas usadas nos exemplos são as mesmas para todos os usuários: 123456
	('foo@example.org', 'foo', '$2a$10$cl.WCmEb3PqliAX1HBpOI.lUxFBKq1FKuRU5Uuw.MZMJWNA3WmdGC'),
	('bar@example.org', 'bar', '$2a$10$cl.WCmEb3PqliAX1HBpOI.lUxFBKq1FKuRU5Uuw.MZMJWNA3WmdGC');

INSERT INTO
	USERS_PROFILES (ID_USER, ID_PROFILE)
VALUES
	(1, 1), (2, 2);

INSERT INTO
	COURSES (NAME, CATEGORY)
VALUES
	('Typescript', 'Backend'),
	('Node', 'Backend'),
	('CSS', 'Frontend'),
	('HTML', 'Frontend');

INSERT INTO
	TOPICS (TITLE, MESSAGE, ID_COURSE, CREATED, STATUS, ID_AUTHOR)
VALUES
	('Integração com VSCode', 'Como fazer para o typescript do yarn ser usado no VSCode?', 1, CURRENT_TIMESTAMP, 'UNANSWERED', 1),
	('Centralização Vertical', 'Como centralizar um elemento verticalmente?', 3, CURRENT_TIMESTAMP, 'UNANSWERED', 1),
	('Atributos sem valor', 'Diferente do XHMTL, no HTML 5 é possível ter atributos sem valor?', 4, CURRENT_TIMESTAMP, 'UNANSWERED', 1);