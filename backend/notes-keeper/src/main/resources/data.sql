INSERT INTO author VALUES
(1, 'Richard Smith'),
(2, 'John Ferry'),
(3, 'Simon Duff');

INSERT INTO note VALUES
(1, 'Welcome', 'Hello World!', '2019-08-18 20:59:59'),
(2, 'Test', 'This was a test note.', '2019-08-18 20:59:59'),
(3, 'Works great', 'This note tool is prett neat.', '2019-08-18 20:59:59');

INSERT INTO note_author VALUES
(1, 1),
(1, 3),
(2, 1),
(2, 2);