package org.cellang.console.view;

import java.awt.Component;

import javax.swing.JScrollPane;

public class AbstractView extends JScrollPane implements View {

	protected String title;

	public AbstractView(String title) {
		this.title = title;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		return null;
	}

}
