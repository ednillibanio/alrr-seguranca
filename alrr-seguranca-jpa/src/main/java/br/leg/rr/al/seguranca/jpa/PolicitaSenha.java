package br.leg.rr.al.seguranca.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import br.leg.rr.al.seguranca.domain.PeriodoExpiraSenha;
import br.leg.rr.al.seguranca.domain.PeriodoExpiraSenhaConverter;

@Embeddable
public class PolicitaSenha implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1146016410741589251L;

	/**
	 * Indica se permite que a nova senha seja igual a senha anterior.
	 */
	@Column(name = "permite_mesma_senha", nullable = false)
	private Boolean permiteMesmaSenha = false;

	/**
	 * Informa quantas tentativas permitidas antes de bloquear a senha.
	 * 
	 */
	@Column(name = "bloquear_senha_invalida", nullable = false)
	private int bloqueiaSenhaInvalida = 0;

	/**
	 * Defique que na primeira autenticação, o usuário deve trocar senha.
	 */
	@Column(name = "troca_senha_primeiro_acesso", nullable = false)
	private Boolean trocaSenhaPrimeiroAcesso = false;

	/**
	 * Indica de quanto em quanto tempo a senha expira. 30, 60, 90 ou 120 dias.
	 * 0 - Indica que a senha nunca expica.
	 */
	@Convert(converter = PeriodoExpiraSenhaConverter.class)
	@Column(name = "periodo_expira_senha", length = 3, nullable = false)
	private PeriodoExpiraSenha periodoExpiraSenha = PeriodoExpiraSenha.NAO_EXPIRA;

	/**
	 * Indica que envia a nova senha temporaria do usuário por email, caso o
	 * usuário tenha esquecido e peça para gerar uma nova.
	 */
	@Column(nullable = false, name = "enviar_senha_email")
	private Boolean enviarSenhaPorEmail;

	@Embedded
	/**
	 * Atributos da Senha. Indicando tamanho da senha, tipos e quantidade de
	 * caracteres.
	 */
	private Senha senha;

	public Boolean getPermiteMesmaSenha() {
		return permiteMesmaSenha;
	}

	public void setPermiteMesmaSenha(Boolean permiteMesmaSenha) {
		this.permiteMesmaSenha = permiteMesmaSenha;
	}

	public int getBloqueiaSenhaInvalida() {
		return bloqueiaSenhaInvalida;
	}

	public void setBloqueiaSenhaInvalida(int bloquearSenhaInvalida) {
		this.bloqueiaSenhaInvalida = bloquearSenhaInvalida;
	}

	public PeriodoExpiraSenha getPeriodoExpiraSenha() {
		return periodoExpiraSenha;
	}

	public void setPeriodoExpiraSenha(PeriodoExpiraSenha periodoExpiraSenha) {
		this.periodoExpiraSenha = periodoExpiraSenha;
	}

	public Boolean getTrocaSenhaPrimeiroAcesso() {
		return trocaSenhaPrimeiroAcesso;
	}

	public void setTrocaSenhaPrimeiroAcesso(Boolean trocaSenhaPrimeiroAcesso) {
		this.trocaSenhaPrimeiroAcesso = trocaSenhaPrimeiroAcesso;
	}

	public Boolean getEnviarSenhaPorEmail() {
		return enviarSenhaPorEmail;
	}

	public void setEnviarSenhaPorEmail(Boolean enviarSenhaEmail) {
		this.enviarSenhaPorEmail = enviarSenhaEmail;
	}

	public Senha getSenha() {
		return senha;
	}

	public void setSenha(Senha senha) {
		this.senha = senha;
	}

}
