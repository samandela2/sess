DROP TABLE IF EXISTS person;
CREATE TABLE person
(
     PERSON_ID     INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
     USERNAME   VARCHAR(256)    NOT NULL,
     PASSWORD VARCHAR(256)  NOT NULL,
     ROLE   VARCHAR(256)    NOT NULL
);


DROP TABLE IF EXISTS person1;
CREATE TABLE person1
(
     PERSON_ID     INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
     USERNAME   VARCHAR(256)    NOT NULL,
     EMAIL VARCHAR(256)  NOT NULL,
     ROLE   VARCHAR(256)    NOT NULL
);



DROP TABLE IF EXISTS client;
CREATE TABLE client
(
    CLIENT_ID   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    NAME    VARCHAR(256)    NOT NULL,
    LOCATION    VARCHAR(256) 
);

DROP TABLE IF EXISTS task;
CREATE TABLE task 
(
    TASK_ID     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    START_TIME   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    END_TIME     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    OWNER  INT NOT NULL,
    CLIENT BIGINT,
    LOCATION VARCHAR(256),
    TYPE   VARCHAR(256) NOT NULL,
    DESCRIPTION     TEXT,

    FOREIGN KEY (OWNER) REFERENCES person(PERSON_ID),
    FOREIGN KEY (CLIENT) REFERENCES client(CLIENT_ID)
);
