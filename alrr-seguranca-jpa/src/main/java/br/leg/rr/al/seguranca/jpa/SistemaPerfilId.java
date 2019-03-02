package br.leg.rr.al.seguranca.jpa;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SistemaPerfilId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 256990566229319795L;

	@Column(name = "sistema_id")
	private Integer sistemaId;

	@Column(name = "perfil_id")
	private Integer perfilId;

	public SistemaPerfilId() {
	}

	public SistemaPerfilId(Integer perfilId, Integer sistemaId) {
		this.perfilId = perfilId;
		this.sistemaId = sistemaId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		SistemaPerfilId that = (SistemaPerfilId) o;
		return Objects.equals(perfilId, that.perfilId) && Objects.equals(sistemaId, that.sistemaId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(perfilId, sistemaId);
	}
}
