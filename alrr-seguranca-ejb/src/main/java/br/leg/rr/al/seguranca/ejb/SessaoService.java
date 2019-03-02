package br.leg.rr.al.seguranca.ejb;

import java.util.List;

import br.leg.rr.al.core.dao.BaseJPADaoStatus;
import br.leg.rr.al.seguranca.jpa.Sessao;

/**
 * @author ednil
 *
 */
public class SessaoService extends BaseJPADaoStatus<Sessao, Integer> implements SessaoLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4310792817122898660L;

	/**
	 * TODO: Mover esses metodos de pesquisar pessoa para outra classe.
	 */
	@Override
	public Sessao findByPessoaFisica(Long pessoaFisicaId) {
		return null;// buscarEntidade(Sessao.FIND_BY_PESSOA_FISICA_ID, "pessoa_id", pessoaFisicaId);

	}

	@Override
	public List<Sessao> findByPessoaFisica(String nome) {
		return null;
		/*
		 * return buscarPoNamedQuery(Sessao.FIND_BY_PESSOA_FISICA_NOME, "nome",
		 * "%".concat(nome.toUpperCase()).concat("%"));
		 */
	}

	@Override
	public Boolean jaExiste(Sessao entidade) {
		return false;
	}
}
