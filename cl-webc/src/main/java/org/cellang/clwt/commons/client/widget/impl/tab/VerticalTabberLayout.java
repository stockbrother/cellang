/**
 *  Feb 8, 2013
 */
package org.cellang.clwt.commons.client.widget.impl.tab;

import org.cellang.clwt.commons.client.mvc.widget.StackWI;
import org.cellang.clwt.commons.client.widget.TabWI;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public class VerticalTabberLayout extends TabberLayout {

	private Element leftTd;

	private Element ul;

	private Element bodyTd;

	/**
	 * @param c
	 * @param element
	 */
	public VerticalTabberLayout(Element element, boolean rev) {
		super(element, "tabber-layout-vertical", rev);
		Element table = this.element;
		table.setAttribute("cellspacing", "0");
		table.setAttribute("cellspading", "0");
		Element tbody = DOM.createTBody();
		DOM.appendChild(table, tbody);
		Element tr = this.createTr(tbody);
		this.leftTd = this.createTd(tr, "tabber-layout-left");

		this.ul = (com.google.gwt.user.client.Element) Document.get().createULElement().cast();
		DOM.appendChild(this.leftTd, this.ul);

		this.bodyTd = this.createTd(tr, "tabber-layout-right");
	}

	@Override
	public void setStack(StackWI cw) {
		DOM.appendChild(this.bodyTd, cw.getElement());//
	}

	@Override
	public void addTab(TabWI cw) {
		// TODO Auto-generated method stub
		Element li = (com.google.gwt.user.client.Element) Document.get().createLIElement().cast();
		if (this.isReverse) {
			this.ul.insertFirst(li);
		} else {
			this.ul.appendChild(li);//
		}
		li.appendChild(cw.getElement());

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
		Element li = cw.getElement().getParentElement().cast();
		li.removeFromParent();
		cw.getElement().removeFromParent();

	}

	/* (non-Javadoc)
	 * @see com.fs.uicommons.impl.gwt.client.widget.tab.TabberLayout#afterTabAddOrRemove()
	 */
	@Override
	public void afterTabAddOrRemove() {
		// TODO Auto-generated method stub
		
	}

}
