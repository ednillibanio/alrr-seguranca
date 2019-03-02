package br.leg.rr.al.seguranca.domain;

import java.util.EnumMap;

import br.leg.rr.al.core.jpa.BasicEnumType;

public enum PeriodoExpiraSenha implements BasicEnumType<PeriodoExpiraSenha> {
	NAO_EXPIRA("0", "Não Expira"), TRINTA_DIAS("30", "30 dias"), SESSENTA_DIAS("60", "60 dias"), NOVENTA_DIAS("90",
			"90 dias"), CENTO_E_VINTE_DIAS("120", "120 dias");

	private PeriodoExpiraSenha(String value, String label) {
		this.label = label;
		this.value = value;
	}

	private String label;
	private String value;

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return label;
	}

	@Override
	public EnumMap<PeriodoExpiraSenha, String> getEnumMap() {
		EnumMap<PeriodoExpiraSenha, String> map = new EnumMap<PeriodoExpiraSenha, String>(PeriodoExpiraSenha.class);
		map.put(PeriodoExpiraSenha.NAO_EXPIRA, "0");
		map.put(PeriodoExpiraSenha.TRINTA_DIAS, "30");
		map.put(PeriodoExpiraSenha.SESSENTA_DIAS, "60");
		map.put(PeriodoExpiraSenha.NOVENTA_DIAS, "90");
		map.put(PeriodoExpiraSenha.CENTO_E_VINTE_DIAS, "120");
		return map;
	}
}
