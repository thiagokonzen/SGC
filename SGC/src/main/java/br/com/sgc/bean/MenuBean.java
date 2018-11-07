package br.com.sgc.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import br.com.sgc.converter.MenuDAO;
import br.com.sgc.domain.Menu;

@ManagedBean
@SessionScoped
public class MenuBean {
	private MenuModel modeloDoMenu;
	
	public MenuModel getModeloDoMenu() {
		return modeloDoMenu;
	}
	
	public void setModeloDoMenu(MenuModel modeloDoMenu) {
		this.modeloDoMenu = modeloDoMenu;
	}
	
	@PostConstruct
	public void iniciar(){
		MenuDAO menuDAO = new MenuDAO();
		List<Menu> lista = menuDAO.listar();
		
		modeloDoMenu = new DefaultMenuModel();

		for (Menu menu : lista) {
			if (menu.getCaminho() == null) {
				DefaultSubMenu subMenu = new DefaultSubMenu(menu.getRotulo());
				
				for (Menu item : menu.getMenus()){
					DefaultMenuItem menuItem = new DefaultMenuItem(item.getRotulo());
					menuItem.setUrl(item.getCaminho());
					
					subMenu.addElement(menuItem);
				}
				
				modeloDoMenu.addElement(subMenu);
			}
		}
	}
}
