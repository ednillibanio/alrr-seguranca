package br.leg.rr.al.seguranca.jpa;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import br.leg.rr.al.core.jpa.BaseEntityStatus;

@Entity
/**
 * TODO: Entidade que irá integrar usuário e pessoa para diminuir o acoplamento
 * entre as duas entidades. Ver se é o melhor caminho.
 * 
 * @author ednil
 *
 */
public class Sessao extends BaseEntityStatus<Integer> {

	private static final long serialVersionUID = -928762612582557980L;

	@OneToOne(optional = false)
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
