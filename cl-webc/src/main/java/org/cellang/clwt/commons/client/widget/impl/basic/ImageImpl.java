/**
 * Jun 29, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.basic;

import org.cellang.clwt.commons.client.widget.ImageI;
import org.cellang.clwt.commons.client.widget.WidgetSupport;
import org.cellang.clwt.core.client.Container;

import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class ImageImpl extends WidgetSupport implements ImageI {

	/** */
	public ImageImpl(Container c, String name) {
		super(c, name, DOM.createImg());
	}

	@Override
	public void setSrc(String src) {
		this.element.setAttribute("src", src);
	}

}
