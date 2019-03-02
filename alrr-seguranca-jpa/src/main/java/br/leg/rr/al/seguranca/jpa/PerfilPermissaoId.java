package br.leg.rr.al.seguranca.jpa;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PerfilPermissaoId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1718333069513970373L;

	@Column(name = "perfil_id")
	private Integer perfilId;

	@Column(name = "permissao_id")
	private Integer permissaoId;

	public PerfilPermissaoId() {
	}

	public PerfilPermissaoId(Integer perfilId, Integer permissaoId) {
		this.perfilId = perfilId;
		this.permissaoId = permissaoId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		PerfilPermissaoId that = (PerfilPermissaoId) o;
		return Objects.equals(perfilId, that.perfilId) && Objects.equals(permissaoId, that.permissaoId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(perfilId, permissaoId);
	}
}
