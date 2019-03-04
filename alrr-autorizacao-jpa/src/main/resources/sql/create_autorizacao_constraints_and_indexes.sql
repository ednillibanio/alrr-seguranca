
CREATE UNIQUE INDEX grupo_perfil_uq_idx1 ON seguranca_schema.grupo_perfil (LOWER(grupo_perfil.nome));
CREATE UNIQUE INDEX perfil_uq_idx1 ON seguranca_schema.perfil (grupo_perfil_id, LOWER(perfil.nome));
CREATE UNIQUE INDEX modulo_uq_idx1 ON seguranca_schema.modulo (LOWER(modulo.nome));
CREATE UNIQUE INDEX objeto_uq_idx1 ON seguranca_schema.objeto (LOWER(objeto.nome));
CREATE UNIQUE INDEX operacao_uq_idx1 ON seguranca_schema.operacao (LOWER(operacao.nome));


CREATE UNIQUE INDEX permissao_url_uq_idx1 on seguranca_schema.permissao_url (permissao_id, LOWER(permissao_url.url));

ALTER TABLE seguranca_schema.permissao ADD CONSTRAINT permissao_uq1 UNIQUE (objeto_id, operacao_id);
CREATE UNIQUE INDEX permissaor_uq_idx1 ON seguranca_schema.permissao (LOWER(permissao.valor));


