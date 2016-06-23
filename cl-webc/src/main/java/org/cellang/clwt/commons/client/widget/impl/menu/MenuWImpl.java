/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.menu;

import java.util.HashMap;
import java.util.Map;

import org.cellang.clwt.commons.client.UiCommonsConstants;
import org.cellang.clwt.commons.client.widget.LayoutSupport;
import org.cellang.clwt.commons.client.widget.MenuItemWI;
import org.cellang.clwt.commons.client.widget.MenuWI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.core.ElementWrapper;
import org.cellang.clwt.core.client.event.ClickEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.gwtbridge.GwtMouseOutHandler;
import org.cellang.clwt.core.client.gwtbridge.GwtMouseOverHandler;
import org.cellang.clwt.core.client.lang.Point;
import org.cellang.clwt.core.client.lang.WebElement;
import org.cellang.clwt.core.client.widget.WebWidget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;

/**
 * @author wu
 * 
 */
public class MenuWImpl extends LayoutSupport implements MenuWI {

	/**
	 * TODO close menu when click other position.
	 * 
	 * @param ele
	 */
	protected Element ul;

	protected Timer timerToHide;
	
	protected Map<String,MenuItemWI> childMap = new HashMap<String,MenuItemWI>();

	public MenuWImpl(Container c, String name) {
		super(c, name, DOM.createDiv());

		this.ul = (com.google.gwt.user.client.Element) Document.get().createULElement().cast();
		DOM.appendChild(this.element, this.ul);
		this.addGwtEventHandler(com.google.gwt.event.dom.client.MouseOutEvent.getType(), new GwtMouseOutHandler() {
			protected void handleInternal(com.google.gwt.event.dom.client.MouseOutEvent evt) {
				MenuWImpl.this.onGwtMouseOut(evt);
			}

		});
		this.addGwtEventHandler(com.google.gwt.event.dom.client.MouseOverEvent.getType(), new GwtMouseOverHandler() {
			protected void handleInternal(com.google.gwt.event.dom.client.MouseOverEvent evt) {
				MenuWImpl.this.onGwtMouseOver(evt);
			}

		});

	}

	/**
	 * Mar 30, 2013
	 */
	protected void onGwtMouseOut(MouseOutEvent evt) {
		// close this after 1 sec;
		this.tryCancelHideTimer();
		timerToHide = new Timer() {

			@Override
			public void run() {
				MenuWImpl.this.onBlurTimeout();
			}
		};
		timerToHide.schedule(UiCommonsConstants.MENU_HIDE_TIMEOUT_MS);
	}

	protected void tryCancelHideTimer() {
		if (timerToHide != null) {
			timerToHide.cancel();
		}
	}

	protected void onGwtMouseOver(MouseOverEvent evt) {
		this.tryCancelHideTimer();
	}

	protected void onBlurTimeout() {
		this.setVisible(false);
	}

	@Override
	public MenuItemWI addItem(String name) {

		MenuItemWI rt = this.getItem(name);
		if (rt != null) {
			throw new UiException("menu item duplicated:" + name + ",in menu:" + this.getName());
		}
		rt = new MenuItemWImpl(this.container, name);
		rt.setText(true, name);
		this.appendElement(rt);
		this.childMap.put(name, rt);
		return rt;
	}

	/*
	 * Nov 12, 2012
	 */
	@Override
	public void appendElement(WebElement cw) {
		//
		if (!(cw instanceof MenuItemWI)) {
			throw new UiException("node allowed child type:" + cw);
		}

		Element li = (com.google.gwt.user.client.Element) Document.get().createLIElement().cast();
		DOM.appendChild(this.ul, li);
		DOM.appendChild(li, cw.getElement());//

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.widget.menu.MenuWI#addItem(java.lang.
	 * String, com.fs.uicommons.api.gwt.client.widget.menu.MenuWI)
	 */
	@Override
	public MenuItemWI addItem(String name, MenuWI subm) {

		MenuItemWI rt = this.addItem(name);//
		rt.setProperty("_SUBMENU", subm);

		this.addHandler(ClickEvent.TYPE, //
				new EventHandlerI<ClickEvent>() {

					@Override
					public void handle(ClickEvent e) {
						MenuWImpl.this.onClick(e);
					}
				});

		return rt;
	}

	protected void onClick(ClickEvent e) {
		if (!(e.getSource() instanceof MenuItemWI)) {
			return;
		}
		MenuItemWI miw = (MenuItemWI) e.getSource();
		MenuWI mi = (MenuWI) miw.getProperty("_SUBMENU");
		if (mi != null) {
			// mi.setVisible(true);// show sub munu;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.widget.menu.MenuWI#openBy(com.fs.uicore
	 * .api.gwt.client.core.WidgetI)
	 */
	@Override
	public void openBy(WebWidget src) {
		this.setVisible(true);//
		Point topLeft = src.getElementWrapper().getAbsoluteRectangle().getBottomLeft();
		ElementWrapper body = this.getElementWrapper().getBody();// TODO move
																	// around.
		ElementWrapper ele = this.getElementWrapper();
		ele.tryMoveInside(topLeft, body);

	}

	@Override
	public void close() {
		this.setVisible(false);// TODO remove setVisible.replace by css
								// classname.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.widget.menu.MenuWI#getItem(java.lang.
	 * String)
	 */
	@Override
	public MenuItemWI getItem(String name) {
		return this.childMap.get(name);

	}

	@Override
	public int size() {
		return this.childMap.size();
	}

}
