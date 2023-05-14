INSERT INTO lecture (topic, path_number, lecture_number, date_time, capacity) VALUES
('Distributed build at scale', 1, 1, '2023-06-01 10:00:00.000', 5),
('Cloud-Based application development', 1, 2, '2023-06-01 12:00:00.000', 5),
('Data design and curation in era of cloud computing', 1, 3, '2023-06-01 14:00:00.000', 5),
('Rhe future of Artificial Intelligence', 2, 1, '2023-06-01 10:00:00.000', 5),
('AI Risks', 2, 2, '2023-06-01 12:00:00.000', 5),
('Will AI take out jobs?', 2, 3, '2023-06-01 14:00:00.000', 5),
('Evolution of Digital Identity', 3, 1, '2023-06-01 10:00:00.000', 5),
('Semiconductors - Why so important?', 3, 2, '2023-06-01 12:00:00.000', 5),
('Role of technology in remote workspace', 3, 3, '2023-06-01 14:00:00.000', 5);

INSERT INTO user_data (login, email) VALUES
('adam123', 'adamhackerman@email.com'),
('agnieszka_programistka', 'agnieszka.programistka@email.com'),
('98kamil', 'kamil_98@email.com'),
('patrycja653', 'patrycja653@email.com'),
('ada_sklada', 'ada997@email.com'),
('aneta_325', 'aneta@email.com');

INSERT INTO lecture_reservation (user_id, lecture_id) VALUES
(1, 1),
(3, 5),
(1, 8),
(2, 8),
(3, 8),
(4, 8),
(5, 8);