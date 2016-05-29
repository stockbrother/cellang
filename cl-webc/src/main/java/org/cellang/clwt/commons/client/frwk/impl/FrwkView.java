/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 10, 2012
 */
package org.cellang.clwt.commons.client.frwk.impl;

import org.cellang.clwt.commons.client.dom.TDWrapper;
import org.cellang.clwt.commons.client.dom.TRWrapper;
import org.cellang.clwt.commons.client.dom.TableWrapper;
import org.cellang.clwt.commons.client.frwk.BodyViewI;
import org.cellang.clwt.commons.client.frwk.BottomViewI;
import org.cellang.clwt.commons.client.frwk.FrwkViewI;
import org.cellang.clwt.commons.client.frwk.HeaderViewI;
import org.cellang.clwt.commons.client.mvc.support.ViewSupport;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.lang.ElementObjectI;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 *         <p>
 *         TODO replace ManagersView by Layout/Perspective, use Layout to define
 *         the rule of displaying based on the models.
 *         <p>
 *         There is no need to provide a complex but fixed layout for FrwkView.
 */
public class FrwkView extends ViewSupport implements FrwkViewI {
	private static final WebLogger LOG = WebLoggerFactory.getLogger(FrwkView.class);

	private Element top;

	private TDWrapper left;

	private TDWrapper body;

	private TDWrapper right;

	private Element bottom;

	/**
	 * @param ele
	 * @param ctn
	 */
	public FrwkView(Container c) {
		super(c, "frwk", DOM.createDiv());

		top = DOM.createDiv();

		top.addClassName("frwk-outer");

		this.element.appendChild(top);
		{
			TableWrapper table = new TableWrapper();
			this.elementWrapper.append(table);

			TRWrapper tr = table.addTr();
			left = tr.addTd();
			left.addClassName("frwk-left");

			this.body = tr.addTd();

			this.right = tr.addTd();
		}
		
		{//bottom

			this.bottom = DOM.createDiv();
			this.bottom.addClassName("frwk-bottom");
			this.element.appendChild(this.bottom);
		}

		// popup

		//

		HeaderView hv = new HeaderView(this.container);

		hv.parent(this);

		BodyView bv = new BodyView(this.container);
		bv.parent(this);
		
		BottomView tv = new BottomView(this.container);
		tv.parent(this);
		
		//
		EndpointBusyIndicator ebi = new EndpointBusyIndicator(this.container);
		ebi.parent(this);
	}

/*	@Override
	protected void onAddChild(Element pe, ElementObjectI cw) {
		
		DOM.appendChild(this.top, cw.getElement());
	}
*/
	@Override
	public BodyViewI getBodyView() {
		return this.getChild(BodyViewI.class, true);

	}

	@Override
	public HeaderViewI getHeader() {
		return this.getChild(HeaderViewI.class, true);
	}

	/*
	 *Apr 5, 2013
	 */
	@Override
	public BottomViewI getBottom() {
		// 
		return this.getChild(BottomViewI.class, true);
	}

}
