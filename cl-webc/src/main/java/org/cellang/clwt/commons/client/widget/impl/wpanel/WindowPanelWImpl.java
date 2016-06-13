/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.wpanel;

import org.cellang.clwt.commons.client.mvc.widget.LayoutSupport;
import org.cellang.clwt.commons.client.mvc.widget.WindowPanelWI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.event.SizeChangeEvent;
import org.cellang.clwt.core.client.lang.Point;
import org.cellang.clwt.core.client.lang.Size;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;

/**
 * @author wu
 *         <p>
 *         This can be the top widget and no parent,but it should be managed by
 *         RootI.
 * @see AbsolutePanel
 */
public class WindowPanelWImpl extends LayoutSupport implements WindowPanelWI {

	/**
	 * @param ele
	 */
	public WindowPanelWImpl(Container c, String name) {
		super(c, name, DOM.createDiv());

		// DOM.setStyleAttribute(this.element, "position", "relative");//

		this.element.getStyle().setPosition(Position.ABSOLUTE);
		this.element.getStyle().setOverflow(Overflow.HIDDEN);
		this.getElementWrapper().moveTo(Point.valueOf(0, 0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.WidgetSupport#doAttach()
	 */
	@Override
	public void doAttach() {
		super.doAttach();
		//
		this.addHandler(SizeChangeEvent.TYPE, 
				new EventHandlerI<SizeChangeEvent>() {

					@Override
					public void handle(SizeChangeEvent e) {
						WindowPanelWImpl.this.onWindowSizeChange(e);
					}
				});
	}

	protected void onWindowSizeChange(SizeChangeEvent e) {
		
		Size s = e.getSize();
		DOM.setStyleAttribute(this.element, "width", s.getWidth() + "px");
		DOM.setStyleAttribute(this.element, "height", s.getHeight() + "px");

	}
}
