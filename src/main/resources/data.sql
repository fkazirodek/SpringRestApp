CREATE TABLE IF NOT EXISTS author
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    version   BIGINT,

    firstname VARCHAR(255),
    lastname  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS quote
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    version   BIGINT,

    content   VARCHAR(1000),
    author_id BIGINT,

    FOREIGN KEY (author_id) REFERENCES author (id)
);

INSERT INTO author (id, version, firstname, lastname)
VALUES (1, 1, 'Albert', 'Einstein');

INSERT
INTO quote (id, version, content, author_id)
VALUES (1,
        1,
        'Wyobraźnia  jest ważniejsza od wiedzy. Wiedza jest ograniczona, a wyobraźnia otacza cały świat.',
        SELECT id FROM author where firstname = 'Albert' AND lastname = 'Einstein'
        );