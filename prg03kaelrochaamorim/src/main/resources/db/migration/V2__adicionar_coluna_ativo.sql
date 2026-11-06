-- V2: indica se o usuário está ativo no sistema
ALTER TABLE usuario
    ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;
