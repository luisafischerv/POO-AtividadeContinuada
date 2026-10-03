package br.edu.cs.poo.ac.seguro.mediators;

public class ValidadorCpfCnpj {
	private static final int[] PESOS_CNPJ_1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	private static final int[] PESOS_CNPJ_2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

	private ValidadorCpfCnpj() {}

	public static boolean ehCnpjValido(String cnpj) {
		if (!StringUtils.temSomenteNumeros(cnpj) || cnpj.length() != 14 || todosIguais(cnpj)) {
			return false;
		}
		int dv1 = digitoCnpj(cnpj, PESOS_CNPJ_1);
		int dv2 = digitoCnpj(cnpj, PESOS_CNPJ_2);
		return dv1 == cnpj.charAt(12) - '0' && dv2 == cnpj.charAt(13) - '0';
	}

	public static boolean ehCpfValido(String cpf) {
		if (!StringUtils.temSomenteNumeros(cpf) || cpf.length() != 11 || todosIguais(cpf)) {
			return false;
		}
		int dv1 = digitoCpf(cpf, 9);
		int dv2 = digitoCpf(cpf, 10);
		return dv1 == cpf.charAt(9) - '0' && dv2 == cpf.charAt(10) - '0';
	}

	// Calcula o dígito do CPF usando os "qtd" primeiros dígitos, com pesos de qtd+1 até 2.
	private static int digitoCpf(String cpf, int qtd) {
		int soma = 0;
		for (int i = 0; i < qtd; i++) {
			soma += (cpf.charAt(i) - '0') * (qtd + 1 - i);
		}
		int resto = soma % 11;
		return resto < 2 ? 0 : 11 - resto;
	}

	private static int digitoCnpj(String cnpj, int[] pesos) {
		int soma = 0;
		for (int i = 0; i < pesos.length; i++) {
			soma += (cnpj.charAt(i) - '0') * pesos[i];
		}
		int resto = soma % 11;
		return resto < 2 ? 0 : 11 - resto;
	}

	private static boolean todosIguais(String s) {
		for (int i = 1; i < s.length(); i++) {
			if (s.charAt(i) != s.charAt(0)) {
				return false;
			}
		}
		return true;
	}
}
