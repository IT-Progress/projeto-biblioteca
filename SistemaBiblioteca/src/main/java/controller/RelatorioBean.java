package controller;

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
import dao.impl.RelatorioDao;
import dao.impl.UsuarioDao;
import model.Livro;
import model.Relatorio;
import model.Usuario;

@ApplicationScoped
@Named
public class RelatorioBean {
	
	@Inject
	private UsuarioDao daoUsuario;
	
	@Inject
	private RelatorioDao daoRelatorio;

	@Inject
	private LivroDao daoLivro;
	
	private List<Usuario> listUsuario;
	
	private List<Livro> listLivro;

	private String nomeUsuarioFiltrado;

	private Usuario usuario;
	
	private Livro livro;
	
	private Relatorio relatorio;
	
	private List<Relatorio> listRelatorio;
	
	private Long idFiltrado;

	@PostConstruct
	public void init() {
		zerarLista();
		carregarLivro();
		carregarUsuario();
	}
	
	public void carregarLivro() {
		listLivro = daoLivro.findAll();
		listUsuario = daoUsuario.findAll();
	}
	
	public void carregarUsuario() {
		
	}

	private void zerarLista() {
		listUsuario = new ArrayList<Usuario>();
	}

	public String getNomeUsuarioFiltrado() {
		return nomeUsuarioFiltrado;
	}

	public void setNomeUsuarioFiltrado(String nomeUsuarioFiltrado) {
		this.nomeUsuarioFiltrado = nomeUsuarioFiltrado;
	}

	public List<Usuario> getList() {
		return listUsuario;
	}

	public void setList(List<Usuario> list) {
		this.listUsuario = list;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String novo() {
		usuario = new Usuario();
		return "cadastrarUsuario";
	}
	
	public List<Usuario> getListUsuario() {
		return listUsuario;
	}

	public void setListUsuario(List<Usuario> listUsuario) {
		this.listUsuario = listUsuario;
	}

	public List<Livro> getListLivro() {
		return listLivro;
	}

	public void setListLivro(List<Livro> listLivro) {
		this.listLivro = listLivro;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Relatorio getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}
	
	public List<Relatorio> getListRelatorio() {
		return listRelatorio;
	}

	public void setListRelatorio(List<Relatorio> listRelatorio) {
		this.listRelatorio = listRelatorio;
	}
	
	

	public String salvar() {
		if (!daoUsuario.save(usuario)) {
			adicionarMensagem("Erro ao cadastrar o Usuário.", FacesMessage.SEVERITY_ERROR);
		} else {

			adicionarMensagem("Usuário salvo com sucesso.", FacesMessage.SEVERITY_INFO);
			nomeUsuarioFiltrado = this.usuario.getNome();
			listarUsuario();
		}
		return "usuario";
	}


	public String editar(Usuario usuario) {
		this.usuario = daoUsuario.findById(usuario.getId());
		return "cadastrarUsuario";
	}

	public String remover(Usuario usuario) {
		if (!daoUsuario.delete(usuario.getId())) {
			adicionarMensagem("Erro ao remover o Usuário.", FacesMessage.SEVERITY_ERROR);
		} else {

			adicionarMensagem("Usuário removido com sucesso.", FacesMessage.SEVERITY_INFO);
			listarUsuario();
		}
		return "usuario";
	}

	public void listarUsuario() {
		zerarLista();
		if (!nomeUsuarioFiltrado.isEmpty()) {
			listUsuario.addAll(daoUsuario.findByName(nomeUsuarioFiltrado));
		} else {
			listUsuario.addAll(daoUsuario.findAll2());
		}
	}
	
	public Long getIdFiltrado() {
		return idFiltrado;
	}

	public void setIdFiltrado(Long idFiltrado) {
		this.idFiltrado = idFiltrado;
	}

	public void listarRelatorio() {
		zerarLista();
		if(!idFiltrado.toString().isEmpty()) {
			listRelatorio.add(daoRelatorio.findById(idFiltrado));
		}
			listRelatorio.addAll(daoRelatorio.findAll());
	}

	public void adicionarMensagem(String mensagem, Severity tipoMensagem) {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage fm = new FacesMessage(mensagem);
		fm.setSeverity(tipoMensagem);
		fc.addMessage(null, fm);

	}
	
}
