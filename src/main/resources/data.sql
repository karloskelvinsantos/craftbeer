DROP TABLE IF EXISTS beer;

CREATE TABLE BEER (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(255),
    ingredients VARCHAR(255),
    alcohol_content VARCHAR(255),
    price NUMERIC,
    category VARCHAR(50)
);

INSERT INTO BEER (id, name, ingredients, alcohol_content, price, category)
VALUES (1, 'beer', 'Malte, cevada', '4%', 4.6, 'IPA');