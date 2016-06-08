/**
 * Jun 30, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.basic;

import org.cellang.clwt.commons.client.mvc.widget.AnchorWI;
import org.cellang.clwt.commons.client.mvc.widget.WidgetSupport;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.core.ElementWrapper;
import org.cellang.clwt.core.client.event.ClickEvent;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class AnchorWImpl extends WidgetSupport implements AnchorWI {

	private ElementWrapper image;
	public AnchorWImpl(Container c, String name) {
		super(c, name, (Element) Document.get().createAnchorElement().cast());
		this.addGwtHandler(com.google.gwt.event.dom.client.ClickEvent.getType(), new ClickHandler() {

			@Override
			public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
				AnchorWImpl.this.onGwtClick(event);
			}//
		});
	}

	public void onGwtClick(com.google.gwt.event.dom.client.ClickEvent event) {
		new ClickEvent(this).dispatch();
	}

	/* */
	@Override
	public void click() {
		this.assertAttached();
		this.getElementWrapper().click();
	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public void setDisplayText(String txt) {
		//
		this.setDisplayText(txt,false);
	}

	/*
	 *Apr 5, 2013
	 */
	@Override
	public void setDisplayText(String txt, boolean loc) {
		// 
		if(loc){
			txt = this.getClient(true).localized(txt);			
		}
		this.setTextAndTitle(txt, null);
	}

	/* (non-Javadoc)
	 * @see com.fs.uicommons.api.gwt.client.widget.basic.AnchorWI#setTextAndTitle(java.lang.String, java.lang.String)
	 */
	@Override
	public void setTextAndTitle(String txt, String title) {
		
		AnchorElement ae = this.getElement().cast();
		ae.setInnerText(txt);//
		ae.setTitle(title);// TODO replace this
		
	}

	/* (non-Javadoc)
	 * @see com.fs.uicommons.api.gwt.client.widget.basic.AnchorWI#setImage(java.lang.String)
	 */
	@Override
	public void setImage(String src) {
		if(this.image == null){
			image = new ElementWrapper(DOM.createImg());
			this.element.appendChild(image.getElement());
		}
		if(src == null){
			src = "";
		}
		image.setAttribute("src", src);
	}

}
