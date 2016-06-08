package org.cellang.clwt.core.client.lang;

import java.util.ArrayList;
import java.util.List;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.webc.main.client.CellangClientPluginImpl;

import com.google.gwt.core.client.GWT;

public class Plugins {
	private static final WebLogger LOG = WebLoggerFactory.getLogger(Plugins.class);
	private Container container;

	private List<Plugin> spiList = new ArrayList<Plugin>();

	private static Plugins ME = new Plugins();

	private Plugins() {
		this.container = GWT.create(Container.class);
	}

	public static Plugins create() {
		return new Plugins();//
	}

	public static Plugins get() {
		return ME;
	}

	public Container getContainer() {
		return this.container;
	}

	public Plugins active(Plugin[] spis) {
		LOG.info("before active");

		for (int i = 0; i < spis.length; i++) {
			this.active(spis[i]);
		}
		LOG.info("after active");
		return this;
	}

	public Plugins active(Plugin spi) {
		LOG.info("before active plugin:" + spi.getClass().getName());
		spiList.add(spi);
		spi.active(this.container);
		LOG.info("after active plugin:" + spi.getClass().getName());
		return this;
	}

}