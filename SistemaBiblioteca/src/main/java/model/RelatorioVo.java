package model;

import java.util.Date;


public class RelatorioVo  {

	private Long id;
	
	private String nomeLivro;
	
	private String nomeUsuario;

	private Date dataDeEmprestimo;

	private Date dataDeDevolucao;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNomeLivro() {
		return nomeLivro;
	}


	public void setNomeLivro(String nomeLivro) {
		this.nomeLivro = nomeLivro;
	}


	public String getNomeUsuario() {
		return nomeUsuario;
	}


	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}


	public Date getDataDeEmprestimo() {
		return dataDeEmprestimo;
	}


	public void setDataDeEmprestimo(Date dataDeEmprestimo) {
		this.dataDeEmprestimo = dataDeEmprestimo;
	}


	public Date getDataDeDevolucao() {
		return dataDeDevolucao;
	}


	public void setDataDeDevolucao(Date dataDeDevolucao) {
		this.dataDeDevolucao = dataDeDevolucao;
	}


	
}
