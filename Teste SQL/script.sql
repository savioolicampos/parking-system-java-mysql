CREATE DATABASE testesql;

USE testesql;

CREATE TABLE lojas (
	loj_prod int(8) PRIMARY KEY,
    desc_loj varchar(40)
);

CREATE TABLE produtos (
	cod_prod int(8) PRIMARY KEY,
    loj_prod int(8),
    desc_prod varchar(40),
    dt_inclu_prod date,
    preco_prod decimal(8,3),
    FOREIGN KEY fk_loja_produtos (loj_prod) REFERENCES lojas (loj_prod)
);

CREATE TABLE estoque (
	cod_prod int(8),
    loj_prod int(8),
    qtd_prod decimal(15,3),
    FOREIGN KEY fk_produto_estoque (cod_prod) REFERENCES produtos (cod_prod),
    FOREIGN KEY fk_loja_estoque (loj_prod) REFERENCES lojas (loj_prod)
);

INSERT INTO lojas (loj_prod, desc_loj)
VALUES (1, 'Loja 01');

INSERT INTO lojas (loj_prod, desc_loj)
VALUES (2, 'Loja 02');

INSERT INTO produtos (cod_prod, loj_prod, desc_prod, dt_inclu_prod, preco_prod)
VALUES ('170', '2', 'Leite Condensado Mococa', '2010-12-30', '45.40');

INSERT INTO produtos (cod_prod, loj_prod, desc_prod, dt_inclu_prod, preco_prod)
VALUES ('171', '1', 'Pipoca', '2022-06-24', '4.90');

INSERT INTO estoque (cod_prod, loj_prod, qtd_prod)
VALUES ('171', '1', '10');

UPDATE produtos
SET preco_prod = 95.40
WHERE cod_prod = 170 AND loj_prod = 2;

SELECT * FROM produtos
WHERE loj_prod IN (1, 2);

SELECT MAX(dt_inclu_prod) AS maior_data
FROM produtos;

SELECT MIN(dt_inclu_prod) AS menor_data
FROM produtos;

SELECT COUNT(cod_prod) AS quantidade
FROM produtos;

SELECT * FROM produtos
WHERE desc_prod LIKE 'L%';

SELECT loj_prod, SUM(preco_prod) AS soma
FROM produtos
GROUP BY loj_prod;

SELECT loj_prod, SUM(preco_prod) AS soma
FROM produtos
WHERE preco_prod > 100000.00
GROUP BY loj_prod;

SELECT
	lojas.loj_prod,
    lojas.desc_loj,
    produtos.cod_prod,
    produtos.desc_prod,
    produtos.preco_prod,
    estoque.qtd_prod
FROM lojas
INNER JOIN produtos ON lojas.loj_prod = produtos.loj_prod
INNER JOIN estoque ON produtos.cod_prod = estoque.cod_prod
WHERE lojas.loj_prod = 1;

SELECT
	produtos.cod_prod,
    produtos.desc_prod,
    estoque.cod_prod
FROM produtos LEFT JOIN estoque ON produtos.cod_prod = estoque.cod_prod;

SELECT
	produtos.cod_prod,
    produtos.desc_prod,
    estoque.cod_prod
FROM produtos RIGHT JOIN estoque ON produtos.cod_prod = estoque.cod_prod;