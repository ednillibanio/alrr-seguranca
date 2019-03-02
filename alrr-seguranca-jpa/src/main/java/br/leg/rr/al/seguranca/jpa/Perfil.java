package br.leg.rr.al.seguranca.jpa;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.jpa.DominioIndexado;
import br.leg.rr.al.seguranca.domain.UsuarioType;
import br.leg.rr.al.seguranca.domain.UsuarioTypeConverter;

@Entity
@Table
public class Perfil extends DominioIndexado {

	private static final long serialVersionUID = -1718333069513970373L;

	@NotNull(message = "Tipo do Usuário: preenchimento obrigatório")
	@Convert(converter = UsuarioTypeConverter.class)
	@Column(name = "tipo_usuario", length = 1, nullable = false)
	private UsuarioType tipoUsuario;

	@NotNull(message = "Grupo do Perfil: preenchimento obrigatório")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "grupo_perfil_id", nullable = false, foreignKey = @ForeignKey(name = "grupo_perfil_fk"))
	private GrupoPerfil grupoPerfil;

	public GrupoPerfil getGrupoPerfil() {
		return grupoPerfil;
	}

	public void setGrupoPerfil(GrupoPerfil grupo) {
		this.grupoPerfil = grupo;
	}

	public UsuarioType getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(UsuarioType tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}
