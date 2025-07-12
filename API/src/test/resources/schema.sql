-- File: database/schema.sql

CREATE TABLE routes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    numberOfPoints BIGINT NOT NULL,
    time DATE NOT NULL
);

CREATE TABLE points (
    id SERIAL PRIMARY KEY,
    routeId BIGINT NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    time DATE NOT NULL,
    FOREIGN KEY (routeId) REFERENCES routes(id) ON DELETE CASCADE
);