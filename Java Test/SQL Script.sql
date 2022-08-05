CREATE DATABASE testejava;

USE testeJava;

CREATE TABLE tbl_usuario (
	id int AUTO_INCREMENT PRIMARY KEY,
    nome varchar(50),
	username varchar(30) UNIQUE NOT NULL,
    pass varchar(30) NOT NULL
);

CREATE TABLE tbl_movimentacao (
	id int AUTO_INCREMENT PRIMARY KEY,
    placa varchar(15) UNIQUE NOT NULL,
    modelo varchar(15),
	data_entrada datetime ,
    data_saida datetime,
    tempo time,
    valor_pago decimal(5,2)
);

CREATE TABLE tbl_valor (
	id int AUTO_INCREMENT PRIMARY KEY,
    valor_primeira_hora decimal(5,2),
    valor_demais_horas decimal(5,2),
    data_fim date
);

INSERT INTO tbl_usuario (nome, username, pass)
VALUES ('admin', 'admin', '123456');

INSERT INTO tbl_valor (valor_primeira_hora, valor_demais_horas)
VALUES ('6.00', '4.00');

CREATE FUNCTION calcular_valor(primeira_hora double, demais_horas double, tempo int)
RETURNS double
RETURN primeira_hora + (demais_horas * tempo - 1);