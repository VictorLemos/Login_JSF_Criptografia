package com.nac.seguranca;

import java.io.Serializable;
import java.util.Hashtable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import com.nac.entity.Login;
import com.nac.entity.Usuario;

public class Autenticacao implements Serializable {

	private Usuario usuario;
	private Login login;
	
	public Autenticacao(Login login, Usuario usuario){
		setLogin(login);
		setUsuario(usuario);
	 }
	
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean autenticar(String usuario, String senha)
	{
		this.login.setUsuario(usuario);
		this.login.setSenha(senha);
		
		boolean autenticado = false;
		String identity = "uid="+this.login.getUsuario()+",ou=sp,dc=fiapldap,dc=com";
		String password = Criptografia.encriptar(this.login.getSenha());
		
		Hashtable<String, String> ambiente = new Hashtable<String, String>();
		
		ambiente.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

		//Configuração do acesso ao serviço
		ambiente.put(Context.PROVIDER_URL, "ldap://localhost:389");
		ambiente.put(Context.SECURITY_AUTHENTICATION, "simple");
		ambiente.put(Context.SECURITY_PRINCIPAL, identity);
		ambiente.put(Context.SECURITY_CREDENTIALS, password);
		
		try {
			DirContext dir = new InitialDirContext(ambiente);
			autenticado = true;
			Attributes attrs = dir.getAttributes(identity);
			
					this.usuario.setCodigo(attrs.get("employeeNumber").get().toString());
					this.usuario.setNome(attrs.get("cn").get() +" "+ attrs.get("sn").get().toString());
					this.usuario.setCargo(attrs.get("employeeType").get().toString());
					this.usuario.setDepartamento(attrs.get("departmentNumber").get().toString());
					this.usuario.setSalario(attrs.get("description").get().toString());
					this.usuario.setEmail(attrs.get("mail").get().toString());
					//redirecionar para pagina se tiver OK user...	
					FacesContext.getCurrentInstance().getExternalContext().redirect("consultaUsuario.xhtml");
					dir.close();
				
		} catch (Exception e) {
			e.printStackTrace();
			//Da mensagem de erro de Login no growl do login.xhtml
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro! E-mail ou senha inválidos!", "Dados incorretos"));
			//ou....
			//FacesContext.getCurrentInstance().addMessage("login", new FacesMessage("Usuário ou senha inválida"));
			autenticado = false;
		}
		return autenticado;
	}
	
}
