/**
 *
 */
package br.leg.al.rr.shiro.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Embeddable
public class AleShiroDataSource {

	@Column(name = "datasource_required_type", length = 250)
	private String dsRequiredType;

	@Column(name = "datasource_resource_name", length = 50)
	private String dsResourceName;

	@Column(name = "datasource_resource_ref")
	private Boolean dsResourceRef;

	/**
	 * @return the dsRequiredType
	 */
	public String getDsRequiredType() {
		return dsRequiredType;
	}

	/**
	 * @param dsRequiredType
	 *            the dsRequiredType to set
	 */
	public void setDsRequiredType(String dsRequiredType) {
		this.dsRequiredType = dsRequiredType;
	}

	/**
	 * @return the dsResourceName
	 */
	public String getDsResourceName() {
		return dsResourceName;
	}

	/**
	 * @param dsResourceName
	 *            the dsResourceName to set
	 */
	public void setDsResourceName(String dsResourceName) {
		this.dsResourceName = dsResourceName;
	}

	/**
	 * @return the dsResourceRef
	 */
	public Boolean getDsResourceRef() {
		return dsResourceRef;
	}

	/**
	 * @param dsResourceRef
	 *            the dsResourceRef to set
	 */
	public void setDsResourceRef(Boolean dsResourceRef) {
		this.dsResourceRef = dsResourceRef;
	}

}
