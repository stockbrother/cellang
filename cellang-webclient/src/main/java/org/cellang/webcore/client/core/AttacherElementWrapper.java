/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 20, 2012
 */
package org.cellang.webcore.client.core;

import org.cellang.webcore.client.WebException;
import org.cellang.webcore.client.lang.Attacher;
import org.cellang.webcore.client.lang.WebObject;

import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public class AttacherElementWrapper extends ElementWrapper implements
		Attacher {

	protected WebObject owner;

	/**
	 * @param ele
	 */
	public AttacherElementWrapper(Element ele) {
		super(ele);
	}

	@Override
	public void owner(WebObject obj) {
		this.owner = obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see core.AttacherI#getOwner(boolean)
	 */
	@Override
	public WebObject getOwner(boolean force) {
		if (this.owner == null && force) {
			throw new WebException("no owner for:" + this);
		}
		return this.owner;

	}

	@Override
	public void ownerAttached() {
		this.doAttach();
	}

	protected void doAttach() {

	}

	protected void doDettach() {

	}

	@Override
	public void ownerDettached() {
		this.doDettach();
	}

}
