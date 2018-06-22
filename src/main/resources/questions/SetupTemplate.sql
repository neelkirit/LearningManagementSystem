INSERT INTO course (name, description, icon)
VALUES ( 'Alphabets', 'Learn to identify a set of letters or symbols in a fixed order used to represent the basic set of speech sounds of a language, especially the set of letters from A to Z.', 'logo-angular' );

INSERT INTO course (name, description, icon)
VALUES ( 'Numbers', 'Learn to identify an arithmetical value, expressed by a word, symbol, or figure, representing a particular quantity and used in counting and making calculations', 'keypad' );

INSERT INTO course (name, description, icon)
VALUES ( 'Words', 'Learn to identify a single distinct meaningful element of speech or writing, used with others (or sometimes alone) to form a sentence and typically shown with a space on either side when written or printed', 'logo-wordpress' );

INSERT INTO course (name, description, icon)
VALUES ( 'Sentences', 'Learn to identify a set of words that is complete in itself, typically containing a subject and predicate, conveying a statement, question, exclamation, or command, and consisting of a main clause and sometimes one or more subordinate clauses', 'clipboard' );

INSERT INTO course (name, description, icon)
VALUES ( 'Radio Buttons', 'Learn to select an icon representing one of a set of options, only one of which can be selected at any time.', 'radio-button-on' );

INSERT INTO course (name, description, icon)
VALUES ( 'Combo Boxes', 'Learn to select a type of dialogue box containing a combination of controls, such as sliders, text boxes, and drop-down lists.', 'arrow-dropdown-circle' );

INSERT INTO course (name, description, icon)
VALUES ( 'Check Boxes', 'Learn to select a small box on a form into which a tick or other mark is entered as the response to a question', 'checkbox' );

INSERT INTO course (name, description, icon)
VALUES ( 'Forms', 'Use all that you have practiced and fill the form', 'list-box' );


INSERT INTO assessment (threshold, course_id) VALUES ('75', (select id from course where name='Alphabets'));

INSERT INTO assessment (threshold, course_id) VALUES ('75', (select id from course where name='Numbers'));

INSERT INTO assessment (threshold, course_id) VALUES ('75', (select id from course where name='Words'));

INSERT INTO assessment (threshold, course_id) VALUES ('75', (select id from course where name='Sentences'));

INSERT INTO assessment (threshold, course_id) VALUES ('75', (select id from course where name='Radio Buttons'));

INSERT INTO assessment (threshold, course_id) VALUES ('75', (select id from course where name='Combo Boxes'));

INSERT INTO assessment (threshold, course_id) VALUES ('75', (select id from course where name='Check Boxes'));


INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'ALPHABET', 'Identify and type the alphabet shown', 'BOLD', 'ALPH_BOLD' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'ALPHABET', 'Identify and type the alphabet shown', 'ITALICS', 'ALPH_ITALICS' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'ALPHABET', 'Identify and type the alphabet shown', 'BOLD_ITALICS', 'ALPH_BOLD_ITALICS' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'ALPHABET', 'Identify and type the alphabet shown', 'CAPITALS', 'ALPH_CAPITALS' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'NUMBER', 'Identify and type the number shown', 'BOLD', 'NUMB_BOLD' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'NUMBER', 'Identify and type the number shown', 'ITALICS', 'NUMB_ITALICS' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'NUMBER', 'Identify and type the number shown', 'BOLD_ITALICS', 'NUMB_BOLD_ITALICS' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'WORD', 'Identify and type the word shown', 'BOLD', 'WORD_BOLD' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'WORD', 'Identify and type the word shown', 'ITALICS', 'WORD_ITALICS' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'WORD', 'Identify and type the word shown', 'BOLD_ITALICS', 'WORD_BOLD_ITALICS' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'WORD', 'Identify and type the word shown', 'CAPITALS', 'WORD_CAPITALS' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'SENTENCE', 'Identify and type the sentence shown', 'BOLD', 'SENT_BOLD' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'SENTENCE', 'Identify and type the sentence shown', 'ITALICS', 'SENT_ITALICS' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'SENTENCE', 'Identify and type the sentence shown', 'BOLD_ITALICS', 'SENT_BOLD_ITALICS' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'SENTENCE', 'Identify and type the sentence shown', 'CAPITALS', 'SENT_CAPITALS' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'COMBOBOX', 'Identify __ANSWER__ and select from options', 'NORMAL', 'COMBO_NORMAL' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'RADIOBUTTON', 'Identify __ANSWER__ and select from options', 'NORMAL', 'RADIO_NORMAL' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'CHECKBOX', 'Identify __ANSWER__ and select from options', 'NORMAL', 'CHECK_NORMAL' );

INSERT INTO template (content_type, content_prefix, content_style, name) VALUES ( 'FORM', 'Fill all the fields in the form', 'NORMAL', 'FORM_NORMAL' );
