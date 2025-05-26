CREATE TABLE payments (
      id               VARCHAR(225)    NOT NULL,
      appointment_id   INT             NOT NULL,
      amount           DOUBLE          NOT NULL,
      date_time        VARCHAR(50)     NOT NULL,
      method           VARCHAR(50)     NOT NULL,
      status           VARCHAR(50)     NOT NULL,
      PRIMARY KEY (id),
      FOREIGN KEY (appointment_id) REFERENCES appointments (id)
);

ALTER TABLE appointments ADD COLUMN booked_at varchar(100)       NOT NULL;
ALTER TABLE appointments ADD COLUMN payment varchar(100)         NOT NULL;
ALTER TABLE appointments ADD COLUMN booked_online BOOLEAN        NOT NULL;