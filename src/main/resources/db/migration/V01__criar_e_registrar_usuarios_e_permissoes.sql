CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `senha` VARCHAR(150) NOT NULL,
  `ativo` tinyint(1) NOT NULL DEFAULT '0',
  `tipo` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `permissoes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `usuarios_permissoes` (
  `usuario_id` INT NOT NULL,
  `permissao_id` INT NOT NULL,
  PRIMARY KEY (`usuario_id`, `permissao_id`),
  INDEX `fk_usuarios_permissoes_permissoes_idx` (`permissao_id` ASC),
  INDEX `fk_usuarios_permissoes_usuarios_idx` (`usuario_id` ASC),
  CONSTRAINT `fk_usuarios_permissoes_usuarios`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `usuarios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuarios_permissoes_permissoes`
    FOREIGN KEY (`permissao_id`)
    REFERENCES `permissoes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


INSERT INTO usuarios (id, nome, email, senha, ativo, tipo) values (1, 'Administrador', 'tecnologia.sistemas@sespa.pa.gov.br', '$2a$10$iwcbc3IUaZN8h0CqZhaKuOxOr5yt.0.jhJ4fVblY/1SMfzXixJhE6', 1, 'Administrador');
INSERT INTO usuarios (id, nome, email, senha, ativo, tipo) values (2, 'Usuario 01', 'usuario01@sespa.pa.gov.br', '$2a$10$iwcbc3IUaZN8h0CqZhaKuOxOr5yt.0.jhJ4fVblY/1SMfzXixJhE6', 0 , 'Operador');


INSERT INTO permissoes (id, descricao) values (1, 'ROLE_CADASTRAR_USUARIO');
INSERT INTO permissoes (id, descricao) values (2, 'ROLE_PESQUISAR_USUARIO');
INSERT INTO permissoes (id, descricao) values (3, 'ROLE_REMOVER_USUARIO');
INSERT INTO permissoes (id, descricao) values (4, 'ROLE_ALTERAR_PASSWORD');


-- Administrador 
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (1, 1);
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (1, 2);
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (1, 3);
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (1, 4);
-- Operador 
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (2, 4);