package br.leg.rr.al.sistema.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.leg.rr.al.core.domain.NavigationOutcomeDefault;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.web.controller.status.DialogControllerEntityStatus;
import br.leg.rr.al.seguranca.ejb.SistemaLocal;
import br.leg.rr.al.seguranca.jpa.Sistema;

@Named
@ViewScoped
public class SistemaController extends DialogControllerEntityStatus<Sistema, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3815139685297240851L;

	/**
	 * Filtro de pesquisa nome.
	 */
	private String nome;

	/**
	 * Filtro de pesquisa situacao.
	 */
	private StatusType situacao;

	@EJB
	private SistemaLocal bean;

	@PostConstruct
	public void init() {
		setBean(bean);
		jaExisteMsg = "Sistema já existe.";

		setNovoDialogName("dlg-sistema");
		setEditarDialogName("dlg-sistema");
	}

	public List<Sistema> buscarTodos() {
		return bean.buscarTodos();
	}

	public List<Sistema> getSistemasAtivo() {
		return bean.getAtivos();
	}

	public List<Sistema> completeSistemas(String query) {
		return bean.buscarPorNome(query);
	}

	/*
	 * @Override public String pesquisar() {
	 * 
	 * Map<String, Object> filtros = new HashMap<String, Object>();
	 * filtros.put(SistemaLocal.PESQUISAR_PARAM_NOME, nome);
	 * 
	 * setEntities(bean.pesquisar(filtros)); if (getEntities().size() > 0) {
	 * createDataModel(getEntities()); } else {
	 * FacesMessageUtils.addError(CoreUtilsValidationMessages.
	 * REGISTRO_NAO_ENCONTRADO); }
	 * 
	 * return null; }
	 */

	/*
	 * @Override public String salvar() { try { bean.salvar(getEntity());
	 * 
	 * FacesMessageUtils.addInfo(CoreUtilsValidationMessages.
	 * REGISTRO_SALVO_COM_SUCESSO); return
	 * NavigationOutcomeDefault.SUCCESSO.toString(); } catch (BeanException e) {
	 * FacesMessageUtils.addFatal(e.getMessage()); e.printStackTrace(); return null;
	 * }
	 * 
	 * }
	 */

	public String cancelar() {
		return NavigationOutcomeDefault.CANCELAR.toString();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public StatusType getSituacao() {
		return situacao;
	}

	public void setSituacao(StatusType situacao) {
		this.situacao = situacao;
	}
}
