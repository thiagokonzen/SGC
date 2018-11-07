package br.com.sgc.util;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

public class HibernateContexto implements ServletContextListener {
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		HibernateUtil.getFabricaDeSessoes().close();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		HibernateUtil.getFabricaDeSessoes();
	}

}
