package org.cellang.webc.test.client;

import org.cellang.clwt.commons.client.CommonsPlugin;
import org.cellang.clwt.commons.test.client.AbstractGwtTestBase2;
import org.cellang.clwt.core.client.ClientObject;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.WebCorePlugin;
import org.cellang.clwt.core.client.lang.Plugin;
import org.cellang.webc.main.client.CellangClientPlugin;

import com.google.gwt.core.client.GWT;

public abstract class AbstractGwtTestBase3 extends AbstractGwtTestBase2 {

	protected ClientObject client;
	protected Container container;

	@Override
	public String getModuleName() {
		return "org.cellang.webc.test.Module";
	}

	@Override
	protected Plugin[] getPlugins() {
		return new Plugin[] { //
				(WebCorePlugin) GWT.create(WebCorePlugin.class), //
				(CommonsPlugin) GWT.create(CommonsPlugin.class), //
				(CellangClientPlugin) GWT.create(CellangClientPlugin.class) //
		};
	}

}
