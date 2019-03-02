package br.leg.rr.al.seguranca.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.leg.rr.al.core.dao.BeanException;

@Named
@Stateless
// FIXME: falta terminar essa classe.
public class AutenticacaoService implements AutenticacaoLocal {

	private static final Logger log = LoggerFactory.getLogger(AutenticacaoService.class);

	@EJB
	private UsuarioLocal usuarioBean;

	@Override
	public void autenticar(String login, String senha) throws BeanException {

		// FIXME: falta o tratamento caso já esteja autenticado. Ver depois isso

	}

	@Override
	public void sair() {

	}

}
