CREATE UNIQUE INDEX nome_uq_idx on seguranca_schema.sistema (LOWER(sistema.nome));  
CREATE UNIQUE INDEX valor_uq_idx on seguranca_schema.sistema (LOWER(sistema.valor));

CREATE UNIQUE INDEX nome_uq_idx on seguranca_schema.grupo_perfil (LOWER(grupo_perfil.nome));

CREATE UNIQUE INDEX perfil_uq_idx1 on seguranca_schema.perfil (perfil.grupo_perfil_id, perfil.tipo_usuario, LOWER(perfil.nome));

