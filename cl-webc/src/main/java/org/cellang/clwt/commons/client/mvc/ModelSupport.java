/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 10, 2012
 */
package org.cellang.clwt.commons.client.mvc;

import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.lang.AbstractWebObject;
import org.cellang.clwt.core.client.lang.HasProperties;
import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * @deprecated
 */
public class ModelSupport extends AbstractWebObject implements Model {

	protected boolean silent;

	/**
	 * @param defProperty
	 */
	public ModelSupport(String name) {
		this(name, null);
	}

	public ModelSupport(String name, String id) {
		super(null, name, id);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public WebObject parent(WebObject parent) {
		if (parent != null && !(parent instanceof Model)) {
			throw new UiException("parent of model must be model,but is:" + parent);
		}
		super.parent(parent);
		return this;
	}

	protected void _setProperty(String key, Object value) {
		super.setProperty(key, value);
	}

	@Override
	public void setProperty(String key, Object value) {
		throw new UiException("not allowed,please use Model.setValue");
	}

	@Override
	public void setProperties(HasProperties<Object> pts) {
		throw new UiException("not allowed,please use ModelI.setValue");

	}

	@Override
	public <E extends Event> void dispatch(E evt) {
		if (this.silent) {
			return;
		}
		super.dispatch(evt);

	}

}
