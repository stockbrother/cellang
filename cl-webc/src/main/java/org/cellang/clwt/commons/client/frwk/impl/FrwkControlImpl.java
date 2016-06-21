/**
 *  Jan 31, 2013
 */
package org.cellang.clwt.commons.client.frwk.impl;

import org.cellang.clwt.commons.client.AbstractCommonsControl;
import org.cellang.clwt.commons.client.CreaterI;
import org.cellang.clwt.commons.client.UiCommonsConstants;
import org.cellang.clwt.commons.client.frwk.BodyViewI;
import org.cellang.clwt.commons.client.frwk.BottomViewI;
import org.cellang.clwt.commons.client.frwk.ConsoleViewI;
import org.cellang.clwt.commons.client.frwk.FrwkControlI;
import org.cellang.clwt.commons.client.frwk.FrwkViewI;
import org.cellang.clwt.commons.client.frwk.HeaderItemEvent;
import org.cellang.clwt.commons.client.frwk.HeaderViewI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.DispatcherImpl;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.PathBasedDispatcher;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.widget.WebWidget;
import org.cellang.webc.main.client.LoginControlI;
import org.cellang.webc.main.client.LoginViewI;
import org.cellang.webc.main.client.handler.ClientStartingHandler;

/**
 * @author wuzhen
 * 
 */
public class FrwkControlImpl extends AbstractCommonsControl implements FrwkControlI {
	private static final WebLogger LOG = WebLoggerFactory.getLogger(FrwkControlImpl.class);

	private PathBasedDispatcher headerItemDispatcher;

	/**
	 * @param c
	 * @param name
	 */
	public FrwkControlImpl(Container c, String name) {
		super(c, name);
		this.headerItemDispatcher = new DispatcherImpl("header-item-dispatcher");
	}

	@Override
	public void open() {
		

		WebWidget root = this.getClient(true).getRoot();

		FrwkViewI fv = root.getChild(FrwkViewI.class, false);

		if (fv == null) {
			fv = new FrwkView(this.container);
			fv.parent(root);

		}
	}

	@Override
	public void addHeaderItem(Path path, EventHandlerI<HeaderItemEvent> hdl) {
		this.addHeaderItem(path, false, hdl);
	}

	protected FrwkViewI getFrwkView() {
		return this.getRootView().getChild(FrwkViewI.class, true);
	}

	@Override
	public void addHeaderItem(Path path, boolean left, EventHandlerI<HeaderItemEvent> hdl) {
		this.getFrwkView().getHeader().addItem(path, left, hdl);

	}

	/*
	 * Feb 1, 2013
	 */
	@Override
	public LoginViewI openLoginView(boolean show) {
		//
		LoginControlI lc = this.getControl(LoginControlI.class, true);
		return lc.openLoginView(show);

	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public HeaderViewI getHeaderView() {
		//
		return this.getFrwkView().getHeader();
	}

	@Override
	public BottomViewI getBottomView() {
		//
		return this.getFrwkView().getBottom();
	}

	/*
	 * Mar 30, 2013
	 */
	@Override
	public void addHeaderItemIfNotExist(Path path) {
		//
		this.getHeaderView().addItemIfNotExist(path);
	}

	@Override
	public void tryRemoveHeaderItem(Path path) {
		this.getHeaderView().tryRemoveItem(path);
	}

	@Override
	public ConsoleViewI openConsoleView(boolean show) {
		BodyViewI bv = this.getFrwkView().getBodyView();
		ConsoleViewI rt = bv.getOrCreateItem(UiCommonsConstants.P_CONSOLE_VIEW, new CreaterI<ConsoleViewI>() {

			@Override
			public ConsoleViewI create(Container ct) {

				return new ConsoleView(ct);
			}
		}, show);
		return rt;
	}
}
