package org.venturabank.managedbean.session;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import org.ventura.entity.Menu;

@Named
@SessionScoped
@ManagedBean
public class UsuarioMB implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Menu> menus;
	
	@Resource  
	private SessionContext sessionContext;
	
	@PostConstruct
	private void init(){
		Principal p = sessionContext.getCallerPrincipal();
		System.out.println(p.getName());
	}

}
