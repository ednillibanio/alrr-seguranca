package br.leg.rr.al.seguranca.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.seguranca.jpa.Usuario;
import br.leg.rr.al.seguranca.jpa.Usuario_;

/**
 * @author ednil
 *
 */
@Named
@Stateless
public class UsuarioPessoaFisicaService extends UsuarioService implements UsuarioPessoaFisicaLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6592653802555497392L;

	@Override
	public Usuario findByPessoaFisica(Long pessoaFisicaId) {
		return null; // buscarEntidade(Sessao.FIND_BY_PESSOA_FISICA_ID, "pessoa_id", pessoaFisicaId);

	}

	@Override
	public List<Usuario> findByPessoaFisica(String nome) {
		return null;// buscarPorNamedQuery(Sessao.FIND_BY_PESSOA_FISICA_NOME, "nome",
		// "%".concat(nome.toUpperCase()).concat("%"));

	}

	// FIXME: Esse metodo está sobrepondo o método de usuário, pois pode ter
	// condição de pessoa.
	// Ver se vai ser isso msm...
	@Override
	public List<Usuario> pesquisar(Usuario usuario, List<StatusType> situacoes) {

		final CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = builder.createQuery(Usuario.class);
		final Root<Usuario> usuarioRoot = cq.from(Usuario.class);
		cq.select(usuarioRoot);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		Predicate login, nome, cpf, predSituacao;

		if (!StringUtils.isBlank(usuario.getLogin())) {

			login = builder.like(builder.upper(usuarioRoot.get(Usuario_.login)),
					"%" + usuario.getLogin().toUpperCase() + "%");
			predicateList.add(login);
		}
		if (situacoes != null && (situacoes.size() > 0)) {

			predSituacao = usuarioRoot.get(Usuario_.situacao).in(situacoes.toArray());
			predicateList.add(predSituacao);
		}

		/**
		 * FIXME: Foi removido, pq usuario nao vai mais ser associado bidirecional com
		 * pessoa. Somente pessoa com usuario.
		 */
		/*
		 * if (usuario.getPessoaFisica() != null) { Join<Usuario, PessoaFisica> pf =
		 * usuarioRoot.join(Usuario_.pessoaFisica);
		 * 
		 * if (!StringUtils.isBlank(usuario.getPessoaFisica().getNome())) { nome =
		 * builder.like(builder.upper(pf.get(PessoaFisica_.nome)), "%" +
		 * usuario.getPessoaFisica().getNome().toUpperCase() + "%");
		 * predicateList.add(nome); } if
		 * (!StringUtils.isBlank(usuario.getPessoaFisica().getCpf())) { cpf =
		 * builder.like(pf.get(PessoaFisica_.cpf), "%" +
		 * usuario.getPessoaFisica().getCpf() + "%"); predicateList.add(cpf); } }
		 */
		if (predicateList.size() > 0) {
			cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		}
		return getEntityManager().createQuery(cq).getResultList();

	}

}
