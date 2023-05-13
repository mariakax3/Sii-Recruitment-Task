CREATE TABLE user_data
(
    user_id SERIAL      NOT NULL,
    login VARCHAR(32)   NOT NULL,
    email VARCHAR(32)   NOT NULL,
    lecture_id INT,
    PRIMARY KEY (user_id),
    UNIQUE (login),
    UNIQUE (email)
);

CREATE TABLE lecture
(
    lecture_id SERIAL                   NOT NULL,
    topic VARCHAR(64)                   NOT NULL,
    path_number INT                     NOT NULL,
    lecture_number INT                  NOT NULL,
    date_time TIMESTAMP WITH TIME ZONE  NOT NULL,
    capacity INT                        NOT NULL,
    PRIMARY KEY (lecture_id)
);

CREATE TABLE lecture_reservation
(
    lecture_reservation_id SERIAL   NOT NULL,
    user_id INT                     NOT NULL,
    lecture_id INT                  NOT NULL,
    PRIMARY KEY (lecture_reservation_id),
    CONSTRAINT fk_lecture_reservation_user
        FOREIGN KEY (user_id)
            REFERENCES user_data(user_id),
    CONSTRAINT fk_lecture_reservation_lecture
        FOREIGN KEY (lecture_id)
            REFERENCES lecture(lecture_id)
);