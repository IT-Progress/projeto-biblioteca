package model;

public enum SituacaoLivro {
	RESERVADO("Reservado"),
	DISPONÍVEL("Disponível"),
	INDISPONÍVEL("Indisponível");
	
	private String situacaoLivro;
	
	private SituacaoLivro(String situacaoLivro) {
		this.situacaoLivro = situacaoLivro;
	}
	
	public String getSituacaoLivros() {
		return situacaoLivro;
	}
}
