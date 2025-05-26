CREATE TABLE schedule (
    id               INT             NOT NULL,
    start_time       VARCHAR(50)     NOT NULL,
    end_time         VARCHAR(50)     NOT NULL,
    service_duration INT             NOT NULL,
    PRIMARY KEY (id)
);