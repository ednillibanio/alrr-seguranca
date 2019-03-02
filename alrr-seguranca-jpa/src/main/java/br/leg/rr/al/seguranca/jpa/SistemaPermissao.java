package br.leg.rr.al.seguranca.jpa;

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
public class SistemaPermissao extends SistemaEntityStatus<Integer> {

	/**
	* 
	*/
	private static final long serialVersionUID = 4095665896993479832L;

	/**
	 * valor = "objeto:operacao" -> haspermisison('contato:incluir');
	 */
	@NotNull
	@Column(length = 100, nullable = false)
	private String valor;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "objeto_id", foreignKey = @ForeignKey(name = "objeto_fk"), nullable = false)
	private Objeto objeto;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "operacao_id", foreignKey = @ForeignKey(name = "operacao_fk"), nullable = false)
	private Operacao operacao;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<SistemaPermissaoUrl> sistemaPermissaoUrl;

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

}
