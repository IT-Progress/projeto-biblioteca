package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.impl.LivroDao;
import dao.impl.RelatorioDao;
import model.CategoriaLivro;
import model.Livro;
import model.Relatorio;
import model.SituacaoLivro;
import model.Upload;
import model.Usuario;

@ApplicationScoped
@Named
public class LivroBean {

	@Inject
	private LivroDao dao;

	private List<Livro> list;

	private String nomeLivroFiltrada;

	private Livro livro;
	
	private String nomeLivroPesquisaGeral;

	private Usuario usuario;
	
	private List<Relatorio> listRelatorio;
	
	@Inject
	private RelatorioDao daoRelatorio;
	
	private List<Usuario> listUsuario;

	private String textoBotao;
	
	

	public String getNomeLivroPesquisaGeral() {
		return nomeLivroPesquisaGeral;
	}

	public void setNomeLivroPesquisaGeral(String nomeLivroPesquisaGeral) {
		this.nomeLivroPesquisaGeral = nomeLivroPesquisaGeral;
	}

	public String getTextoBotao() {
		if(livro.getSituacao() == SituacaoLivro.DISPONÍVEL) {
			textoBotao = "Alugar";
		} else {
			textoBotao = "Reservar";
		}
		
		return textoBotao;
	}

	public void setTextoBotao(String textoBotao) {
		this.textoBotao = textoBotao;
	}


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
		listUsuario = new ArrayList<Usuario>();
		listRelatorio = new ArrayList<Relatorio>();
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
		livro.setSituacao(SituacaoLivro.DISPONÍVEL);
		return "cadastrarLivro";
	}

	public String salvar() {
		if (!dao.save(livro)) {
			adicionarMensagem("Erro ao cadastrar o Livro.", FacesMessage.SEVERITY_ERROR);
		} else {

			adicionarMensagem("Livro salvo com sucesso.", FacesMessage.SEVERITY_INFO);
			listarLivro();
		}
		return "livro";
	}

	public String editar(Livro livro) {
		this.livro = dao.findById(livro.getId());
		return "editarLivro";
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
	
	

	public String pesquisar() {
		zerarLista();
		if (!nomeLivroPesquisaGeral.isEmpty()) {
			list.addAll(dao.findByString(nomeLivroPesquisaGeral));
			nomeLivroPesquisaGeral = null;
			return "livro";	
		} else {
			list.addAll(dao.findAll());
			nomeLivroPesquisaGeral = null;
			return "livro";
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

	public String formatarTexto(String nomeColuna) {
		if (nomeColuna.length() == 0)
			return "";

		if (nomeColuna.length() == 1)
			return nomeColuna.toUpperCase();

		return nomeColuna.substring(0, 1).toUpperCase() + nomeColuna.substring(1).toLowerCase();
	}
	

	
	public void alugar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(true);
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		dao.alugar(this.livro, usuario);

			adicionarMensagem("Sucesso", FacesMessage.SEVERITY_INFO);
			listarRelatorio();
		
		//return "relatorio";
	}
	
	public void listarRelatorio() {
		zerarLista();
		
			listRelatorio.addAll(daoRelatorio.findAllPersonalizado());
		
	}
	
	public String executarBotao(SituacaoLivro situacao) {
		if(livro.getSituacao() == SituacaoLivro.DISPONÍVEL) {			
			alugar();
			return "livro";
		} else {
			return "livro";
		}
		
		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Relatorio> getListRelatorio() {
		return listRelatorio;
	}

	public void setListRelatorio(List<Relatorio> listRelatorio) {
		this.listRelatorio = listRelatorio;
	}

	public List<Usuario> getListUsuario() {
		return listUsuario;
	}

	public void setListUsuario(List<Usuario> listUsuario) {
		this.listUsuario = listUsuario;
	}

	public void setList(List<Livro> list) {
		this.list = list;
	}
	
	
}
