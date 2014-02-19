package org.ventura.caja.view;

import java.io.Serializable;
import java.security.MessageDigest;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.LoginServiceLocal;
import org.ventura.session.AgenciaBean;
import org.venturabank.util.JsfUtil;

@Named
@Dependent
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean success;
	private boolean failure;
	
	private boolean dlgLogin;
	
	private String usuario;
	private String password;
	private boolean autenticado;
	
	@Inject AgenciaBean agenciaBean;
	@EJB private LoginServiceLocal loginServiceLocal;
	
	public LoginBean() {
		success = false;
		failure = false;
		dlgLogin = false;
		autenticado = false;
	}
	
	@PostConstruct
	public void initialize() throws Exception{
		try {
			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
			throw e;
		}
	}
	
	public void login(){
		try {
			autenticado = loginServiceLocal.loginAsadministrador(agenciaBean.getAgencia(), usuario, getMD5(password));
			success = true;		
			if(autenticado == true){
				dlgLogin = false;
			} else {
				dlgLogin = true;
			}
			
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());			
		}
	}
	
	public String getMD5(String cadena) throws Exception {	 
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(cadena.getBytes());
 
        int size = b.length;
        StringBuilder h = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
 
            int u = b[i] & 255;
 
            if (u < 16)
            {
                h.append("0").append(Integer.toHexString(u));
            }
            else
            {
                h.append(Integer.toHexString(u));
            }
        }
        return h.toString();
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

	public boolean isDlgLogin() {
		return dlgLogin;
	}

	public void setDlgLogin(boolean dlgLogin) {
		this.dlgLogin = dlgLogin;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}

}
