DELETE FROM exercise_stats;
DELETE FROM assessment_stats;
DELETE FROM exercise;
DELETE FROM topic;
DELETE FROM template;
DELETE FROM assessment;
DELETE FROM course;

INSERT INTO course VALUES (1, 'Alphabet'), (2, 'Number');

INSERT INTO assessment (id, threshold, course_id) VALUES (1, 30.0, 1), (2, 30.0, 2);

INSERT INTO template (id, content_type, content_prefix, content_style, name) VALUES (1, 'ALPHABET', 'Identify', 'BOLD', 'bold alphabet'), (2, 'NUMBER', 'Identify', 'BOLD', 'bold number');

INSERT INTO topic (id, name, course_id) VALUES (1, 'Capslock', 1), (2, 'Even', 2);

INSERT INTO exercise (id, content, content_type, topic_id, template_id) VALUES (1, 'A', 'ALPHABET', 1, 1), (2, '3', 'NUMBER', 1, 2);

INSERT INTO assessment_stats (id, score, user_id, assessment_id) VALUES (1, 40.0, 4, 1), (2, 20.0, 4, 2);

INSERT INTO exercise_stats (id, status, user_id, exercise_id) VALUES (1, 1, 4, 1), (2, 0, 4, 2);



