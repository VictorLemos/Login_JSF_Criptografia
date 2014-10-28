package com.nac.loginBean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.nac.entity.Login;
import com.nac.entity.Usuario;
import com.nac.seguranca.Autenticacao;


@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {


	private Login login;
	private Autenticacao autentica;
	private Usuario usuario;
	
	
	public LoginBean(){
		this.login = new Login();
		this.usuario = new Usuario();
		this.autentica = new Autenticacao(login,usuario);
	}
	
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}


	public void logar(){
		autentica.autenticar(this.login.getUsuario(), this.login.getSenha());
	}
	
	//Logout
		public String deslogar(){
			// Mato a session do usuário....
			FacesContext fc = FacesContext.getCurrentInstance();  
		    HttpSession session = (HttpSession)fc.getExternalContext().getSession(false);  
		    session.invalidate();
		    
			return "login?faces-redirect=true";
		}	
}

	
