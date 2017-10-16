package controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
public class AutenticacaoBean {
	private static final String USUARIO_CORRETO = "admin";
	private static final String SENHA_CORRETA = "admin";
	private static final String USUARIO_CORRETO2 = "usuario";
	private static final String SENHA_CORRETA2 = "usuario";

	private String usuario;
	private String senha;

	public String autentica() {
		FacesContext fc = FacesContext.getCurrentInstance();

		if (USUARIO_CORRETO.equals(this.usuario) && SENHA_CORRETA.equals(this.senha) || USUARIO_CORRETO2.equals(this.usuario) && SENHA_CORRETA2.equals(this.senha)) {

			ExternalContext ec = fc.getExternalContext();
			HttpSession session = (HttpSession) ec.getSession(false);
			session.setAttribute("usuario", this.usuario);

			return "/logado/home";
		} else {
			FacesMessage fm = new FacesMessage("usu�rio e/ou senha inv�lidos");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(null, fm);
			return "/login";
		}
	}

	public String registraSaida() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		session.removeAttribute("usuario");

		return "/login";
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
