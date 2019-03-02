package br.leg.rr.al.seguranca.domain;

import java.util.EnumMap;

import br.leg.rr.al.core.jpa.BasicEnum;

/**
 * Tipo de usuário
 * 
 */
public enum UsuarioType implements BasicEnum<UsuarioType> {

	SERVIDOR_ALE("Servidor Público",
			"A pessoa deve ser um servidor público efetivo ou comissionado da Assembleia Legislativa de Roraima."), PARLAMENTAR(
					"Parlamentar", "Qualquer pessoa que seja um Deputado Estadual."), EXTERNO("Externo",
							"Qualquer pessoa que não seja servidor efetivo/comissionado da Assembleia Legislativa."), SISTEMA(
									"Sistema",
									"O usuário não está associado a nenhuma pessoa e é executado em rotinas ou procedimentos do sistema/servidor.");

	private UsuarioType(String label, String descricao) {
		this.setLabel(label);
		this.setDescricao(descricao);
	}

	private String label;
	private String descricao;

	@Override
	public EnumMap<UsuarioType, String> getEnumMap() {
		EnumMap<UsuarioType, String> map = new EnumMap<UsuarioType, String>(UsuarioType.class);
		map.put(UsuarioType.SERVIDOR_ALE, "1");
		map.put(UsuarioType.EXTERNO, "2");
		map.put(UsuarioType.SISTEMA, "3");
		map.put(UsuarioType.PARLAMENTAR, "4");

		return map;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
