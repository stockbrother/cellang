package org.cellang.clwt.commons.client.mvc.impl;

import java.util.HashMap;
import java.util.Map;

import org.cellang.clwt.commons.client.mvc.Control;
import org.cellang.clwt.commons.client.mvc.ControlManager;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.lang.AbstractWebObject;

/**
 * 
 * @author wu
 * 
 */
public class ControlManagerImpl extends AbstractWebObject implements ControlManager {

	protected Map<String, Control> controlMap = new HashMap<String, Control>();

	/**
	 * @param c
	 */
	public ControlManagerImpl(Container c) {
		super(c);
	}

	@Override
	public <T extends Control> ControlManager addControl(Class<T> cls, T c) {
		this.controlMap.put(cls.getName(), c);
		return this;
	}

	@Override
	public <T extends Control> T getControl(Class<T> cls, boolean force) {
		Control rt = this.controlMap.get(cls.getName());
		if (rt == null && force) {
			throw new UiException("no this control:" + cls);
		}
		return (T) rt;
	}

}
