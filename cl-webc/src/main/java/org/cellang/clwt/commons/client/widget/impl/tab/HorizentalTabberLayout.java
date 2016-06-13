/**
 *  Feb 8, 2013
 */
package org.cellang.clwt.commons.client.widget.impl.tab;

import org.cellang.clwt.commons.client.mvc.widget.StackWI;
import org.cellang.clwt.commons.client.widget.TabWI;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public class HorizentalTabberLayout extends TabberLayout {

	private Element headerTd;

	private Element middleTd;

	private Element bodyTd;

	private TabberWImpl tabber;

	/**
	 * @param c
	 * @param element
	 */
	public HorizentalTabberLayout(TabberWImpl tabber, Element element, boolean rev) {
		super(element, "layout-horizental", rev);
		this.tabber = tabber;
		Element table = this.element;
		Element tbody = DOM.createTBody();
		DOM.appendChild(table, tbody);

		this.headerTd = this.createTrTd(tbody, "position-header");

		this.middleTd = this.createTrTd(tbody, "position-middle");

		this.bodyTd = this.createTrTd(tbody, "position-body");
	}

	public void setStack(StackWI cw) {
		DOM.appendChild(this.bodyTd, cw.getElement());//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.impl.gwt.client.widget.tab.TabberLayout#addTab(com.fs
	 * .uicommons.api.gwt.client.widget.tab.TabWI)
	 */
	@Override
	public void addTab(TabWI cw) {
		// TODO Auto-generated method stub
		if (this.isReverse) {
			this.headerTd.insertFirst(cw.getElement());
		} else {

			this.headerTd.appendChild(cw.getElement());//
		}
		
	}
	
	@Override
	public void afterTabAddOrRemove() {

		if (this.tabber.getTabList().size() <= 1) {
			// hide the header

			this.headerTd.addClassName("invisible");
			this.headerTd.removeClassName("visible");
		} else {// show the tab
			this.headerTd.addClassName("visible");
			this.headerTd.removeClassName("invisible");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.impl.gwt.client.widget.tab.TabberLayout#removeTab(com
	 * .fs.uicommons.api.gwt.client.widget.tab.TabWI)
	 */
	@Override
	public void removeTab(TabWI cw) {
		cw.getElement().removeFromParent();

		this.afterTabAddOrRemove();
	}

}
