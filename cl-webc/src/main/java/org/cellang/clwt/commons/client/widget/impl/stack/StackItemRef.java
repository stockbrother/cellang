/**
 *  Feb 6, 2013
 */
package org.cellang.clwt.commons.client.widget.impl.stack;

import org.cellang.clwt.commons.client.widget.StackItemI;
import org.cellang.clwt.commons.client.widget.StackWI;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wuzhen
 * 
 */
public class StackItemRef implements StackItemI {

	private StackWI stack;

	private boolean isDefault;

	private boolean isSelected;

	private WebWidget managed;

	private Path path;

	public StackItemRef(Path path, StackWI stack, WebWidget managed) {
		super();
		this.path = path;
		this.stack = stack;
		this.managed = managed;
	}

	public boolean isDefaultItem() {
		return this.isDefault;
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	public void trySelect(boolean sel) {
		if (this.isSelected() == sel) {
			return;
		}
		this.select(sel);
	}

	@Override
	public void select(boolean sel) {
		this.isSelected = sel;
		this.stack.updateSelect(this);
	}

	public WebWidget getManagedWidget() {
		return this.managed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI#getPath()
	 */
	@Override
	public Path getPath() {
		return this.path;
	}

	@Override
	public boolean isSelect() {
		// TODO Auto-generated method stub
		return this.isSelected;
	}

}