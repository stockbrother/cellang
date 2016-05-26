/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package org.cellang.clwt.commons.client.mvc.simple;

import org.cellang.clwt.commons.client.mvc.support.ViewSupport;
import org.cellang.clwt.core.client.Container;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu display model as text
 */
public class LightWeightView extends ViewSupport {

	/**
	 * @param ctn
	 */
	public LightWeightView(Container ctn) {
		this(ctn, null);
	}

	public LightWeightView(Container c, String name) {
		this(c, name, DOM.createDiv());
	}

	public LightWeightView(Container c, String name, Element ele) {
		super(c, name, ele);
	}

}
