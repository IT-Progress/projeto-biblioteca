package controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import dao.impl.UsuarioDao;
import model.Usuario;

@ManagedBean
@ApplicationScoped
@Named
public class UsuarioBean {
	@Inject
	private UsuarioDao dao;

	private List<Usuario> list;

	private String nomeUsuarioFiltrado;

	private Usuario usuario;

	private String acaoUsuario;

	@PostConstruct
	public void init() {
		usuario = new Usuario();
		list = new ArrayList<Usuario>();
	}

	public String getAcaoUsuario() {
		return acaoUsuario;
	}

	public void setAcaoUsuario(String acaoUsuario) {
		this.acaoUsuario = acaoUsuario;
	}

	private void zerarLista() {
		list = new ArrayList<Usuario>();
	}

	public String getNomeUsuarioFiltrado() {
		return nomeUsuarioFiltrado;
	}

	public void setNomeUsuarioFiltrado(String nomeUsuarioFiltrado) {
		this.nomeUsuarioFiltrado = nomeUsuarioFiltrado;
	}

	public List<Usuario> getList() {
		return list;
	}

	public void setList(List<Usuario> list) {
		this.list = list;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String novo() {
		usuario = new Usuario();
		this.acaoUsuario = "Cadastro de";
		return "cadastrarUsuario";
	}

	public String salvar() {
		if (!dao.save(usuario)) {
			adicionarMensagem("Erro ao cadastrar o Usuário.", FacesMessage.SEVERITY_ERROR);
		} else {

			adicionarMensagem("Usuário salvo com sucesso.", FacesMessage.SEVERITY_INFO);
			listarUsuario();
		}
		return "usuario";
	}

	public String editar(Usuario usuario) {
		this.usuario = dao.findById(usuario.getId());
		this.acaoUsuario = "Edição de";
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
			list.addAll(dao.findByName(nomeUsuarioFiltrado));
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

	public String formatarTexto(String string) {
		if (string.length() == 0)
			return "";

		if (string.length() == 1)
			return string.toUpperCase();

		return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
	}
	
}
