package org.cellang.clwt.commons.client.mvc.impl;

import org.cellang.clwt.commons.client.mvc.Control;
import org.cellang.clwt.commons.client.mvc.ControlManager;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.lang.AbstractWebObject;

/**
 * 
 * @author wu
 * 
 */
public class ControlManagerImpl extends AbstractWebObject implements ControlManager {

	/**
	 * @param c
	 */
	public ControlManagerImpl(Container c) {
		super(c);
	}

	@Override
	public ControlManager addControl(Control c) {
		this.child(c);//
		return this;
	}

	@Override
	public <T extends Control> T getControl(Class<T> cls, String name, boolean force) {
		return this.getChild(cls, name, force);
	}

	@Override
	public <T extends Control> T getControl(Class<T> cls, boolean force) {
		return this.getChild(cls, null, force);
	}

}
