CREATE TABLE services (
    id             INT             NOT NULL,
    name           VARCHAR(100)    NOT NULL,
    price          DOUBLE          NOT NULL,
    description    VARCHAR(1000)   NOT NULL,
    PRIMARY KEY (id)
);