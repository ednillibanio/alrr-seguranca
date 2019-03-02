package br.leg.rr.al.seguranca.domain;

import java.io.Serializable;

public class PermissaoTreeNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1980444424199413422L;

	private String id;
	private String nome;
	private String descricao;
	private Object entidade;

	public PermissaoTreeNode() {

	}

	public PermissaoTreeNode(String id, String nome, String descricao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}

	public PermissaoTreeNode(String id, String nome, String descricao, Object entidade) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.setEntidade(entidade);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Object getEntidade() {
		return entidade;
	}

	public void setEntidade(Object entidade) {
		this.entidade = entidade;
	}

}
