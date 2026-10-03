package br.edu.cs.poo.ac.seguro.entidades;
import static br.edu.cs.poo.ac.seguro.entidades.PrecosAnosCategoria.*;
public enum CategoriaVeiculo {
	
	
	BASICO(1,"Ve\u00edculo econ\u00f4mico", PA_BASICO),
	INTERMEDIARIO(2,"Ve\u00edculo de categoria m\u00e9dia", PA_INTERMEDIARIO),
	LUXO(3, "Ve\u00edculo de luxo", PA_LUXO),
	SUPER_LUXO(4, "Ve\u00edculo exclusivo", PA_SUPER_LUXO),
	ESPORTIVO(5, "Ve\u00edculo esportivo", PA_ESPORTIVO);
		
	private int codigo;
	private String nome;
	private PrecoAno[] precosAnos;
	private CategoriaVeiculo(int codigo, String nome, PrecoAno[] precosAnos) {
		this.codigo = codigo;
		this.nome = nome;
		this.precosAnos = precosAnos;
	}
	public int getCodigo() {
		return codigo;
	}
	public String getNome() {
		return nome;
	}
	public PrecoAno[] getPrecosAnos() {
		return precosAnos;
	}
	public static CategoriaVeiculo obterCategoria(int codigo) {
		for (CategoriaVeiculo cat : CategoriaVeiculo.values()) {
			if (cat.getCodigo() == codigo) {
				return cat;
			}
		}
		return null;
	}
}