package br.edu.cs.poo.ac.seguro.testes;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class TesteApoliceDAO extends TesteDAO {
	private ApoliceDAO dao = new ApoliceDAO();

	protected Class getClasse() {
		return Apolice.class;
	}

	// O número não faz parte do construtor, então é definido pelo set.
	private Apolice criarApolice(String numero, double premio) {
		Veiculo veiculo = new Veiculo("ABC1234", 2022, null, null, CategoriaVeiculo.INTERMEDIARIO);
		Apolice apolice = new Apolice(veiculo, new BigDecimal("1500.00"), new BigDecimal(premio),
				new BigDecimal("80000.00"));
		apolice.setNumero(numero);
		return apolice;
	}

	@Test
	public void teste01() {
		String numero = "00000000";
		cadastro.incluir(criarApolice(numero, 1000.0), numero);
		Apolice apo = dao.buscar(numero);
		Assertions.assertNotNull(apo);
	}

	@Test
	public void teste02() {
		String numero = "10000000";
		cadastro.incluir(criarApolice(numero, 1001.0), numero);
		Apolice apo = dao.buscar("11000000");
		Assertions.assertNull(apo);
	}

	@Test
	public void teste03() {
		String numero = "20000000";
		cadastro.incluir(criarApolice(numero, 1002.0), numero);
		boolean ret = dao.excluir(numero);
		Assertions.assertTrue(ret);
		Assertions.assertNull(dao.buscar(numero));
	}

	@Test
	public void teste04() {
		String numero = "30000000";
		cadastro.incluir(criarApolice(numero, 1003.0), numero);
		boolean ret = dao.excluir("31000000");
		Assertions.assertFalse(ret);
		Assertions.assertNotNull(dao.buscar(numero));
	}

	@Test
	public void teste05() {
		String numero = "40000000";
		boolean ret = dao.incluir(criarApolice(numero, 1004.0));
		Assertions.assertTrue(ret);
		Apolice apo = dao.buscar(numero);
		Assertions.assertNotNull(apo);
	}

	@Test
	public void teste06() {
		String numero = "50000000";
		Apolice apo = criarApolice(numero, 1005.0);
		cadastro.incluir(apo, numero);
		boolean ret = dao.incluir(apo);
		Assertions.assertFalse(ret);
	}

	@Test
	public void teste07() {
		String numero = "60000000";
		boolean ret = dao.alterar(criarApolice(numero, 1006.0));
		Assertions.assertFalse(ret);
		Apolice apo = dao.buscar(numero);
		Assertions.assertNull(apo);
	}

	@Test
	public void teste08() {
		String numero = "70000000";
		cadastro.incluir(criarApolice(numero, 1007.0), numero);
		boolean ret = dao.alterar(criarApolice(numero, 2008.0));
		Assertions.assertTrue(ret);
		Apolice apo = dao.buscar(numero);
		Assertions.assertEquals(2008.0, apo.getValorPremio().doubleValue());
	}
}
