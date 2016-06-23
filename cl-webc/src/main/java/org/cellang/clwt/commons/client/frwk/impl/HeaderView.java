/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package org.cellang.clwt.commons.client.frwk.impl;

import java.util.HashMap;
import java.util.Map;

import org.cellang.clwt.commons.client.frwk.HeaderItemEvent;
import org.cellang.clwt.commons.client.frwk.HeaderViewI;
import org.cellang.clwt.commons.client.mvc.simple.SimpleView;
import org.cellang.clwt.commons.client.widget.BarWidgetI;
import org.cellang.clwt.commons.client.widget.MenuItemWI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.DispatcherImpl;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.PathBasedDispatcher;
import org.cellang.clwt.core.client.lang.Position;

/**
 * @author wu
 * 
 */
public class HeaderView extends SimpleView implements HeaderViewI {

	private BarWidgetI itemList;

	private Map<Path, ItemView> itemViewMap = new HashMap<Path, ItemView>();

	private PathBasedDispatcher headerItemDispatcher;
	
	/**
	 * @param ctn
	 */
	public HeaderView(Container c) {
		super(c, "header");
		this.itemList = this.factory.create(BarWidgetI.class);
		this.appendElement(this.itemList);//
		this.itemList.getElement().addClassName("header-bar");
		this.headerItemDispatcher = new DispatcherImpl("header-item-dispatcher");

	}

	public ItemView getOrCreateItem(Path path, boolean preferLeft) {

		ItemView rt = this.getItem(path);
		if (rt != null) {
			return rt;
		}
		rt = new ItemView(this.getContainer(), path);
		Position p = preferLeft ? BarWidgetI.P_LEFT : BarWidgetI.P_RIGHT;
		this.itemList.addItem(p, rt);
		this.itemViewMap.put(path, rt);
		rt.addHandler(HeaderItemEvent.TYPE, new EventHandlerI<HeaderItemEvent>() {

			@Override
			public void handle(HeaderItemEvent t) {
				HeaderView.this.headerItemDispatcher.dispatch(t.getHeaderItemPath(), t);//
			}
		});
		return rt;
	}

	/*
	 * Mar 30, 2013
	 */
	@Override
	public void tryRemoveItem(Path path) {
		ItemView iv = this.getItem(path);
		if (iv == null) {
			return;
		}
		this.removeElement(iv);//
		this.itemViewMap.remove(iv);
	}

	public ItemView getItem(Path path) {
		ItemView rt = this.itemViewMap.get(path);
		return rt;
	}

	@Override
	public void addItem(Path path, EventHandlerI<HeaderItemEvent> hdl) {
		this.addItem(path, false, hdl);
	}

	@Override
	public void addItem(Path path, boolean left, EventHandlerI<HeaderItemEvent> hdl) {

		if (path.size() == 1) {
			ItemView rt = this.getOrCreateItem(path, left);
		} else if (path.size() == 2) {
			ItemView rt = this.getOrCreateItem(path.getParent(), left);
			MenuItemWI m = rt.getOrAddMenuItem(path.getName());
		} else {
			throw new UiException("not support deeper menu for path:" + path);
		}

		this.headerItemDispatcher.addHandler(path, hdl);//
	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public void setItemDisplayText(Path path, boolean toloc, String text) {
		if (toloc) {
			text = this.getClient(true).localized(text);
		}
		ItemView iv = this.getOrCreateItem(path, false);
		iv.setDisplayText(false, text);
	}

	/*
	 * Mar 30, 2013
	 */
	@Override
	public void addItemIfNotExist(Path path) {
		//
		if (null != this.getItem(path)) {
			return;
		}
		this.addItem(path, null);
	}

	@Override
	public void _clickItem(Path path) {
		if (path.size() == 1) {
			ItemView iv = this.getItem(path);
			iv._click();
		} else if (path.size() == 2) {
			ItemView iv = this.getItem(path.getParent());
			if (iv == null) {
				throw new RuntimeException("no this item:" + path);
			}
			iv._clickMenuItem(path.getName());
		} else {
			throw new RuntimeException("not supported.");
		}
	}

}
