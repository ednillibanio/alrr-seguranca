package br.leg.rr.al.seguranca.autorizacao.jpa;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.jpa.Dominio;

@Entity
@Table
public class Objeto extends Dominio {

	@NotNull(message = "Módulo: preenchimento obrigatório")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "modulo_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "modulo_fk"))
	private Modulo modulo;

	@OneToMany(mappedBy = "objeto", fetch = FetchType.LAZY)
	private Set<Permissao> permissoes;

}
