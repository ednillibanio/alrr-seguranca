package br.leg.rr.al.seguranca.ejb;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped()
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
// TODO: Rascunho de classe que verifica se usuário já autenticou-se. Seguranca
// para permitir apenas um login ativo com sessao.
public class LoggedInService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8666305951497313308L;

	private Hashtable<String, String> users;

	@SuppressWarnings("rawtypes")
	private ConcurrentHashMap hits = null;

	@SuppressWarnings("rawtypes")
	@PostConstruct
	public void initialize() {
		this.hits = new ConcurrentHashMap();
	}

	public void add(String login, String sessionId) {

		users.put(login, sessionId);
	}

	public void remove(String login) {
		users.remove(login);
	}
}
