package br.leg.rr.al.sistema.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.leg.rr.al.core.web.controller.status.DialogControllerEntityStatus;
import br.leg.rr.al.seguranca.ejb.MenuLocal;
import br.leg.rr.al.seguranca.jpa.Menu;

@Named
@ViewScoped
public class MenuController extends DialogControllerEntityStatus<Menu, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6239603098390230045L;
	@EJB
	private MenuLocal bean;

	@PostConstruct
	public void init() {
		setBean(bean);
		setEditarDialogName("dlg-menu");
	}

}
