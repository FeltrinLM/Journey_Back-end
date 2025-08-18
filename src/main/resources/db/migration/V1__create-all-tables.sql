CREATE TABLE usuario (
                         usuarioId SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         senha VARCHAR(100) NOT NULL,
                         tipo VARCHAR(20) NOT NULL
);

CREATE TABLE colecao (
                         colecaoId SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         dataInicio DATE NOT NULL,
                         dataFim DATE
);

CREATE TABLE estampa (
                         estampaId SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         quantidade INTEGER NOT NULL,
                         colecaoId INTEGER NOT NULL,
                         FOREIGN KEY (colecaoId) REFERENCES colecao(colecaoId)
);

CREATE TABLE peca (
                      pecaId SERIAL PRIMARY KEY,
                      tipo VARCHAR(50) NOT NULL,
                      tamanho VARCHAR(50) NOT NULL,
                      cor VARCHAR(50) NOT NULL,
                      quantidade INTEGER NOT NULL
);

CREATE TABLE adesivo (
                         adesivoId SERIAL PRIMARY KEY,
                         adesivo_modelo VARCHAR(100) NOT NULL,
                         cromatico BOOLEAN NOT NULL
);

CREATE TABLE chaveiro (
                          chaveiroId SERIAL PRIMARY KEY,
                          chaveiroModelo VARCHAR(100) NOT NULL,
                          colecaoId INTEGER NOT NULL,
                          FOREIGN KEY (colecaoId) REFERENCES colecao(colecaoId)
);

CREATE TABLE historico_alteracao (
                                     id SERIAL PRIMARY KEY,
                                     entidade VARCHAR(100) NOT NULL,
                                     entidadeId INTEGER NOT NULL,
                                     campoAlterado VARCHAR(100) NOT NULL,
                                     valorAntigo TEXT,
                                     valorNovo TEXT,
                                     dataHora TIMESTAMP NOT NULL,
                                     usuarioId INTEGER NOT NULL,
                                     FOREIGN KEY (usuarioId) REFERENCES usuario(usuarioId)
);
