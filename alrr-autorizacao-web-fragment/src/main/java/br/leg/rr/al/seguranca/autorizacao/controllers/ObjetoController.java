package br.leg.rr.al.seguranca.autorizacao.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.leg.rr.al.core.CoreUtilsValidationMessages;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.utils.StringHelper;
import br.leg.rr.al.core.web.controller.status.DialogControllerEntityStatus;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.seguranca.autorizacao.ejb.ModuloLocal;
import br.leg.rr.al.seguranca.autorizacao.ejb.ObjetoLocal;
import br.leg.rr.al.seguranca.autorizacao.jpa.Modulo;
import br.leg.rr.al.seguranca.autorizacao.jpa.Objeto;

@Named
@ViewScoped
public class ObjetoController extends DialogControllerEntityStatus<Objeto, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7882112613545512515L;

	Logger logger = LoggerFactory.getLogger(ObjetoController.class);

	@EJB
	private ObjetoLocal bean;

	@EJB
	private ModuloLocal moduloBean;

	private List<Modulo> modulosAtivos;

	private List<Modulo> modulos;

	// ************ FILTROS DE PESQUISA ************//
	/**
	 * valor do filtro 'nome' do pesquisar municipio.
	 */
	private String nome;

	private List<Modulo> modulosSelecionados;

	private StatusType situacao;
	// ********************************************//

	@PostConstruct
	public void init() {
		setBean(bean);
		carregarModulos();
		jaExisteMsg = "objeto já existe.";
		setNovoDialogName("dlg-objeto");
		setEditarDialogName("dlg-objeto");
		setDetalhesDialogName("dlg-objeto-detalhes");
	}

	@Override
	public void carregarEntity() {

		try {
			utx.begin();
			Objeto obj = getEntity();

			if (obj != null && obj.getId() != null) {

				obj = getBean().buscar(obj.getId());

				// fetch´s Modulo
				if (obj.getModulo() != null) {
					obj.getModulo().getId();
				}

				setEntity(obj);

			}
		} catch (NotSupportedException | SystemException e) {
			logger.error(fatal, e.getMessage());
			FacesMessageUtils.addFatal(e.getMessage());
		} catch (Exception e) {
			logger.error(fatal, e.getMessage());
			FacesMessageUtils.addFatal(e.getMessage());
		} finally {
			try {
				utx.commit();
			} catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException
					| RollbackException | SystemException e) {
				logger.error(fatal, e.getMessage());
				FacesMessageUtils.addFatal(e.getMessage());
			}
		}

	}

	@Override
	protected void posEditar() {

		super.posEditar();
		modulosAtivos = moduloBean.getAtivos(getEntity().getModulo());
	}

	@Override
	public String pesquisar() {

		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put(ObjetoLocal.PESQUISAR_PARAM_NOME, nome);
		filtros.put(ObjetoLocal.PESQUISAR_PARAM_SITUACAO, situacao);
		filtros.put(ObjetoLocal.PESQUISAR_PARAM_FETCH_MODULO, true);
		filtros.put(ObjetoLocal.PESQUISAR_PARAM_MODULOS, modulosSelecionados);

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

	public void carregarModulosAtivos() {
		modulosAtivos = moduloBean.getAtivos();
	}

	public void carregarModulos() {
		setModulos(moduloBean.buscarTodos());
	}

	@Override
	public String novo() {
		carregarModulosAtivos();
		return super.novo();
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

	public List<Modulo> getModulosSelecionados() {
		return modulosSelecionados;
	}

	public void setModulosSelecionados(List<Modulo> modulosSelecionados) {
		this.modulosSelecionados = modulosSelecionados;
	}

	public List<Modulo> getModulosAtivos() {
		return modulosAtivos;
	}

	public void setModulosAtivos(List<Modulo> modulosAtivos) {
		this.modulosAtivos = modulosAtivos;
	}

	public List<Modulo> getModulos() {
		return modulos;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}

}
