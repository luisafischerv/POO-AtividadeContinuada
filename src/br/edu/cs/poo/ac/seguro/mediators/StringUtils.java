package br.edu.cs.poo.ac.seguro.mediators;

public class StringUtils {
	private StringUtils() {}

	public static boolean ehNuloOuBranco(String str) {
		return str == null || str.trim().isEmpty();
	}

	// Retorna true somente se a string não é vazia e todos os caracteres são dígitos de 0 a 9.
	public static boolean temSomenteNumeros(String input) {
		if (input == null || input.isEmpty()) {
			return false;
		}
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}
}
