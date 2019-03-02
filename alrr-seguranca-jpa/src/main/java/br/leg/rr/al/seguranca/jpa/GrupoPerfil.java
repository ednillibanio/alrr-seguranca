package br.leg.rr.al.seguranca.jpa;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

import br.leg.rr.al.core.jpa.DominioIndexado;

@Indexed
@Entity
@Table
public class GrupoPerfil extends DominioIndexado {

	private static final long serialVersionUID = -7368701394019331647L;

	/*
	 * @ManyToMany
	 * 
	 * @JoinTable(name = "grupo_perfil_sistema", joinColumns = @JoinColumn(name =
	 * "grupo_perfil_id", referencedColumnName = "id", foreignKey = @ForeignKey(name
	 * = "grupo_fk")), inverseJoinColumns = @JoinColumn(name = "sistema_id",
	 * referencedColumnName = "id", foreignKey = @ForeignKey(name = "sistema_fk")),
	 * uniqueConstraints = @UniqueConstraint(name = "grupo_perfil_sistema_uq",
	 * columnNames = { "grupo_perfil_id", "sistema_id" })) private List<Sistema>
	 * sistemas;
	 */

	@OneToMany(mappedBy = "grupoPerfil", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Perfil> perfis;

}
