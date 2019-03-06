package br.leg.rr.al.seguranca.autorizacao.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.leg.rr.al.core.web.controller.AutoCompleteController;
import br.leg.rr.al.seguranca.autorizacao.ejb.ObjetoLocal;
import br.leg.rr.al.seguranca.autorizacao.jpa.Objeto;

@Named
@RequestScoped
public class ObjetoAutoCompleteController extends AutoCompleteController<Objeto> {

	@EJB
	private ObjetoLocal bean;

	@Override
	@PostConstruct
	public void init() {
		setBean(bean);

	}

}
