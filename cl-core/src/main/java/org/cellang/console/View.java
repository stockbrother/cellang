package org.cellang.console;

import java.awt.Component;

public interface View {

	public String getTitle();

	public Component getComponent();

	public <T> T getDelegate(Class<T> cls);
	
}
