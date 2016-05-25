/**
 * Jun 25, 2012
 */
package org.cellang.webcommon.mvc.support;

import org.cellang.webcommon.mvc.ControlI;
import org.cellang.webcommon.mvc.ControlManagerI;
import org.cellang.webcore.client.Container;
import org.cellang.webcore.client.lang.AbstractWebObject;

/**
 * @author wuzhen
 * 
 */
public abstract class AbstractControl extends AbstractWebObject implements ControlI {

	protected String name;

	protected Container container;

	public AbstractControl(Container c, String name) {
		super(c, name);
		this.container = c;
		this.name = name;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.UiObjectSupport#doAttach()
	 */
	@Override
	protected void doAttach() {
		super.doAttach();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public ControlManagerI getManager() {

		return (ControlManagerI) this.parent;

	}

	protected <T extends ControlI> T getControl(Class<T> cls, boolean force) {
		return this.getManager().getControl(cls, force);
	}

}
