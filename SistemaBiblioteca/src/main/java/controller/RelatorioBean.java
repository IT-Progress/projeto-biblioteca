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

import dao.impl.UsuarioDao;
import model.Livro;
import model.Usuario;

@ApplicationScoped
@Named
public class RelatorioBean {
	
	@Inject
	private UsuarioDao dao;

	private List<Usuario> listUsuario;
	
	private List<Livro> listLivro;

	private String nomeUsuarioFiltrado;

	private Usuario usuario;
	
	private Livro livro;

	@PostConstruct
	public void init() {
		usuario = new Usuario();
		listUsuario = new ArrayList<Usuario>();
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

	public String salvar() {
		if (!dao.save(usuario)) {
			adicionarMensagem("Erro ao cadastrar o Usuário.", FacesMessage.SEVERITY_ERROR);
		} else {

			adicionarMensagem("Usuário salvo com sucesso.", FacesMessage.SEVERITY_INFO);
			nomeUsuarioFiltrado = this.usuario.getNome();
			listarUsuario();
		}
		return "usuario";
	}


	public String editar(Usuario usuario) {
		this.usuario = dao.findById(usuario.getId());
		return "cadastrarUsuario";
	}

	public String remover(Usuario usuario) {
		if (!dao.delete(usuario.getId())) {
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
			listUsuario.addAll(dao.findByName(nomeUsuarioFiltrado));
		} else {
			listUsuario.addAll(dao.findAll());
		}
	}

	public void adicionarMensagem(String mensagem, Severity tipoMensagem) {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage fm = new FacesMessage(mensagem);
		fm.setSeverity(tipoMensagem);
		fc.addMessage(null, fm);

	}
}
