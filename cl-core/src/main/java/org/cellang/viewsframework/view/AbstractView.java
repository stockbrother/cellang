package org.cellang.viewsframework.view;

import java.awt.Component;

import javax.swing.JScrollPane;

import org.cellang.commons.util.UUIDUtil;
import org.cellang.viewsframework.ops.OperationContext;

public abstract class AbstractView extends JScrollPane implements View {

	protected String title;
	protected String id;
	protected OperationContext oc;

	public AbstractView(String title, OperationContext oc) {
		this.title = title;
		this.id = UUIDUtil.randomStringUUID();
		this.oc = oc;
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
	public String getId() {
		return this.id;
	}

}
