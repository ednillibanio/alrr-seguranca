package br.leg.rr.al.seguranca.ejb;

import java.util.List;

import br.leg.rr.al.seguranca.jpa.Usuario;

public interface UsuarioPessoaFisicaLocal extends UsuarioLocal {

	Usuario findByPessoaFisica(Long pessoaFisicaId);

	List<Usuario> findByPessoaFisica(String nome);

}
