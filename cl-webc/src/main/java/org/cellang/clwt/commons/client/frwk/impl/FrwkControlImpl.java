/**
 *  Jan 31, 2013
 */
package org.cellang.clwt.commons.client.frwk.impl;

import org.cellang.clwt.commons.client.CreaterI;
import org.cellang.clwt.commons.client.UiCommonsConstants;
import org.cellang.clwt.commons.client.frwk.BodyViewI;
import org.cellang.clwt.commons.client.frwk.BottomViewI;
import org.cellang.clwt.commons.client.frwk.ConsoleViewI;
import org.cellang.clwt.commons.client.frwk.FrwkControlI;
import org.cellang.clwt.commons.client.frwk.FrwkViewI;
import org.cellang.clwt.commons.client.frwk.HeaderViewI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.widget.WebWidget;
import org.cellang.webc.main.client.WebcControlSupport;
import org.cellang.webc.main.client.LoginControlI;
import org.cellang.webc.main.client.LoginViewI;

/**
 * @author wuzhen
 * 
 */
public class FrwkControlImpl extends WebcControlSupport implements FrwkControlI {

	/**
	 * @param c
	 * @param name
	 */
	public FrwkControlImpl(Container c, String name) {
		super(c, name);

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
	public void addHeaderItem(Path path) {
		this.addHeaderItem(path, false);
	}

	protected FrwkViewI getFrwkView() {
		return this.getRootView().getChild(FrwkViewI.class, true);
	}

	@Override
	public void addHeaderItem(Path path, boolean left) {
		this.getFrwkView().getHeader().addItem(path, left);

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
		ConsoleViewI rt = bv.getOrCreateItem(UiCommonsConstants.P_CONSOLE_VIEW,
				new CreaterI<ConsoleViewI>() {

					@Override
					public ConsoleViewI create(Container ct) {

						return new ConsoleView(ct);
					}
				}, show);
		return rt;
	}
}
