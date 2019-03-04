package br.leg.rr.al.seguranca.autorizacao.jpa;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.jpa.DominioIndexado;

@Entity
@Table
public class Perfil extends DominioIndexado {

	private static final long serialVersionUID = -1718333069513970373L;

	@NotNull(message = "Grupo do Perfil: preenchimento obrigatório")
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "grupo_perfil_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "grupo_perfil_fk"))
	private GrupoPerfil grupoPerfil;

	public GrupoPerfil getGrupoPerfil() {
		return grupoPerfil;
	}

	public void setGrupoPerfil(GrupoPerfil grupo) {
		this.grupoPerfil = grupo;
	}

}
