/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 4, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.panel;

import org.cellang.clwt.commons.client.widget.ClosingEvent;
import org.cellang.clwt.commons.client.widget.LayoutSupport;
import org.cellang.clwt.commons.client.widget.PanelWI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.gwtbridge.GwtClickHandler;
import org.cellang.clwt.core.client.lang.ObjectElementHelper;
import org.cellang.clwt.core.client.lang.WebElement;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 *         <p>
 *         Panel is a simple container.
 * 
 */
public class PanelWImpl extends LayoutSupport implements PanelWI {

	protected Element header;
	protected ObjectElementHelper close;
	protected WebElement content;

	/**
	 * @param ele
	 */
	public PanelWImpl(Container c, String name) {
		super(c, name, DOM.createDiv());

		this.header = DOM.createDiv();
		this.header.setClassName("panel-header");
		this.element.appendChild(this.header);

		this.close = new ObjectElementHelper(DOM.createButton());
		
		this.close.getElement().setInnerText("X");
		this.close.getElement().addClassName("button");
		this.close.addGwtHandler(ClickEvent.getType(), new GwtClickHandler() {

			@Override
			protected void handleInternal(ClickEvent evt) {
				PanelWImpl.this.onClose();
			}
		});

		this.header.appendChild(this.close.getElement());//
		// this.header.appendChild(DOM.createDiv());//right filler
		this.setClosable(false);
	}
	
	@Override
	public void setContent(WebElement we){
		if(this.content != null){
			throw new UiException("duplicated");
		}
		this.content = we;
		this.appendElement(we);
	}

	protected void onClose() {
		new ClosingEvent(this).dispatch();
	}

	@Override
	public void setClosable(boolean b) {
		this.close.setVisible(b);
		if (b) {//
			this.header.addClassName("visible");
			this.header.removeClassName("invisible");
		} else {//empty,not show header
			this.header.addClassName("invisible");
			this.header.removeClassName("visible");
		}
	}



	@Override
	public WebElement getContent() {
		return this.content;
	}
}
