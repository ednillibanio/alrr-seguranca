package br.leg.rr.al.seguranca.jpa;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.leg.rr.al.core.jpa.Dominio;

@Entity
@Table
public class GrupoPerfil extends Dominio {

	private static final long serialVersionUID = -7368701394019331647L;

	@ManyToMany
	@JoinTable(name = "grupo_perfil_sistema", joinColumns = @JoinColumn(name = "grupo_perfil_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "grupo_fk")), inverseJoinColumns = @JoinColumn(name = "sistema_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "sistema_fk")), uniqueConstraints = @UniqueConstraint(name = "grupo_perfil_sistema_uq", columnNames = {
			"grupo_perfil_id", "sistema_id" }))
	private List<Sistema> sistemas;

	@OneToMany(mappedBy = "grupoPerfil", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Perfil> perfis;

}
