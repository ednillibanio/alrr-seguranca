package br.leg.rr.al.sistema.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import br.leg.rr.al.seguranca.ejb.SistemaLocal;
import br.leg.rr.al.seguranca.jpa.Menu;
import br.leg.rr.al.seguranca.jpa.Sistema;

@Named
@ApplicationScoped
public class SistemaSessao {

	private Sistema sistema;
	private Menu menu;
	@EJB
	private SistemaLocal bean;

	@PostConstruct
	public void init() {

	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
