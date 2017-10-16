package controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import dao.impl.LivroDao;
import model.Livro;

@RequestScoped
@Named
public class LivroBean {
	
	@Inject
	private LivroDao dao;

	private List<Livro> list;

	private String nomeLivroFiltrada;

	private Livro livro;

	@PostConstruct
	public void init() {
		list = new ArrayList<Livro>();
	}

	public String getNomeLivroFiltrada() {
		return nomeLivroFiltrada;
	}

	public void setNomeLivroFiltrada(String nomeLivroFiltrada) {
		this.nomeLivroFiltrada = nomeLivroFiltrada;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Livro> getList() {
		return list;
	}

	public String novo() {
		livro = new Livro();
		return "cadastrarLivro";
	}

	public void salvar() {
		if (!dao.save(livro)) {
			adicionarMensagem("Erro ao cadastrar o Livro.", FacesMessage.SEVERITY_ERROR);
		}

		adicionarMensagem("Livro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
	}

	public String editar() {
		livro = dao.findById(1L);
		return "cadastrarLivro";
	}

	public void remover(Livro livro) {
		if (!dao.delete(livro.getId())) {
			adicionarMensagem("Erro ao remover o Livro.", FacesMessage.SEVERITY_ERROR);
		}

		adicionarMensagem("Livro removido com sucesso.", FacesMessage.SEVERITY_INFO);
	}

	public void listarLivro() {
		if (!nomeLivroFiltrada.isEmpty()) {
			list.addAll(dao.findByName(nomeLivroFiltrada));
		} else {
			list.addAll(dao.findAll());
		}
	}

	public void adicionarMensagem(String mensagem, Severity tipoMensagem) {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage fm = new FacesMessage(mensagem);
		fm.setSeverity(tipoMensagem);
		fc.addMessage(null, fm);

	}
}
