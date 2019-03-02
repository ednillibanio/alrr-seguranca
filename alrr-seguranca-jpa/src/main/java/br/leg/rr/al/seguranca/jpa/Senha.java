package br.leg.rr.al.seguranca.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

//TODO Ver se fiz certo colocando como embeddable
@Embeddable
public class Senha implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7340498869780094064L;

	/**
	 * Indica o tamanho mínimo da senha.
	 * 
	 */
	@Column(name = "senha_tam_minimo", nullable = false)
	private int tamanhoMinimo = 0;

	/**
	 * Informa o tamanho máximo da senha. Sempre vai ser 20.
	 */
	@Transient
	private int tamanhoMaximo = 20;
	/**
	 * Quantidade mínima de números inteiros na senha.
	 */
	@Column(name = "senha_qtde_min_digitos", nullable = false)
	private int qtdeMinDigitos = 0;

	/**
	 * Quantidade mínima de simbolos na senha. Ex. @##$%ˆ&*(
	 */
	@Column(name = "senha_qtde_min_simbolos", nullable = false)
	private int qtdeMinSimbolos = 0;

	/**
	 * Quantidade mínima de letras maiusculas.
	 */
	@Column(name = "senha_qtde_min_letras_maiusc", nullable = false)
	private int qtdeMinLetrasMaiusculas = 0;

	/**
	 * Quantidade mínima de letras minusculas.
	 */
	@Column(name = "senha_qtde_min_letras_minusc", nullable = false)
	private int qtdeMinLetrasMinusculas = 0;

	public int getQtdeMinDigitos() {
		return qtdeMinDigitos;
	}

	public void setQtdeMinDigitos(int qtdeMinInteiros) {
		this.qtdeMinDigitos = qtdeMinInteiros;
	}

	public int getQtdeMinLetrasMaiusculas() {
		return qtdeMinLetrasMaiusculas;
	}

	public void setQtdeMinLetrasMaiusculas(int qtdeMinLetrasMaiusculas) {
		this.qtdeMinLetrasMaiusculas = qtdeMinLetrasMaiusculas;
	}

	public int getQtdeMinSimbolos() {
		return qtdeMinSimbolos;
	}

	public void setQtdeMinSimbolos(int qtdeMinSimbolos) {
		this.qtdeMinSimbolos = qtdeMinSimbolos;
	}

	public int getQtdeMinLetrasMinusculas() {
		return qtdeMinLetrasMinusculas;
	}

	public void setQtdeMinLetrasMinusculas(int qtdeMinLetrasMinusculas) {
		this.qtdeMinLetrasMinusculas = qtdeMinLetrasMinusculas;
	}

	public int getTamanhoMaximo() {
		return tamanhoMaximo;
	}

	public int getTamanhoMinimo() {
		return tamanhoMinimo;
	}

	public void setTamanhoMinimo(int tamanhoMinimo) {
		this.tamanhoMinimo = tamanhoMinimo;
	}
}
