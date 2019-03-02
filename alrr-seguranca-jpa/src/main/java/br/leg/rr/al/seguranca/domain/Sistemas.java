package br.leg.rr.al.seguranca.domain;

import java.util.EnumMap;

import br.leg.rr.al.core.jpa.BasicEnum;

/**
 * Lista dos Sistemas que são gerenciados pelo Sistema de Gerenciamento dos
 * Sistemas e Segurança.
 * 
 */
public enum Sistemas implements BasicEnum<Sistemas> {

	AGENDA_DEPUTADO("Agenda Deputados", "sistema-agenda"), GERENCIAMENTO_SISTEMAS_E_SEGURANCA(
			"Gerenciamento dos Sistemas e de Segurança",
			"sistema-acl"), SISTEMA_COMUNS("Sistema Básico", "sistema-commons");

	private Sistemas(String label, String outcome) {
		this.label = label;
		this.outcome = outcome;

	}

	private String label;

	private String outcome;

	@Override
	public EnumMap<Sistemas, String> getEnumMap() {
		EnumMap<Sistemas, String> map = new EnumMap<Sistemas, String>(Sistemas.class);
		map.put(Sistemas.SISTEMA_COMUNS, "1");
		map.put(Sistemas.GERENCIAMENTO_SISTEMAS_E_SEGURANCA, "2");
		map.put(Sistemas.AGENDA_DEPUTADO, "2");

		return map;
	}

	@Override
	public String getLabel() {
		return label;
	}

	public String getOutcome() {
		return outcome;
	}
}
