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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.jpa.BaseEntityStatus;
import br.leg.rr.al.seguranca.jpa.SistemaObjeto;
import br.leg.rr.al.seguranca.jpa.SistemaOperacao;

/**
 * 
 * @author ednil
 *
 */
@Entity
@Table(name = "sistema_permissao", uniqueConstraints = {
		@UniqueConstraint(name = "uq_objeto_operacao", columnNames = { "sistema_id", "objeto_id", "operacao_id" }),
		@UniqueConstraint(name = "uq_valor_sistema", columnNames = { "sistema_id", "valor" }) })
// TOXO Criar os index.
public class Permissao extends BaseEntityStatus<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<PermissaoUrl> permissaoUrl;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public SistemaOperacao getOperacao() {
		return sistemaOperacao;
	}

	public void setOperacao(SistemaOperacao sistemaOperacao) {
		this.sistemaOperacao = sistemaOperacao;
	}

	public SistemaObjeto getObjeto() {
		return sistemaObjeto;
	}

	public void setObjeto(SistemaObjeto sistemaObjeto) {
		this.sistemaObjeto = sistemaObjeto;
	}

}
