/**
 * 
 */
package br.leg.rr.al.seguranca.jpa;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.leg.rr.al.core.jpa.Dominio;

/**
 * @author Ednil Libanio da Costa Junior
 *
 */
@Entity
@Table(name = "menu_item")
public class MenuItem extends Dominio {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2653982160453675595L;

	private String value;
	private String label;
	private String icon;
	private String outcome;
	private String permission;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "menu_superior_id", foreignKey = @ForeignKey(name = "menu_superior_fk"))
	public MenuItem menuSuperior;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "sistema_id", foreignKey = @ForeignKey(name = "sistema_fk"))
	public Sistema sistema;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
}
