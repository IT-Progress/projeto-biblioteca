package model;

public enum SituacaoLivro {
	RESERVADO("Reservado"),
	DISPON�VEL("Dispon�vel"),
	INDISPON�VEL("Indispon�vel");
	
	private String situacaoLivro;
	
	private SituacaoLivro(String situacaoLivro) {
		this.situacaoLivro = situacaoLivro;
	}
	
	public String getSituacaoLivros() {
		return situacaoLivro;
	}
}
