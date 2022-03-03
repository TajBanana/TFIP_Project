DROP DATABASE IF EXISTS sudoku ;

CREATE DATABASE sudoku;

CREATE TABLE userlist (
    username VARCHAR(24),
    password VARCHAR(64),
    PRIMARY KEY (username)
);

CREATE TABLE seed (
    seed VARCHAR(81),
    difficulty VARCHAR(12),
    PRIMARY KEY (seed)
)