package br.edu.cs.poo.ac.seguro.entidades;

public enum TipoSinistro {
	COLISAO(1, "Colis\u00e3o"),
	INCENDIO(2, "Inc\u00eandio"),
	FURTO(3, "Furto"),
	ENCHENTE(4, "Enchente"),
	DEPREDACAO(5, "Depreda\u00e7\u00e3o");

    private int codigo;
    private String nome;

    private TipoSinistro(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public static TipoSinistro getTipoSinistro(int codigo){
        for(TipoSinistro tipo : TipoSinistro.values()){
            if(tipo.getCodigo() == codigo){
                return tipo;
            }
        }
        return null;
    }
}