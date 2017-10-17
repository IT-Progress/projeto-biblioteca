package model;

public enum CategoriaLivro {
	COMPUTAÇÃO("Computação"),
	ADMINISTRAÇÃO("Administração"),
	OUTROS("Outros");
	
	private String categoria;
	
	private CategoriaLivro(String categoria) {
		this.categoria = categoria;
	}
	
	
	public String getCategoria() {
		return categoria;
	}
}
