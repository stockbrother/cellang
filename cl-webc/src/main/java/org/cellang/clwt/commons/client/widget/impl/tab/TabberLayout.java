/**
 *  Feb 8, 2013
 */
package org.cellang.clwt.commons.client.widget.impl.tab;

import org.cellang.clwt.commons.client.mvc.widget.StackWI;
import org.cellang.clwt.commons.client.widget.TabWI;
import org.cellang.clwt.core.client.core.ElementWrapper;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public abstract class TabberLayout extends ElementWrapper {

	protected boolean isReverse;

	/**
	 * @param c
	 * @param element
	 */
	public TabberLayout(Element element, String cname, boolean rev) {
		super(element);
		element.addClassName(cname);
		this.isReverse = rev;
	}

	protected Element createTr(Element tbody) {
		Element tr = DOM.createTR();
		DOM.appendChild(tbody, tr);
		return tr;
	}

	protected Element createTd(Element tr, String cname) {
		Element td = DOM.createTD();
		DOM.appendChild(tr, td);
		td.addClassName(cname);
		return td;
	}

	protected Element createTrTd(Element tbody, String cname) {
		Element tr = this.createTr(tbody);
		Element td = this.createTd(tr, cname);
		return td;
	}

	public abstract void setStack(StackWI cw);

	public abstract void addTab(TabWI tab);

	public abstract void removeTab(TabWI tab);

	public abstract void afterTabAddOrRemove();

}
