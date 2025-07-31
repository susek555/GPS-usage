-- File: database/schema.sql

CREATE TABLE routes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    numberofpoints INT NOT NULL,
    time DATE NOT NULL
);

CREATE TABLE points (
    id SERIAL PRIMARY KEY,
    routeid BIGINT NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    time INT NOT NULL,
    FOREIGN KEY (routeid) REFERENCES routes(id) ON DELETE CASCADE
);
