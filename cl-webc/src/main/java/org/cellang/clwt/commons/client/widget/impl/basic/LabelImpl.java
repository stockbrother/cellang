/**
 * Jun 29, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.basic;

import org.cellang.clwt.commons.client.widget.LabelI;
import org.cellang.clwt.commons.client.widget.WidgetSupport;
import org.cellang.clwt.core.client.Container;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class LabelImpl extends WidgetSupport implements LabelI {

	/** */
	public LabelImpl(Container c, String name) {
		super(c, name, DOM.createLabel());
	}

	@Override
	public void setText(String sd) {
		this.setText(sd, false);
	}

	@Override
	public void setText(String sd, boolean loc) {
		//
		if (loc) {
			sd = this.localized(sd);
		}
		Element ele = this.getElement();

		ele.setInnerText(sd);//
	}

	/*
	 * Apr 13, 2013
	 */
	@Override
	public void setTitle(String title) {
		this.getElement().setTitle(title);
	}

	/*
	 * Apr 13, 2013
	 */
	@Override
	public void setTextAndTitle(String sd, boolean loc, String title) {
		this.setText(sd,loc);
		this.setTitle(title);
	}
}
