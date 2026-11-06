-- V3: não permite dois usuários com o mesmo e-mail
ALTER TABLE usuario
    ADD CONSTRAINT uk_usuario_email UNIQUE (email);
