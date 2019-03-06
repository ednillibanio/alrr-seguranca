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
import br.leg.rr.al.seguranca.autorizacao.ejb.ModuloLocal;
import br.leg.rr.al.seguranca.autorizacao.ejb.ObjetoLocal;
import br.leg.rr.al.seguranca.autorizacao.ejb.OperacaoLocal;
import br.leg.rr.al.seguranca.autorizacao.jpa.Modulo;
import br.leg.rr.al.seguranca.autorizacao.jpa.Objeto;
import br.leg.rr.al.seguranca.autorizacao.jpa.Operacao;

@Named
@ViewScoped
public class OperacaoController extends DialogControllerEntityStatus<Operacao, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7882112613545512515L;

	Logger logger = LoggerFactory.getLogger(OperacaoController.class);

	@EJB
	private OperacaoLocal bean;

	@EJB
	private ObjetoLocal objetoBean;

	@EJB
	private ModuloLocal moduloBean;

	private List<Modulo> modulosAtivos;

	private List<Modulo> modulos;

	private List<Objeto> objetosAtivos;

	/**
	 * Campo usado para buscar apenas os objetos deste módulo. Usado no cadastro da
	 * operação.
	 */
	private Modulo modulo;

	// ************ FILTROS DE PESQUISA ************//
	/**
	 * valor do filtro 'nome' do pesquisar municipio.
	 */
	private String nome;

	private List<Objeto> objetosSelecionados;

	private List<Modulo> modulosSelecionados;

	private StatusType situacao;
	// ********************************************//

	@PostConstruct
	public void init() {
		setBean(bean);
		carregarModulos();
		jaExisteMsg = "operação já existe.";
		setNovoDialogName("dlg-operacao");
		setEditarDialogName("dlg-operacao");
		setDetalhesDialogName("dlg-operacao-detalhes");
	}

	@Override
	public String novo() {
		carregarModulosAtivos();
		modulo = null;
		objetosAtivos = null;
		return super.novo();
	}

	/**
	 * Carrega apenas os módulos ativos
	 */
	public void carregarModulosAtivos() {
		modulosAtivos = moduloBean.getAtivos();
	}

	public void onModuloChange() {
		if (modulo != null) {
			objetosAtivos = objetoBean.buscarPorModulo(modulo, StatusType.ATIVO);
		} else {
			objetosAtivos = null;
		}
		getEntity().setObjeto(null);
	}

	/**
	 * Carrega todos os módulos.
	 */
	public void carregarModulos() {
		setModulos(moduloBean.buscarTodos());
	}

	@Override
	public void carregarEntity() {

		try {
			utx.begin();
			Operacao operacao = getEntity();

			if (operacao != null && operacao.getId() != null) {

				operacao = getBean().buscar(operacao.getId());

				// fetch´s Modulo
				if (operacao.getObjeto() != null) {
					operacao.getObjeto().getId();
					operacao.getObjeto().getModulo().getId();
				}

				setEntity(operacao);

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
	protected void preEditar() {
		modulosAtivos = null;
		modulo = null;
		objetosAtivos = null;
	}

	@Override
	protected void posEditar() {

		super.posEditar();
		if (getEntity().getObjeto() != null) {
			carregarObjetosAtivos(getEntity().getObjeto());
			modulosAtivos = moduloBean.getAtivos(getEntity().getObjeto().getModulo());
			modulo = getEntity().getObjeto().getModulo();
		} else {
			carregarModulosAtivos();
		}

	}

	/**
	 * Este método carrega todos os objetos que a situacao seja ativa, e inclui
	 * também mais um objeto caso ele não faça parte da lista de ativos. Esse método
	 * é utilizado pelo {@link #posEditar()} que garante que os objetos do dropdown
	 * sejam carregados de forma correta no momento de exibir o dialog.
	 * 
	 * @param include
	 */
	private void carregarObjetosAtivos(Objeto include) {

		objetosAtivos = objetoBean.buscarPorModulo(modulo, StatusType.ATIVO);
		if (objetosAtivos != null && objetosAtivos.contains(include)) {
			objetosAtivos.add(include);
		}
	}

	@Override
	protected void prePesquisar() {
		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put(OperacaoLocal.PESQUISAR_PARAM_NOME, nome);
		filtros.put(OperacaoLocal.PESQUISAR_PARAM_SITUACAO, situacao);
		filtros.put(OperacaoLocal.PESQUISAR_PARAM_FETCH_MODULO, true);
		filtros.put(OperacaoLocal.PESQUISAR_PARAM_MODULOS, getModulosSelecionados());
		filtros.put(OperacaoLocal.PESQUISAR_PARAM_OBJETOS, getObjetosSelecionados());

		setFiltros(filtros);
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

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public List<Objeto> getObjetosAtivos() {
		return objetosAtivos;
	}

	public void setObjetosAtivos(List<Objeto> objetosAtivos) {
		this.objetosAtivos = objetosAtivos;
	}

	public List<Objeto> getObjetosSelecionados() {
		return objetosSelecionados;
	}

	public void setObjetosSelecionados(List<Objeto> objetosSelecionados) {
		this.objetosSelecionados = objetosSelecionados;
	}

}
