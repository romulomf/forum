INSERT INTO
	USERS (MAIL, USERNAME, PASSWORD)
VALUES
	('romuloflores@gmail.com', 'romulo', '123456');

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