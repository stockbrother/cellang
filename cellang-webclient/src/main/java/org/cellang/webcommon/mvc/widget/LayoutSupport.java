/**
 * Jul 15, 2012
 */
package org.cellang.webcommon.mvc.widget;

import java.util.List;

import org.cellang.webcommon.mvc.CompositeI;
import org.cellang.webcore.client.Container;
import org.cellang.webcore.client.lang.HasProperties;
import org.cellang.webcore.client.widget.WebWidget;

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

	/* */
	@Override
	public CompositeI child(WebWidget w) {
		w.parent(this);
		return this;
	}

	@Override
	public void attach() {
		super.attach();
		for (WebWidget w : this.getChildWidgetList()) {
			w.attach();
		}
	}

	@Override
	public void detach() {
		for (WebWidget w : this.getChildWidgetList()) {
			w.detach();
		}
		super.detach();
	}

	@Override
	public List<WebWidget> getChildWidgetList() {
		return this.getChildList(WebWidget.class);
	}

}
