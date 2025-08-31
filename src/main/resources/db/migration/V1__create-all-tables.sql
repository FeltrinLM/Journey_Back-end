-- ========================
-- V1 - Criação das tabelas
-- ========================

-- ===== USUARIO =====
CREATE TABLE "usuario" (
                           "usuarioId" SERIAL PRIMARY KEY,
                           "nome" VARCHAR(100) NOT NULL UNIQUE,
                           "senha" VARCHAR(100) NOT NULL,
                           "tipo"  VARCHAR(20)  NOT NULL
);

-- ===== COLECAO =====
CREATE TABLE "colecao" (
                           "colecaoId"  SERIAL PRIMARY KEY,
                           "nome"       VARCHAR(100) NOT NULL,
                           "dataInicio" DATE NOT NULL,
                           "dataFim"    DATE
);

-- ===== ESTAMPA =====
CREATE TABLE "estampa" (
                           "estampaId" SERIAL PRIMARY KEY,
                           "nome"      VARCHAR(100) NOT NULL,
                           "quantidade" INTEGER NOT NULL,
                           "colecaoId"  INTEGER NOT NULL,
                           CONSTRAINT fk_estampa_colecao FOREIGN KEY ("colecaoId") REFERENCES "colecao"("colecaoId")
);

-- ===== PECA =====
CREATE TABLE "peca" (
                        "pecaId"    SERIAL PRIMARY KEY,
                        "tipo"      VARCHAR(50) NOT NULL,
                        "tamanho"   VARCHAR(50) NOT NULL,
                        "cor"       VARCHAR(50) NOT NULL,
                        "quantidade" INTEGER NOT NULL
);

-- ===== ADESIVO =====
CREATE TABLE "adesivo" (
                           "adesivoId"     SERIAL PRIMARY KEY,
                           "adesivoModelo" VARCHAR(100) NOT NULL,
                           "cromatico"     BOOLEAN NOT NULL
);

-- ===== CHAVEIRO =====
CREATE TABLE "chaveiro" (
                            "chaveiroId"     SERIAL PRIMARY KEY,
                            "chaveiroModelo" VARCHAR(100) NOT NULL,
                            "colecaoId"      INTEGER NOT NULL,
                            CONSTRAINT fk_chaveiro_colecao FOREIGN KEY ("colecaoId") REFERENCES "colecao"("colecaoId")
);

-- ===== HISTORICO ALTERACAO =====
CREATE TABLE "historicoAlteracao" (
                                      "id"           BIGSERIAL PRIMARY KEY, -- já cria como BIGINT
                                      "entidade"     VARCHAR(100) NOT NULL,
                                      "entidadeId"   INTEGER NOT NULL,
                                      "campoAlterado" VARCHAR(100) NOT NULL,
                                      "valorAntigo"  TEXT,
                                      "valorNovo"    TEXT,
                                      "dataHora"     TIMESTAMP NOT NULL,
                                      "usuarioId"    INTEGER NOT NULL,
                                      CONSTRAINT fk_hist_usuario FOREIGN KEY ("usuarioId") REFERENCES "usuario"("usuarioId")
);
