/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package org.cellang.webframework.client;

import org.cellang.webframework.client.event.EventBus;

/**
 * @author wu
 * 
 */
public class ContainerAwareUiObjectSupport extends StatefulWebObject implements ContainerAware {

	/**
	 * @param name
	 */
	public ContainerAwareUiObjectSupport(Container c) {
		this(c, null);
	}

	public ContainerAwareUiObjectSupport(Container c, String name) {
		super(c, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.ContainerAwareI#setContainer(com.fs.uicore
	 * .api.gwt.client.ContainerI)
	 */
	@Override
	public void setContainer(Container c) {
		this.container = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.UiObjectSupport#getEventBus()
	 */
	@Override
	public EventBus getEventBus(boolean force) {
		return this.container.getEventBus();
	}

}
