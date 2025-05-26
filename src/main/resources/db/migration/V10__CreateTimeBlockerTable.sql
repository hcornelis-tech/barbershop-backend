CREATE TABLE timeblocker (
      id               INT             NOT NULL,
      barber_id        INT             NOT NULL,
      start_date       VARCHAR(50)     NULL,
      end_date         VARCHAR(50)     NULL,
      start_time       VARCHAR(50)     NULL,
      end_time         VARCHAR(50)     NULL,
      all_day          BOOLEAN         NOT NULL,
      PRIMARY KEY (id),
      FOREIGN KEY (barber_id) REFERENCES barbers (id)
);