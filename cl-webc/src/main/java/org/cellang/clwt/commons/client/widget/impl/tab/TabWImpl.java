/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.tab;

import org.cellang.clwt.commons.client.widget.PanelWI;
import org.cellang.clwt.commons.client.widget.SelectEvent;
import org.cellang.clwt.commons.client.widget.StackItemI;
import org.cellang.clwt.commons.client.widget.TabWI;
import org.cellang.clwt.commons.client.widget.ViewReferenceI;
import org.cellang.clwt.commons.client.widget.WidgetSupport;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.widget.WebWidget;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class TabWImpl extends WidgetSupport implements TabWI {

	protected TabberWImpl tabber;

	protected PanelWI panel;

	protected StackItemI stackItem;

	protected boolean selected;
	protected WebWidget managed;

	/**
	 * @param ele
	 */
	public TabWImpl(Container c, String name, PanelWI panel, WebWidget managed, StackItemI sitem, TabberWImpl tabber) {
		super(c, name, DOM.createDiv());
		this.panel = panel;
		this.managed = managed;
		this.tabber = tabber;
		this.stackItem = sitem;

		this.addGwtHandler(com.google.gwt.event.dom.client.ClickEvent.getType(), new ClickHandler() {

			@Override
			public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
				TabWImpl.this.onGwtClick(event);
			}//
		});

		this.setText(true, name);

	}

	private void onGwtClick(com.google.gwt.event.dom.client.ClickEvent event) {
		this.select();
	}

	// this not impact the tabber,only impact this widget itself
	// please call select,it will connected with tabber.
	public void setSelected(boolean sel, boolean dis) {
		this.selected = sel;

		this.getElementWrapper().addAndRemoveClassName(sel, "position-selected", "position-unselected");
		if (dis) {
			new SelectEvent(this, sel).dispatch();//
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.widget.tab.TabWI#show()
	 */
	@Override
	public void select() {

		this.tabber._select(this.name);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.widget.tab.TabWI#getPanel()
	 */
	@Override
	public PanelWI getPanel() {

		return this.panel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.widget.tab.TabWI#getName()
	 */
	@Override
	public String getName() {

		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.support.WidgetBase#doUpdate(com.fs.uicore
	 * .api.gwt.client.core.ModelI)
	 */
	@Override
	public void setText(boolean i18n, String text) {

		if (i18n) {
			//
			/**
			 * <code>
			 * IT's strange: getClient_0_g not defined in genereated js.
			 * text = this.getClient(true).localized(text);
			 * error to open console view,com.google.gwt.core.client.JavaScriptException: (TypeError) : this.getClient_0_g$ is not a function
			at Unknown.a4c_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.R3c_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.l4c_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.k4c_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.xQc_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.BQc_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.ZRc_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.lMd_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.kMd_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.SId_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.ZId_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.kr_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.Qq_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.Lq_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.cr_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.Iq_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.vp_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.yp_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.xp_g$/<(org.cellang.webc.main.Module-0.js)
			at Unknown.callback_0_g$(org.cellang.webc.main.Module-0.js)
			at Unknown.anonymous(Unknown)
			</code>
			 */

		}

		if (this.isSelected()) {
			text += "*";// TODO other way to show the selected tab.
		}

		this.element.setInnerText(text);//

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.widget.tab.TabWI#isSelected()
	 */
	@Override
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * @return the stackItem
	 */
	public ViewReferenceI getStackItem() {
		return stackItem;
	}

	@Override
	public void addSelectEventHandler(EventHandlerI<SelectEvent> eh) {
		this.addHandler(SelectEvent.TYPE, eh);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.widget.tab.TabWI#close()
	 */
	@Override
	public void close() {
		this.stackItem.remove();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.widget.tab.TabWI#getManaged()
	 */
	@Override
	public WebWidget getManaged() {
		return this.managed;
	}

}
