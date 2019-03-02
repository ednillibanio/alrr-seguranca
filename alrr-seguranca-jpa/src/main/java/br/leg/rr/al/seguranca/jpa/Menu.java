/**
 * 
 */
package br.leg.rr.al.seguranca.jpa;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.leg.rr.al.core.jpa.Dominio;
import br.leg.rr.al.seguranca.core.jpa.Sistema;

/**
 * @author Ednil Libanio da Costa Junior
 *
 */
@Entity
public class Menu extends Dominio {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3584978459261301763L;

	private String label;

	private String permission;

	private int ordem;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "menu_superior_id", foreignKey = @ForeignKey(name = "menu_superior_fk"))
	private Menu menu;

	@OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Menu> submenus;

	@OneToOne(orphanRemoval = true, optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "sistema_id", foreignKey = @ForeignKey(name = "sistema_fk"))
	public Sistema sistema;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

}
