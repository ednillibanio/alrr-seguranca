package br.leg.rr.al.seguranca.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.jpa.BaseEntity;
import br.leg.rr.al.pessoa.jpa.PessoaFisica;
import br.leg.rr.al.seguranca.domain.UsuarioType;
import br.leg.rr.al.seguranca.domain.UsuarioTypeConverter;
import br.leg.rr.al.servidor.jpa.ServidorPublico;

/**
 * TODO: Ver a parte de segurança nesses modelos.
 * https://directory.apache.org/fortress/user-guide/1.3-what-rbac-is.html RBAC0
 * - Users, Roles, Permissions (Objects-Operations), Sessions - Form the Core of
 * ANSI RBAC. Role activation and Permissions mapped to Object->Operation
 * pairing are key facets of the basic ANSI RBAC model.
 * https://en.wikipedia.org/wiki/Role-based_access_control
 */
@Entity
@Table(name = "usuario_tipo")
public class UsuarioTipo extends BaseEntity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6347630702987750401L;

	@NotNull(message = "Preenchimento obrigatório do campo: Tipo do Usuário")
	@Convert(converter = UsuarioTypeConverter.class)
	@Column(name = "tipo_usuario", length = 1, nullable = false)
	private UsuarioType tipo;

	/**
	 * Caso o UsuarioType seja igual a 'Externo', então deve vincular o sistema ao
	 * usuário.
	 */
	@OneToOne(optional = true, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(nullable = true, foreignKey = @ForeignKey(name = "pessoa_fk"))
	private PessoaFisica pessoa;

	/**
	 * Caso o UsuarioType seja igual a 'Servidor Público' ou 'Parlamentar', então
	 * deve vincular o sistema ao usuário.
	 */
	@OneToOne(optional = true, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(nullable = true, name = "servidor_publico_id", foreignKey = @ForeignKey(name = "servidor_publico_fk"))
	private ServidorPublico servidorPublico;

	/**
	 * Caso o UsuarioType seja igual a 'Sistema', então deve vincular o sistema ao
	 * usuário.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "sistema_id", foreignKey = @ForeignKey(name = "sistema_fk"))
	private Sistema sistema;

	/**
	 * @return the pessoa
	 */
	public PessoaFisica getPessoa() {
		return pessoa;
	}

	/**
	 * @param pessoa
	 *            the pessoa to set
	 */
	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	/**
	 * @return the servidorPublico
	 */
	public ServidorPublico getServidorPublico() {
		return servidorPublico;
	}

	/**
	 * @param servidorPublico
	 *            the servidorPublico to set
	 */
	public void setServidorPublico(ServidorPublico servidorPublico) {
		this.servidorPublico = servidorPublico;
	}

	/**
	 * @return the sistema
	 */
	public Sistema getSistema() {
		return sistema;
	}

	/**
	 * @param sistema
	 *            the sistema to set
	 */
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	/**
	 * @return the tipo
	 */
	public UsuarioType getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(UsuarioType tipo) {
		this.tipo = tipo;
	}

}
