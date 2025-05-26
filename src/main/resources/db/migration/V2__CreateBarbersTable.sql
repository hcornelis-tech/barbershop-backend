CREATE TABLE barbers (
     user_id        INT         PRIMARY KEY,
     is_admin       BOOLEAN     NOT NULL,
     hire_date      varchar(50) NOT NULL,
     FOREIGN KEY (user_id) REFERENCES users (id)
)