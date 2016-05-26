package org.cellang.clwt.core.client.lang;

import java.util.ArrayList;
import java.util.List;

import org.cellang.clwt.core.client.Container;

import com.google.gwt.core.client.GWT;

public class Plugins {
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
		for (int i = 0; i < spis.length; i++) {
			this.active(spis[i]);
		}
		return this;
	}

	public Plugins active(Plugin spi) {
		spiList.add(spi);

		spi.active(this.container);
		return this;
	}

}