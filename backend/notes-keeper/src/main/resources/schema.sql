CREATE DATABASE IF NOT EXISTS notemaker;

DROP TABLE IF EXISTS note, author, note_author;

CREATE TABLE author (
    id      INT             NOT NULL PRIMARY KEY,
    title   VARCHAR(100)    NOT NULL
);

CREATE TABLE note (
    id              INT             NOT NULL PRIMARY KEY,
    title           VARCHAR(20)     NOT NULL,
    description     VARCHAR(100)    NOT NULL,
    creation_date    TIMESTAMP       NOT NULL
);

CREATE TABLE note_author (
    note_id         INT     NOT NULL,
    author_id       INT     NOT NULL,

    PRIMARY KEY(note_id, author_id),
    CONSTRAINT fk_note_author_note FOREIGN KEY (note_id) REFERENCES note (id),
    CONSTRAINT fk_note_author_author FOREIGN KEY (author_id) REFERENCES author (id) ON UPDATE CASCADE ON DELETE CASCADE
);
