/**
 * Jul 15, 2012
 */
package org.cellang.clwt.commons.client.widget;

import java.util.List;

import org.cellang.clwt.commons.client.mvc.CompositeI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.lang.HasProperties;
import org.cellang.clwt.core.client.widget.WebWidget;

import com.google.gwt.user.client.Element;

/**
 * @author wu TODO rename to LayoutWidgetSupport
 */
public class LayoutSupport extends WidgetSupport implements CompositeI {
	public LayoutSupport(Container c, Element ele) {
		this(c, null, ele);
	}

	public LayoutSupport(Container c, String name, Element ele) {
		super(c, name, ele);
	}

	public LayoutSupport(Container c, String name, Element ele, HasProperties<Object> pts) {
		super(c, name, ele, pts);
	}

}
