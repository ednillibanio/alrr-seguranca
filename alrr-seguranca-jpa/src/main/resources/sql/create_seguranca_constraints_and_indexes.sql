CREATE UNIQUE INDEX nome_uq_idx on seguranca_schema.sistema (LOWER(sistema.nome));  
CREATE UNIQUE INDEX valor_uq_idx on seguranca_schema.sistema (LOWER(sistema.valor));



CREATE UNIQUE INDEX nome_uq_idx on seguranca_schema.grupo_perfil (LOWER(grupo_perfil.nome));
CREATE UNIQUE INDEX perfil_uq_idx1 on seguranca_schema.perfil (perfil.grupo_perfil_id, perfil.tipo_usuario, LOWER(perfil.nome));
CREATE UNIQUE INDEX nome_uq_idx on seguranca_schema.modulo (LOWER(modulo.nome));
CREATE UNIQUE INDEX nome_uq_idx on seguranca_schema.objeto (LOWER(objeto.nome));
CREATE UNIQUE INDEX nome_uq_idx on seguranca_schema.operacao (LOWER(operacao.nome));



CREATE UNIQUE INDEX permissao_url_uq_idx1 on seguranca_schema.permissao_url (permissao_url.permissao_id, LOWER(permissao_url.url));

		@UniqueConstraint(name = "uq_objeto_operacao", columnNames = { "sistema_id", "objeto_id", "operacao_id" }),
		@UniqueConstraint(name = "uq_valor_sistema", columnNames = { "sistema_id", "valor" }) })


