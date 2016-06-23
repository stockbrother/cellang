/**
 * Jun 13, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.bar;

import org.cellang.clwt.commons.client.widget.BarWidgetI;
import org.cellang.clwt.commons.client.widget.LayoutSupport;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.lang.Position;
import org.cellang.clwt.core.client.lang.WebElement;
import org.cellang.clwt.core.client.widget.WebWidget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 *         <p>
 *         Do not use the other widget,but use the underlying DOM element to
 *         build a widget.
 *         <p>
 *         see HorizontalPanel in gwt.
 */
public class BarWidgetImpl extends LayoutSupport implements BarWidgetI {

	private Element table;
	private Element tableRow;

	private Element leftTd;

	private Element centerTd;

	private Element rightTd;

	/** */
	public BarWidgetImpl(Container c, String name) {
		super(c, name, DOM.createTable());
		this.table = this.getElement();
		this.elementWrapper.setAttribute("cellspacing", "0");
		this.elementWrapper.setAttribute("cellspading", "0");

		Element body = DOM.createTBody();
		DOM.appendChild(table, body);

		tableRow = DOM.createTR();
		DOM.appendChild(body, tableRow);

		this.leftTd = DOM.createTD();
		this.leftTd.addClassName("left");
		DOM.appendChild(tableRow, this.leftTd);

		this.centerTd = DOM.createTD();
		this.centerTd.addClassName("center");
		DOM.appendChild(tableRow, this.centerTd);

		this.rightTd = DOM.createTD();
		this.rightTd.addClassName("right");
		DOM.appendChild(tableRow, this.rightTd);

	}

	@Override
	public void addItem(Position loc, WebWidget cw) {

		Element td = null;
		if (loc.equals(BarWidgetI.P_CENTER)) {
			td = this.centerTd;
		} else if (loc.equals(BarWidgetI.P_LEFT)) {
			td = this.leftTd;
		} else if (loc.equals(BarWidgetI.P_RIGHT)) {
			td = this.rightTd;
		} else {
			throw new UiException("no this location in bar:" + loc);
		}

		DOM.appendChild(td, cw.getElement());//

	}

}
