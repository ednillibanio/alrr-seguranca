package br.leg.rr.al.seguranca.autorizacao.jpa;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.jpa.BaseEntityStatus;

/**
 * 
 * @author ednil
 *
 */
@Entity
@Table
// TOXO Criar os index.
public class Permissao extends BaseEntityStatus<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7552792493643136248L;

	/**
	 * valor = "objeto:sistemaOperacao" -> haspermisison('contato:incluir');
	 */
	@NotNull(message = "Valor: preenchimento obrigatório")
	@Column(length = 100, nullable = false)
	private String valor;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "objeto_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "objeto_fk"), nullable = false)
	private Objeto objeto;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "operacao_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "operacao_fk"), nullable = false)
	private Operacao operacao;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "permissao")
	private Set<PermissaoUrl> permissaoUrl;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public Set<PermissaoUrl> getPermissaoUrl() {
		return permissaoUrl;
	}

	public void setPermissaoUrl(Set<PermissaoUrl> permissaoUrl) {
		this.permissaoUrl = permissaoUrl;
	}

}
