package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class SeguradoEmpresaMediator {
	private static SeguradoEmpresaMediator instancia;

	private SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();
	private SeguradoEmpresaDAO dao = new SeguradoEmpresaDAO();

	private SeguradoEmpresaMediator() {}

	public static SeguradoEmpresaMediator getInstancia() {
		if (instancia == null) {
			instancia = new SeguradoEmpresaMediator();
		}
		return instancia;
	}

	public String validarCnpj(String cnpj) {
		if (StringUtils.ehNuloOuBranco(cnpj)) {
			return "CNPJ deve ser informado";
		}
		if (cnpj.length() != 14) {
			return "CNPJ deve ter 14 caracteres";
		}
		if (!ValidadorCpfCnpj.ehCnpjValido(cnpj)) {
			return "CNPJ com dígito inválido";
		}
		return null;
	}

	public String validarFaturamento(double faturamento) {
		if (faturamento <= 0) {
			return "Faturamento deve ser maior que zero";
		}
		return null;
	}

	public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
		String msg = validarSeguradoEmpresa(seg);
		if (msg != null) {
			return msg;
		}
		if (!dao.incluir(seg)) {
			return "CNPJ do segurado empresa já existente";
		}
		return null;
	}

	public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
		String msg = validarSeguradoEmpresa(seg);
		if (msg != null) {
			return msg;
		}
		if (!dao.alterar(seg)) {
			return "CNPJ do segurado empresa não existente";
		}
		return null;
	}

	public String excluirSeguradoEmpresa(String cnpj) {
		if (!dao.excluir(cnpj)) {
			return "CNPJ do segurado empresa não existente";
		}
		return null;
	}

	public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
		return dao.buscar(cnpj);
	}

	// A ordem das validações segue os testes: nome, endereço, data, CNPJ e faturamento.
	public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
		if (seg == null) {
			return "Segurado empresa deve ser informado";
		}
		String msg = seguradoMediator.validarNome(seg.getNome());
		if (msg != null) {
			return msg;
		}
		msg = seguradoMediator.validarEndereco(seg.getEndereco());
		if (msg != null) {
			return msg;
		}
		msg = seguradoMediator.validarDataCriacao(seg.getDataAbertura());
		if (msg != null) {
			// a mensagem do SeguradoMediator fala em "criação"; aqui o termo correto é "abertura"
			return msg.replace("Data da criação", "Data da abertura");
		}
		msg = validarCnpj(seg.getCnpj());
		if (msg != null) {
			return msg;
		}
		return validarFaturamento(seg.getFaturamento());
	}
}
