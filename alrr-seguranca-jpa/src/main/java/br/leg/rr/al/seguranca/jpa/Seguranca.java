package br.leg.rr.al.seguranca.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import br.leg.rr.al.seguranca.domain.PeriodoExpiraSenha;
import br.leg.rr.al.seguranca.domain.PeriodoExpiraSenhaConverter;

@Embeddable
public class Seguranca implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1146016410741589251L;

	/**
	 * Indica se a senha dos usuários devem ser forte.
	 */
	@Column(name = "senha_forte", nullable = false)
	private Boolean senhaForte;

	/**
	 * Indica se permite que a nova senha seja igual a senha anterior.
	 */
	@Column(name = "permite_mesma_senha", nullable = false)
	private Boolean permiteMesmaSenha;

	/**
	 * Indica se deve bloquear o usuário caso erre a senha mais de 3 vezes.
	 */
	@Column(name = "bloquar_senha_invalida", nullable = false)
	private Boolean bloquearSenhaInvalida;

	/**
	 * Indica o periodo que a senha expira. 30, 60, 90 ou 120 dias.
	 */
	@Convert(converter = PeriodoExpiraSenhaConverter.class)
	@Column(name = "periodo_expira_senha", length = 3, nullable = true)
	private PeriodoExpiraSenha periodoExpiraSenha;

	@Column(name = "expira_senha", nullable = false)
	private Boolean expiraSenha;

	/**
	 * Defique que na primeira autenticação, o usuário deve trocar senha.
	 */
	@Column(name = "troca_senha_primeiro_acesso", nullable = false)
	private Boolean trocaSenhaPrimeiroAcesso;

	/**
	 * Indica que envia a nova senha temporaria do usuário por email, caso o
	 * usuário tenha esquecido e peça para gerar uma nova.
	 */
	@Column(nullable = false, name = "enviar_senha_email")
	private Boolean enviarSenhaByEmail;

	public Boolean getSenhaForte() {
		return senhaForte;
	}

	public void setSenhaForte(Boolean senhaForte) {
		this.senhaForte = senhaForte;
	}

	public Boolean getPermiteMesmaSenha() {
		return permiteMesmaSenha;
	}

	public void setPermiteMesmaSenha(Boolean permiteMesmaSenha) {
		this.permiteMesmaSenha = permiteMesmaSenha;
	}

	public Boolean getBloquearSenhaInvalida() {
		return bloquearSenhaInvalida;
	}

	public void setBloquearSenhaInvalida(Boolean bloquearSenhaInvalida) {
		this.bloquearSenhaInvalida = bloquearSenhaInvalida;
	}

	public PeriodoExpiraSenha getPeriodoExpiraSenha() {
		return periodoExpiraSenha;
	}

	public void setPeriodoExpiraSenha(PeriodoExpiraSenha periodoExpiraSenha) {
		this.periodoExpiraSenha = periodoExpiraSenha;
	}

	public Boolean getExpiraSenha() {
		return expiraSenha;
	}

	public void setExpiraSenha(Boolean expiraSenha) {
		this.expiraSenha = expiraSenha;
	}

	public Boolean getTrocaSenhaPrimeiroAcesso() {
		return trocaSenhaPrimeiroAcesso;
	}

	public void setTrocaSenhaPrimeiroAcesso(Boolean trocaSenhaPrimeiroAcesso) {
		this.trocaSenhaPrimeiroAcesso = trocaSenhaPrimeiroAcesso;
	}

	public Boolean getEnviarSenhaByEmail() {
		return enviarSenhaByEmail;
	}

	public void setEnviarSenhaByEmail(Boolean enviarSenhaEmail) {
		this.enviarSenhaByEmail = enviarSenhaEmail;
	}

}
