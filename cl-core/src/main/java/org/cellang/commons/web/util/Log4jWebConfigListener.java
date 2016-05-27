package org.cellang.commons.web.util;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.cellang.core.lang.util.ExceptionUtil;

public class Log4jWebConfigListener implements ServletContextListener {

	public static final String PK_log4jConfigurationResource = "log4jConfigResource";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			ServletContext sc = arg0.getServletContext();
			String log4jProp = sc.getInitParameter(PK_log4jConfigurationResource);
			if (log4jProp == null) {
				String webAppPath = sc.getRealPath("/");
				log4jProp = webAppPath + File.separator + "WEB-INF" + File.separator + "log4j.xml";
			}

			File file = new File(log4jProp);
			if (file.exists()) {
				sc.log("start configure log4j");
				//DOMConfigurator.configure(log4jProp);
				System.out.println("end of configure log4j");
			} else {
				throw new RuntimeException("file not found:" + log4jProp);
			}

		} catch (Exception e) {
			ExceptionUtil.toRuntimeException(e);
		}
	}

}
