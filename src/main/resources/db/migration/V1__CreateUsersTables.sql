CREATE TABLE users (
    id              INT             NOT NULL,
    first_name      varchar(100)    NOT NULL,
    las_name        varchar(100)    NOT NULL,
    email           varchar(100)    NOT NULL,
    phone_number    varchar(100)    NOT NULL,
    password        varchar(100)    NOT NULL,
    date_time       varchar(100)    NOT NULL,
    is_barber       BOOLEAN         NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
)