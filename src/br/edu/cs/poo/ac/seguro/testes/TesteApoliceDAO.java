package br.edu.cs.poo.ac.seguro.testes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import junit.framework.Assert;

public class TesteApoliceDAO extends TesteDAO {
	private ApoliceDAO dao = new ApoliceDAO();
	
	protected Class getClasse() {
		return Apolice.class;
	}
	
	@Test
	public void teste01() {
		String numero = "00000000";
		Apolice apo = new Apolice(null,BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
		apo.setNumero(numero);
		cadastro.incluir(apo, numero);
		Apolice achado = dao.buscar(numero);
		Assertions.assertNotNull(achado);
	}
	
	@Test
	public void teste02() {
		String numero = "10000000";
		Apolice apo = new Apolice (null, BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
		apo.setNumero(numero);
		cadastro.incluir(apo, numero);
		Apolice achado = dao.buscar("11000000");
		Assertions.assertNull(achado);
	}
	
	@Test
	public void teste03() {
		String numero = "20000000";
		Apolice apo = new Apolice (null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
		apo.setNumero(numero);
		cadastro.incluir(apo, numero);
		boolean ret = dao.excluir("20000000");
		Assertions.assertTrue(ret);
		
	}
	
	@Test
	public void teste04() {
		String numero = "30000000";
		Apolice apo = new Apolice (null,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
		apo.setNumero(numero);
		cadastro.incluir(apo, numero);
		boolean ret = dao.excluir("31000000");
		Assertions.assertFalse(ret);
	}
	
	@Test 
	public void teste05() {
		String numero = "40000000";
		Apolice apo = new Apolice (null,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
		apo.setNumero(numero);
		boolean ret = dao.incluir(apo);
		Assertions.assertTrue(ret);
		Apolice achado = dao.buscar(numero);
		Assertions.assertNotNull(achado);
	}
	
	@Test
	public void teste06() {
		String numero = "50000000";
		Apolice apo = new Apolice (null, BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
		apo.setNumero(numero);
		cadastro.incluir(apo, numero);
		boolean ret = dao.incluir(apo);
		Assertions.assertFalse(ret);
	}
	
	@Test
	public void teste07() {
		String numero = "60000000";
		Apolice apo = new Apolice (null, BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
		apo.setNumero(numero);
		boolean ret = dao.alterar(apo);
		Assertions.assertFalse(ret);
		Apolice achado = dao.buscar(numero);
		Assertions.assertNull(achado);
	}
	
	@Test
	public void teste08() {
		String numero = "70000000";
		Apolice apo = new Apolice (null, BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
		apo.setNumero(numero);
		cadastro.incluir(apo, numero);
		apo = new Apolice (null, new BigDecimal("100"), BigDecimal.ZERO, BigDecimal.ZERO);
		apo.setNumero(numero);
		boolean ret = dao.alterar(apo);
		Assertions.assertTrue(ret);
	}
}
