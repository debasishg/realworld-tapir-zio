INSERT INTO users
VALUES (10, 'jake@example.com', 'jake', 'I work at statefarm', 'https://i.stack.imgur.com/xHWG8.jpg');

INSERT INTO articles
VALUES ('how-to-train-your-dragon', 'How to train your dragon', 'Ever wonder how?', 'It takes a Jacobian',
        1455765776637, 1455767315824, 10),
       ('how-to-train-your-dragon-2', 'How to train your dragon 2', 'So toothless', 'Its a dragon', 1455765776637,
        1455767315824, 10),
       ('how-to-train-your-dragon-3', 'How to train your dragon 3', 'The tagless one', 'Its not a dragon', 1455765776637,
        1455767315824, 10);

INSERT INTO tags_articles
VALUES ('dragons', 'how-to-train-your-dragon'),
       ('training', 'how-to-train-your-dragon'),
       ('dragons', 'how-to-train-your-dragon-2'),
       ('training', 'how-to-train-your-dragon-2');
