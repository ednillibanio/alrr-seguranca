
/**
 * Criar as chaves estrangeiras e cascade delete
 */
/**ALTER TABLE seguranca_schema.perfil
    ADD CONSTRAINT dsas FOREIGN KEY (grupo_perfil_id)
    REFERENCES seguranca_schema.grupo_perfil (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;
CREATE INDEX fki_dsas
    ON seguranca_schema.perfil(grupo_perfil_id);
    **/

CREATE UNIQUE INDEX grupo_perfil_uq_idx1 ON seguranca_schema.grupo_perfil (LOWER(grupo_perfil.nome));
CREATE UNIQUE INDEX perfil_uq_idx1 ON seguranca_schema.perfil (grupo_perfil_id, LOWER(perfil.nome)) WHERE grupo_perfil_id IS NOT NULL;
CREATE UNIQUE INDEX perfil_uq_idx2 ON seguranca_schema.perfil (LOWER(perfil.nome)) WHERE grupo_perfil_id IS NULL;


CREATE UNIQUE INDEX modulo_uq_idx1 ON seguranca_schema.modulo (LOWER(modulo.nome));
CREATE UNIQUE INDEX objeto_uq_idx1 ON seguranca_schema.objeto (modulo_id, LOWER(objeto.nome)) WHERE modulo_id IS NOT NULL;
CREATE UNIQUE INDEX objeto_uq_idx2 ON seguranca_schema.objeto (LOWER(objeto.nome)) WHERE modulo_id IS NULL;
CREATE UNIQUE INDEX operacao_uq_idx1 ON seguranca_schema.operacao (LOWER(operacao.nome)) WHERE objeto_id IS NULL;
CREATE UNIQUE INDEX operacao_uq_idx2 ON seguranca_schema.operacao (objeto_id, LOWER(operacao.nome)) WHERE objeto_id IS NOT NULL;
CREATE UNIQUE INDEX permissao_url_uq_idx1 on seguranca_schema.permissao_url (permissao_id, LOWER(permissao_url.url));
CREATE UNIQUE INDEX permissao_uq_idx1 ON seguranca_schema.permissao (LOWER(permissao.valor));
ALTER TABLE seguranca_schema.permissao ADD CONSTRAINT permissao_uq1 UNIQUE (objeto_id, operacao_id);




