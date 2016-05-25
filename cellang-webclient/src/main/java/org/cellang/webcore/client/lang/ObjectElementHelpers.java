/**
 * Jun 30, 2012
 */
package org.cellang.webcore.client.lang;

import java.util.HashMap;
import java.util.Map;

import org.cellang.webcore.client.WebException;

import com.google.gwt.user.client.Element;

/**
 * @author wu
 *         <p>
 * 
 */
public class ObjectElementHelpers {

	private Map<String, ObjectElementHelper> helpers = new HashMap<String, ObjectElementHelper>();

	private WebObject owner;

	public ObjectElementHelpers(WebObject owner) {
		this.owner = owner;
	}

	public ObjectElementHelper addHelper(String name, Element ele) {
		ObjectElementHelper rt = this.getHelper(name, false);
		if (rt != null) {
			throw new WebException("already has helper:" + name + " for owner:"
					+ this.owner);
		}
		rt = new ObjectElementHelper(ele, this.owner);
		this.helpers.put(name, rt);
		return rt;
	}

	public ObjectElementHelper getHelper(String name, boolean force) {
		ObjectElementHelper rt = this.helpers.get(name);
		if (force && rt == null) {
			throw new WebException("no this helper:" + name + " for owner:"
					+ this.owner);
		}
		return rt;
	}

	public void attach() {
		for (ObjectElementHelper oeh : this.helpers.values()) {
			oeh.attach();
		}
	}

	public void detach() {
		for (ObjectElementHelper oeh : this.helpers.values()) {
			oeh.detach();
		}
	}
}