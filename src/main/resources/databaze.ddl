CREATE DATABASE ChatDB;

USE ChatDB;

CREATE TABLE message (
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL
);

INSERT INTO messages (content) VALUES  ("first msg"), ("second msg")

