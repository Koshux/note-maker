DROP TABLE IF EXISTS note, author, note_author;

CREATE TABLE author (
    id      INT             NOT NULL PRIMARY KEY,
    title   VARCHAR(100)    NOT NULL
);

CREATE TABLE note (
    id              INT             NOT NULL PRIMARY KEY,
    title           VARCHAR(20)     NOT NULL,
    description     VARCHAR(100)    NOT NULL
);

CREATE TABLE note_author (
    note_id         INT     NOT NULL,
    author_id       INT     NOT NULL,

    PRIMARY KEY(note_id, author_id),
    CONSTRAINT fk_note_author_note FOREIGN KEY (note_id) REFERENCES note (id),
    CONSTRAINT fk_note_author_author FOREIGN KEY (author_id) REFERENCES author (id) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO author VALUES
(1, 'Richard Smith'),
(2, 'John Ferry'),
(3, 'Simon Duff');

INSERT INTO note VALUES
(1, 'Welcome', 'Hello World!'),
(2, 'Test', 'This was a test note.'),
(3, 'Works great', 'This note tool is prett neat.');

INSERT INTO note_author VALUES
(1, 1),
(1, 3),
(2, 1),
(2, 2);