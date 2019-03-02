package br.leg.rr.al.seguranca.domain;

public final class SegurancaValidationMessages {

	/**
	 * Preenchimento obrigatório do campo Perfil.
	 */
	public static final String INFORME_UM_PERFIL = "Preenchimento obrigatório do campo: Perfil.";

	/**
	 * Preenchimento obrigatório do campo Sistema.
	 */
	public static final String INFORME_UM_SISTEMA = "Preenchimento obrigatório do campo: Sistema.";

	/**
	 * Preenchimento obrigatório do campo Usuario.
	 */
	public static final String INFORME_UM_USUARIO = "Preenchimento obrigatório do campo: Usuário.";

	/**
	 * Preenchimento obrigatório do campo Situação.
	 */
	public static final String INFORME_A_SITUACAO = "Preenchimento obrigatório do campo: Situação.";

	public static final String INFORME_UMA_PESSOA_FISICA = "Usuário sem cadastro de pessoa física. Informe uma Pessoa Física.";

	public static final String USUARIO_ERROR_SENHA_FORA_DO_PADRAO_SEGURANCA = "Senha informada está fora do padrão de segurança.";

	/**
	 * Login informado está fora do padrão de segurança.
	 */
	public static final String USUARIO_LOGIN_FORA_DO_PADRAO_SEGURANCA = "Login informado está fora do padrão de segurança.";

	/**
	 * Informa que a senha contém data de nascimento da pessoa.
	 */
	public static final String USUARIO_ERROR_SENHA_DATA_NASCIMENTO = "Senha não deve conter data de nascimento do usuário.";

	/**
	 * Informa que senha contém o ano atual.
	 */
	public static final String USUARIO_ERROR_SENHA_ANO_ATUAL = "Senha não deve conter o ano atual.";

	/**
	 * Informa que a senha é igual ao login.
	 */
	public static final String USUARIO_ERROR_SENHA_IGUAL_LOGIN = "Senha não deve ser igual ao login do usuário.";

	/**
	 * Informa que senha contém parte do nome da pessoa;
	 */
	public static final String USUARIO_ERROR_SENHA_CONTEM_PARTE_NOME = "Senha não deve conter parte do nome do usuário";

	/**
	 * Informa que a senha do usuário é fraca, fora do padrão.
	 */
	public static final String USUARIO_ERROR_SENHA_FRACA = "Senha informada é considerada fraca.";

	/**
	 * Informa que a senha do usuário expirou.
	 */
	public static final String SENHA_EXPIROU = "Senha expirou. Atualize a sua senha.";

	/**
	 * Pessoa Física com o cpf {0} já possui um usuário cadastrado.
	 */
	public static final String PESSOA_JA_POSSUI_USUARIO = "Pessoa Física com o cpf {0} já possui um usuário cadastrado.";

	/**
	 * Servidor com a matricula {0} já possui um usuário cadastrado.
	 */
	public static final String SERVIDOR_JA_POSSUI_USUARIO = "Servidor com a matricula {0} já possui um usuário cadastrado.";

	/**
	 * Informa que as funcionalidades de um perfil, foram vinculadas ou
	 * desvinculadas com sucesso do perfil selecionado.
	 */
	public static final String USUARIO_ACESSO_SISTEMAS_ATUALIZADO_COM_SUCESSO = "As permissões de acesso aos sistemas foram atualizados com sucesso.";

	/**
	 * Informa que o valor do campo Confirma Senha é diferente da senha.
	 */
	public static final String CONFIRMA_SENHA_DIFERENTE_DE_SENHA = "usuario.error.confirma.senha.diferente.senha";

	/**
	 * Informa que senha do usuário foi atualizada com sucesso.
	 */
	public static final String SENHA_MODIFICADA_COM_SUCESSO = "usuario.info.senha.modificada.sucesso";

	/**
	 * Informa que a senha do usuário é invalida.
	 */
	public static final String SENHA_INCORRETA = "usuario.error.senha.incorreta";

	/**
	 * Informa que o login informado não foi encontrado no baco de dados.
	 */
	public static final String LOGIN_INVALIDO = "Login inválido";

	/**
	 * Informa que o login informado está fora do padrão.
	 */
	public static final String LOGIN_FORA_PADRAO = "Login fora do padrão.";

	/**
	 * Informa que não foi possivel autenticar o usuário. Que o login ou senha está
	 * invalido.
	 */
	public static final String LOGIN_OU_SENHA_INVALIDOS = "Login ou senha invalidos.";

	/**
	 * Informa que ocorreu falha durante a autenticação do usuário no sistema.
	 */
	public static final String FALHA_NA_AUTENTICACAO = "Falha na autenticação";

	/**
	 * Informa ao usuário que deve trocar senha, pois é o seu primeiro acesso ao
	 * sistema.
	 */
	public static final String TROCAR_SENHA_PRIMEIRO_ACESSO = "usuario.warn.troca.senha.primeiro.acesso";

	/**
	 * Informa que a nova senha não pode ser igual a senha atual. Necessário
	 * informar novamente.
	 */
	public static final String NOVA_SENHA_IGUAL_SENHA_ATUAL = "usuario.error.nova.senha.igual.senha.atual";

	/**
	 * Informa que o perfil já possui o usuário selecionado. Dois parametros devem
	 * ser informados: Nome do Perfil - {0}, e Login do Usuario - {1}.
	 */
	public static final String PERFIL_JA_POSSUI_USUARIO = "perfil.error.ja_possui_usuario";

	/**
	 * Usuário sem perfil. Informe um perfil para o usuário.
	 */
	public static final String USUARIO_SEM_PERFIL = "Usuário sem perfil. Informe um perfil para o usuário.";

	/**
	 * Informa que o usuário foi ativado com sucesso.
	 */
	public static final String USUARIO_ATIVADO_COM_SUCESSO = "usuario.info.ativado.com.sucesso";

	/**
	 * Informa que o usuário foi bloqueado com sucesso.
	 */
	public static final String USUARIO_BLOQUEADO_COM_SUCESSO = "usuario.info.bloqueado.com.sucesso";

	/**
	 * Informa que o acesso ao sistema {0} foi habilitado.<br>
	 * {@literal '{0}' - Nome do sistema
	 */
	public static final String USUARIO_ACESSO_SISTEMA_HABILITADO = "Acesso ao sistema {0} foi habilitado.";

	/**
	 * Informa que o acesso ao sistema {0} foi desabilitado.<br>
	 * {@literal '{0}' - Nome do sistema
	 */
	public static final String USUARIO_ACESSO_SISTEMA_DESABILITADO = "Acesso ao sistema {0} foi desabilitado.";

	/**
	 * Informa que o usuário está sem acesso ao sistema por que a situação é
	 * inativa.
	 */
	public static final String USUARIO_ESTA_BLOQUEADO = "usuario.error.bloqueado";

	/**
	 * Informa que o usuário não foi encontrado com o cpf informado.
	 */
	public static final String USUARIO_COM_CPF_NAO_ENCONTRADO = "usuario.error.com.cpf.nao.encontrado";

	/**
	 * Informa que a senha do usuário não tem o minimo de digitos.
	 */
	public static final String USUARIO_ERROR_SENHA_MINIMO_DIGITOS = "{usuario.error.senha.fraca}";

	/**
	 * Informa que a senha do usuário não tem o minimo de letras maiusculas.
	 */
	public static final String USUARIO_ERROR_SENHA_MINIMO_MAIUSCULAS = "{usuario.error.senha.fraca}";

	/**
	 * Informa que a senha do usuário não tem o minimo de minusculas.
	 */
	public static final String USUARIO_ERROR_SENHA_MINIMO_MINUSCULAS = "{usuario.error.senha.fraca}";

	/**
	 * Informa que a senha do usuário não tem o tamanho mínimo de caracteres.
	 */
	public static final String USUARIO_ERROR_SENHA_TAMANHO_MINIMO = "{usuario.error.senha.fraca}";

}
