CREATE SCHEMA IF NOT EXISTS passwordgenerator;

CREATE TABLE "passwordgenerator"."password"
(
    ID SERIAL PRIMARY KEY,
    DATE_CREATED timestamp NOT NULL DEFAULT now(),
    PASSWORD TEXT NOT NULL,
    STRENGTH TEXT NOT NULL
)