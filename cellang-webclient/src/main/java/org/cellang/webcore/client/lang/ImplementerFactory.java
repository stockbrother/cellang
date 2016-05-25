package org.cellang.webcore.client.lang;

import java.util.ArrayList;
import java.util.List;

import org.cellang.webcore.client.Container;

import com.google.gwt.core.client.GWT;

public class ImplementerFactory {
	private Container container;

	private List<Implementer> spiList = new ArrayList<Implementer>();

	private static ImplementerFactory ME = new ImplementerFactory();

	private ImplementerFactory() {
		this.container = GWT.create(Container.class);
	}

	public static ImplementerFactory create() {
		return new ImplementerFactory();//
	}

	public static ImplementerFactory get() {
		return ME;
	}

	public Container getContainer() {
		return this.container;
	}

	public ImplementerFactory active(Implementer[] spis) {
		for (int i = 0; i < spis.length; i++) {
			this.active(spis[i]);
		}
		return this;
	}

	public ImplementerFactory active(Implementer spi) {
		spiList.add(spi);

		spi.active(this.container);
		return this;
	}

}