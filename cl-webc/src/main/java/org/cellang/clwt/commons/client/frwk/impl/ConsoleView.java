/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package org.cellang.clwt.commons.client.frwk.impl;

import org.cellang.clwt.commons.client.frwk.ConsoleViewI;
import org.cellang.clwt.commons.client.mvc.simple.SimpleView;
import org.cellang.clwt.core.client.Console;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.core.ElementWrapper;
import org.cellang.clwt.core.client.lang.Callback;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class ConsoleView extends SimpleView implements ConsoleViewI {

	protected Element table;

	protected ElementWrapper tbody;

	protected int size;

	protected int maxSize = 1000;
	
	protected Callback<Object, Boolean> handler;

	/**
	 * @param ctn
	 */
	public ConsoleView(Container c) {
		super(c, "console", DOM.createDiv());
		this.element.addClassName("console");
		table = DOM.createTable();
		this.tbody = new ElementWrapper(DOM.createTBody());
		DOM.appendChild(table, this.tbody.getElement());
		DOM.appendChild(this.element, table);//
		this.handler = new Callback<Object, Boolean>() {

			@Override
			public Boolean execute(Object t) {
				//
				ConsoleView.this.onConsoleLine(t);//
				return null;
			}
		};
		Console.getInstance().addMessageCallback(this.handler);
		Console.getInstance().replay(this.handler);		
	}
	
	/**
	 * New message is arrived. NOTE,this method should not generate any event,or
	 * to call the Console.pringln();Otherwise,there will be dead loop. Nov 11,
	 * 2012
	 */
	private void onConsoleLine(Object msg) {
		Element tr = DOM.createTR();
		DOM.appendChild(this.tbody.getElement(), tr);

		Element td = DOM.createTD();
		String text = "" + msg;
		td.setInnerText(text);
		DOM.appendChild(tr, td);

		this.size++;
		this.getElement().setPropertyInt("scrollTop", 10000);// Scroll to
																// bottom.
		this.shrink();

	}

	public void shrink() {
		// shrink
		while (true) {
			if (this.size <= this.maxSize) {
				break;
			}
			com.google.gwt.dom.client.Element ele = this.tbody.getElement().getFirstChildElement();
			if (ele == null) {
				break;
			}
			ele.removeFromParent();
			this.size--;
		}

	}

}
