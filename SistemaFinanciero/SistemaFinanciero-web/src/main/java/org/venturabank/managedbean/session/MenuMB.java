package org.venturabank.managedbean.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ventura.boundary.local.LoginServiceLocal;
import org.ventura.entity.Menu;
import org.ventura.entity.Modulo;

@ManagedBean
@ViewScoped
public class MenuMB {
	
	@EJB
	private LoginServiceLocal loginServiceLocal;
	
	@ManagedProperty(value = "#{usuarioMB}")
	private UsuarioMB usuarioMB;
	
	private Modulo modulo;
	
	private List<Menu> menus;
	
	private Map<Menu, List<Menu>> menuFinal;
	
	public MenuMB() {
		modulo = new Modulo();
		menus= new ArrayList<Menu>();
		menuFinal = new HashMap<Menu, List<Menu>>();
	}
	
	@PostConstruct
	private void init(){
		menus = (List<Menu>) loginServiceLocal.getMenu(usuarioMB.getUsuario());
	}
	
	public void updateMenu(){
		menuFinal = orderMenu();
	}
	
	private Map<Menu, List<Menu>> orderMenu(){
		
		List<Menu> menuModulo = new ArrayList<Menu>();
		
		for (Iterator<Menu> iterator = menus.iterator(); iterator.hasNext();) {
			
			Menu menu = iterator.next();
			
			if(menu.getModulo().equals(modulo)){
				menuModulo.add(menu);
			}
				
		}
				
		Map<Menu, List<Menu>> menuFinalMap = new HashMap<Menu, List<Menu>>();
		
		for (Iterator<Menu> iterator = menuModulo.iterator(); iterator.hasNext();) {
			Menu menu = iterator.next();
			if(menu.getMenu() == null){
				menuFinalMap.put(menu, new ArrayList<Menu>());
				iterator.remove();
			}				
		}
		
		for (Iterator<Menu> iterator = menuModulo.iterator(); iterator.hasNext();) {
			Menu menu = iterator.next();
			menuFinalMap.get(menu.getMenu()).add(menu);
		}
		
		return menuFinalMap;
		
	}
	
	public void imprimir(){
		System.out.println(modulo.getIdmodulo());
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public UsuarioMB getUsuarioMB() {
		return usuarioMB;
	}

	public void setUsuarioMB(UsuarioMB usuarioMB) {
		this.usuarioMB = usuarioMB;
	}

	public Map<Menu, List<Menu>> getMenuFinal() {
		return menuFinal;
	}

	public void setMenuFinal(Map<Menu, List<Menu>> menuFinal) {
		this.menuFinal = menuFinal;
	}

}
