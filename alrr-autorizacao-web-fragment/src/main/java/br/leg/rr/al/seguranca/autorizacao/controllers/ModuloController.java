package br.leg.rr.al.seguranca.autorizacao.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.leg.rr.al.core.CoreUtilsValidationMessages;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.utils.StringHelper;
import br.leg.rr.al.core.web.controller.status.DialogControllerEntityStatus;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.seguranca.autorizacao.ejb.ModuloLocal;
import br.leg.rr.al.seguranca.autorizacao.jpa.Modulo;

@Named
@ViewScoped
public class ModuloController extends DialogControllerEntityStatus<Modulo, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7882112613545512515L;

	Logger logger = LoggerFactory.getLogger(ModuloController.class);

	@EJB
	private ModuloLocal bean;

	// ************ FILTROS DE PESQUISA ************//
	/**
	 * valor do filtro 'nome' do pesquisar municipio.
	 */
	private String nome;

	private StatusType situacao;
	// ********************************************//

	@PostConstruct
	public void init() {
		setBean(bean);

		jaExisteMsg = "modulo já existe.";
		setNovoDialogName("dlg-modulo");
		setEditarDialogName("dlg-modulo");
		setDetalhesDialogName("dlg-modulo-detalhes");
	}

	@Override
	public String pesquisar() {

		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put(ModuloLocal.PESQUISAR_PARAM_NOME, nome);
		filtros.put(ModuloLocal.PESQUISAR_PARAM_SITUACAO, situacao);

		try {
			setEntities(bean.pesquisar(filtros));
			if (getEntities().size() > 0) {
				createDataModel(getEntities());
			} else {
				FacesMessageUtils.addError(CoreUtilsValidationMessages.REGISTRO_NAO_ENCONTRADO);
			}
		} catch (Exception e) {
			FacesMessageUtils.addFatal(e.getMessage());

		}

		return null;
	}

	/**
	 * Transformar primeira letra de cada palavra em maiúscula.
	 */
	public void capitalizeNome() {
		String nome = StringHelper.capitalizeFully(getEntity().getNome());
		getEntity().setNome(nome);
	}

	/**
	 * @return valor do filtro 'nome' da pesquisa municipio.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param valor do filtro 'nome' da pesquisa municipio.
	 */
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
