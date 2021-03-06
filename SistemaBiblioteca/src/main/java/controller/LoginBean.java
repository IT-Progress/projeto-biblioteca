package controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.impl.UsuarioDao;
import model.Usuario;

@ManagedBean
@RequestScoped
@Named
public class LoginBean {

	@Inject
	private UsuarioDao dao;

	private Usuario usuario1;
	
	private Usuario usuario2;
 
	@PostConstruct
	public void init() {
		usuario1 = new Usuario();
	}

	public Usuario getUsuario1() {
		return usuario1;
	}

	public void setUsuario1(Usuario usuario1) {
		this.usuario1 = usuario1;
	}

	public String autenticar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		usuario1 = dao.autenticacao(usuario1.getEmail(), usuario1.getSenha());
		
		if (usuario1 == null) {

			fc.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usu�rio n�o encontrado!", "Erro no Login!"));
			return null;
		}
		if (usuario1.getAdministrador()) {
			ExternalContext ec = fc.getExternalContext();
			HttpSession session = (HttpSession) ec.getSession(false);
			session.setAttribute("usuario", this.usuario1);
			return "/logadoADM/home";

		} else {
			ExternalContext ec = fc.getExternalContext();
			HttpSession session = (HttpSession) ec.getSession(false);
			session.setAttribute("usuario", this.usuario1);
			return "/logadoUsuario/home";
		}
	}

	public String registraSaida() {
		usuario1 = new Usuario();
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		session.removeAttribute("usuario");

		return "/login";
	}

	public String salvarLogin() {
		String senha = usuario1.getSenha();
		String senhaConfirmacao = usuario1.getSenhaConfirmacao();
		String email = usuario1.getEmail();
		
		Usuario usuarioValidacaoEmail = dao.findByEmail(email);
		if(usuarioValidacaoEmail != null) {
			FacesMessage msg = new FacesMessage("O email j� est� cadastrado.");
			FacesContext.getCurrentInstance().addMessage("erro", msg);
		} else 
		
			if (senha.equals(senhaConfirmacao)) {
				
				Usuario novoUsuario = new Usuario();
				novoUsuario.setAdministrador(false);
				novoUsuario.setEmail(usuario1.getEmail());
				novoUsuario.setNome(usuario1.getNome());
				novoUsuario.setSenha(usuario1.getSenha());
				if (!dao.save(novoUsuario)) {
					FacesMessage msg = new FacesMessage("Usu�rio N�O cadastrado!");
					FacesContext.getCurrentInstance().addMessage("erro", msg);
				} else {
					FacesMessage msgCadastrado = new FacesMessage("Cadastrado com sucesso!");
					FacesContext.getCurrentInstance().addMessage("erro", msgCadastrado);
				}
			} else {
				FacesMessage senhaErrada = new FacesMessage("Senhas N�O coincidem!");
				FacesContext.getCurrentInstance().addMessage("erro", senhaErrada);
			}
		
		return "login";
	
	}

	public void adicionarMensagem(String mensagem, Severity tipoMensagem) {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage fm = new FacesMessage(mensagem);
		fm.setSeverity(tipoMensagem);
		fc.addMessage(null, fm);

	}

}
