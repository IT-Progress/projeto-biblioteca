package model;

public enum CategoriaLivro {
	COMPUTA��O("Computa��o"),
	ADMINISTRA��O("Administra��o"),
	OUTROS("Outros");
	
	private String categoria;
	
	private CategoriaLivro(String categoria) {
		this.categoria = categoria;
	}
	
	
	public String getCategoria() {
		return categoria;
	}
}
