/**
 * Jun 25, 2012
 */
package org.cellang.clwt.commons.client.mvc.support;

import org.cellang.clwt.commons.client.mvc.Control;
import org.cellang.clwt.commons.client.mvc.ControlManager;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.lang.AbstractWebObject;

/**
 * @author wuzhen
 * 
 */
public abstract class AbstractControl extends AbstractWebObject implements Control {

	protected String name;

	protected Container container;

	public AbstractControl(Container c, String name) {
		super(c, name);
		this.container = c;
		this.name = name;

	}

	@Override
	protected void doAttach() {
		super.doAttach();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ControlManager getManager() {
		return (ControlManager) this.parent;

	}

	protected <T extends Control> T getControl(Class<T> cls, boolean force) {
		return this.getManager().getControl(cls, force);
	}

}
