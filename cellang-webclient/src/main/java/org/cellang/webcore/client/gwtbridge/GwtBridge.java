package org.cellang.webcore.client.gwtbridge;

public class GwtBridge {

	public static String getWindowLocationParameter(String name, String def) {
		String rt = com.google.gwt.user.client.Window.Location.getParameter(name);
		return rt == null ? def : rt;
	}

}
