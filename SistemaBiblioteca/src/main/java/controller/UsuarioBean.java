package controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import dao.impl.UsuarioDao;
import model.Usuario;

@RequestScoped
@Named
public class UsuarioBean {
	@Inject
	private UsuarioDao dao;

	private List<Usuario> list;

	private String nomeUsuarioFiltrado;

	private Usuario usuario;
	
	@PostConstruct
	public void init() {
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
	
	public void salvar() {
		if (!dao.save(usuario)) {
			adicionarMensagem("Erro ao cadastrar o Usuário.", FacesMessage.SEVERITY_ERROR);
		}

		adicionarMensagem("Usuário salvo com sucesso.", FacesMessage.SEVERITY_INFO);
	}

	public String editar() {
		usuario = dao.findById(1L);
		return "cadastrarUsuario";
	}

	public void remover(Usuario usuario) {
		if (!dao.delete(usuario.getId())) {
			adicionarMensagem("Erro ao remover o Usuário.", FacesMessage.SEVERITY_ERROR);
		}

		adicionarMensagem("Usuário removido com sucesso.", FacesMessage.SEVERITY_INFO);
	}

	public void listarUsuario() {
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
	
}
