package br.edu.cs.poo.ac.seguro.testes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;





public class TesteSinistroDAO extends TesteDAO {
	private SinistroDAO dao = new SinistroDAO();
	
	protected Class getClasse() {
		return Sinistro.class;
	}
	
	@Test 
	public void teste01() {
		String numero = "00000000";
		Sinistro sin = new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), "usuario1", BigDecimal.ZERO, TipoSinistro.COLISAO);
		sin.setNumero(numero);
		cadastro.incluir(sin, numero);
		Sinistro achado = dao.buscar(numero);
		Assertions.assertNotNull(achado);
	}
	
	@Test
	public void teste02(){
		String numero = "10000000";
		Sinistro sin = new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), "usuario2", BigDecimal.ZERO, TipoSinistro.INCENDIO);
		sin.setNumero(numero);
		cadastro.incluir(sin, numero);
		Sinistro achado = dao.buscar("11000000");
		Assertions.assertNull(achado);
	}
	
	@Test 
	public void teste03() {
		String numero = "20000000";
		Sinistro sin = new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), "usuario03", BigDecimal.ZERO, TipoSinistro.DEPREDACAO);
		sin.setNumero(numero);
		cadastro.incluir(sin, numero);
		boolean ret = dao.excluir("20000000");
		Assertions.assertTrue(ret);		
	}
	
	@Test 
	public void teste04(){
		String numero = "30000000";
		Sinistro sin = new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), "usuario04", BigDecimal.ZERO, TipoSinistro.ENCHENTE);
		sin.setNumero(numero);
		cadastro.incluir(sin, numero);
		boolean ret = dao.excluir("31000000");
		Assertions.assertFalse(ret);		
	}
	
	@Test 
	public void teste05() {
		String numero = "40000000";
		Sinistro sin = new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), "usuario05", BigDecimal.ZERO, TipoSinistro.FURTO);
		sin.setNumero(numero);
		boolean ret = dao.incluir(sin);
		Assertions.assertTrue(ret);
		Sinistro achado = dao.buscar(numero);
		Assertions.assertNotNull(achado);
	}
	
	@Test 
	public void teste06() {
		String numero = "50000000";
		Sinistro sin = new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), "usuario06", BigDecimal.ZERO, TipoSinistro.COLISAO);
		sin.setNumero(numero);
		cadastro.incluir(sin, numero);
		boolean ret = dao.incluir(sin);
		Assertions.assertFalse(ret);
	}
	
	@Test
	public void teste07() {
		String numero = "60000000";
		Sinistro sin = new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), "usuario07", BigDecimal.ZERO, TipoSinistro.DEPREDACAO);
		sin.setNumero(numero);
		boolean ret = dao.alterar(sin);
		Assertions.assertFalse(ret);
		Sinistro achado = dao.buscar(numero);
		Assertions.assertNull(achado);
	}
	
	@Test
	public void teste08() {
		String numero = "70000000";
		Sinistro sin = new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), "usuario08", BigDecimal.ZERO, TipoSinistro.ENCHENTE);
		sin.setNumero(numero);
		cadastro.incluir(sin, numero);
		sin = new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), "usuarioTeste", BigDecimal.ZERO, TipoSinistro.INCENDIO);
		sin.setNumero(numero);
		boolean ret = dao.alterar(sin);
		Assertions.assertTrue(ret);
	}
}
