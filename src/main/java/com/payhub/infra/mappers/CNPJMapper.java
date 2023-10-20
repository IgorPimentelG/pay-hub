package com.payhub.infra.mappers;

public abstract class CNPJMapper {
	public static String format(String cnpj) {
		return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/" +
			cnpj.substring(8, 12) + "-" + cnpj.substring(12);
	}
}
