INSERT INTO
	PROFILES (NAME)
VALUES
	('Admin'),
	('Guest');

INSERT INTO
	USERS (MAIL, USERNAME, PASSWORD)
VALUES
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