package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import dao.impl.LivroDao;
import model.CategoriaLivro;
import model.Livro;
import model.SituacaoLivro;
import model.Upload;

@ApplicationScoped
@Named
public class LivroBean {

	@Inject
	private LivroDao dao;

	private List<Livro> list;

	private String nomeLivroFiltrada;

	private Livro livro;

	public CategoriaLivro[] getCategorias() {
		return CategoriaLivro.values();
	}

	public SituacaoLivro[] getSituacoesLivro() {
		return SituacaoLivro.values();
	}

	@PostConstruct
	public void init() {
		list = new ArrayList<Livro>();
	}

	private void zerarLista() {
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

	public String salvar() {
		if (!dao.save(livro)) {
			adicionarMensagem("Erro ao cadastrar o Livro.", FacesMessage.SEVERITY_ERROR);
		} else {

			adicionarMensagem("Livro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
			nomeLivroFiltrada = this.livro.getNome();
			listarLivro();
		}
		return "livro";
	}

	public String editar(Livro livro) {
		this.livro = dao.findById(livro.getId());
		return "cadastrarLivro";
	}

	public String visualizar(Livro livro) {
		this.livro = dao.findById(livro.getId());
		return "exibirLivro";
	}

	public String remover(Livro livro) {
		if (!dao.delete(livro.getId())) {
			adicionarMensagem("Erro ao remover o Livro.", FacesMessage.SEVERITY_ERROR);
		} else {

			adicionarMensagem("Livro removido com sucesso.", FacesMessage.SEVERITY_INFO);
			listarLivro();
		}
		return "livro";
	}

	public void listarLivro() {
		zerarLista();
		if (!nomeLivroFiltrada.isEmpty()) {
			list.addAll(dao.findByString(nomeLivroFiltrada));
		} else {
			list.addAll(dao.findAll());
		}
	}

	public String listarLivroComputacao() {
		zerarLista();
		list.addAll(dao.findByComputacao());
		return "livro";
	}

	public String listarLivroAdministracao() {
		zerarLista();
		list.addAll(dao.findByAdministracao());
		return "livro";
	}

	public String listarLivroOutros() {
		zerarLista();
		list.addAll(dao.findByOutros());
		return "livro";
	}

	public String listarLivroTodos() {
		zerarLista();
		list.addAll(dao.findAll());
		return "livro";
	}

	public void adicionarMensagem(String mensagem, Severity tipoMensagem) {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage fm = new FacesMessage(mensagem);
		fm.setSeverity(tipoMensagem);
		fc.addMessage(null, fm);

	}

	public String formatarNomeColuna(String nomeColuna) {
		if (nomeColuna.length() == 0)
			return "";
		
		if (nomeColuna.length() == 1)
			return nomeColuna.toUpperCase();
		
		return nomeColuna.substring(0, 1).toUpperCase() + nomeColuna.substring(1).toLowerCase();
	}

}
