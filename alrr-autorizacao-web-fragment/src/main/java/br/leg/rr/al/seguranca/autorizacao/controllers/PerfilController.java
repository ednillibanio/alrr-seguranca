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

import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.utils.StringHelper;
import br.leg.rr.al.core.web.controller.status.DialogControllerEntityStatus;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.seguranca.autorizacao.ejb.GrupoPerfilLocal;
import br.leg.rr.al.seguranca.autorizacao.ejb.ObjetoLocal;
import br.leg.rr.al.seguranca.autorizacao.ejb.PerfilLocal;
import br.leg.rr.al.seguranca.autorizacao.jpa.GrupoPerfil;
import br.leg.rr.al.seguranca.autorizacao.jpa.Perfil;

@Named
@ViewScoped
public class PerfilController extends DialogControllerEntityStatus<Perfil, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8313659000882011005L;

	Logger logger = LoggerFactory.getLogger(PerfilController.class);

	@EJB
	private PerfilLocal bean;

	@EJB
	private GrupoPerfilLocal grupoBean;

	private List<GrupoPerfil> gruposAtivos;

	private List<GrupoPerfil> grupos;

	// ************ FILTROS DE PESQUISA ************//
	/**
	 * valor do filtro 'nome' do pesquisar municipio.
	 */
	private String nome;

	private List<GrupoPerfil> gruposSelecionados;

	private StatusType situacao;
	// ********************************************//

	@PostConstruct
	public void init() {
		setBean(bean);
		carregarGrupos();
		jaExisteMsg = "objeto já existe.";
		setNovoDialogName("dlg-perfil");
		setEditarDialogName("dlg-perfil");
		setDetalhesDialogName("dlg-perfil-detalhes");
	}

	@Override
	public void carregarEntity() {

		try {
			utx.begin();
			Perfil perfil = getEntity();

			if (perfil != null && perfil.getId() != null) {

				perfil = getBean().buscar(perfil.getId());

				// fetch´s Modulo
				if (perfil.getGrupoPerfil() != null) {
					perfil.getGrupoPerfil().getId();
				}

				setEntity(perfil);

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
		setGruposAtivos(grupoBean.getAtivos(getEntity().getGrupoPerfil()));
	}

	@Override
	protected void prePesquisar() {

		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put(ObjetoLocal.PESQUISAR_PARAM_NOME, nome);
		filtros.put(ObjetoLocal.PESQUISAR_PARAM_SITUACAO, situacao);
		filtros.put(ObjetoLocal.PESQUISAR_PARAM_FETCH_MODULO, true);
		filtros.put(ObjetoLocal.PESQUISAR_PARAM_MODULOS, getGruposSelecionados());

		setFiltros(filtros);
	}

	/**
	 * Carrega apenas os módulos ativos
	 */
	public void carregarGruposAtivos() {
		setGruposAtivos(grupoBean.getAtivos());
	}

	/**
	 * Carrega todos os módulos.
	 */
	public void carregarGrupos() {
		setGrupos(grupoBean.buscarTodos());
	}

	@Override
	public String novo() {
		carregarGruposAtivos();
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

	public List<GrupoPerfil> getGruposAtivos() {
		return gruposAtivos;
	}

	public void setGruposAtivos(List<GrupoPerfil> gruposAtivos) {
		this.gruposAtivos = gruposAtivos;
	}

	public List<GrupoPerfil> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<GrupoPerfil> grupos) {
		this.grupos = grupos;
	}

	public List<GrupoPerfil> getGruposSelecionados() {
		return gruposSelecionados;
	}

	public void setGruposSelecionados(List<GrupoPerfil> gruposSelecionados) {
		this.gruposSelecionados = gruposSelecionados;
	}

}
