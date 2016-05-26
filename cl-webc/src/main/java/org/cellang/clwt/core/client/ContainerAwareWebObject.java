/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package org.cellang.clwt.core.client;

import org.cellang.clwt.core.client.event.EventBus;

/**
 * @author wu
 * 
 */
public class ContainerAwareWebObject extends HasStateWebObject implements ContainerAware {

	/**
	 * @param name
	 */
	public ContainerAwareWebObject(Container c) {
		this(c, null);
	}

	public ContainerAwareWebObject(Container c, String name) {
		super(c, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ContainerAwareI#setContainer(com.fs.uicore
	 * .api.gwt.client.ContainerI)
	 */
	@Override
	public void setContainer(Container c) {
		this.container = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see support.UiObjectSupport#getEventBus()
	 */
	@Override
	public EventBus getEventBus(boolean force) {
		return this.container.getEventBus();
	}

}
