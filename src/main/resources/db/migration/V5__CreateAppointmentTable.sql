CREATE TABLE appointments (
    id             INT             NOT NULL,
    user_id        INT             NOT NULL,
    barber_id      INT             NOT NULL,
    service_id     INT             NOT NULL,
    date           varchar(50)     NOT NULL,
    time           varchar(50)     NOT NULL,
    status         varchar(50)     NOT NULL,
    total_cost     DOUBLE          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (barber_id) REFERENCES barbers (user_id),
    FOREIGN KEY (service_id) REFERENCES services (id)
);