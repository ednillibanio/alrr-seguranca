package br.leg.rr.al.seguranca.autorizacao.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

import br.leg.rr.al.core.jpa.DominioIndexado;

@Indexed
@Entity
@Table
public class Perfil extends DominioIndexado {

	private static final long serialVersionUID = -1718333069513970373L;

	@Column(nullable = true, length = 250)
	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "grupo_perfil_id", referencedColumnName = "id", nullable = true, foreignKey = @ForeignKey(name = "grupo_perfil_fk"))
	private GrupoPerfil grupoPerfil;

	public GrupoPerfil getGrupoPerfil() {
		return grupoPerfil;
	}

	public void setGrupoPerfil(GrupoPerfil grupo) {
		this.grupoPerfil = grupo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
