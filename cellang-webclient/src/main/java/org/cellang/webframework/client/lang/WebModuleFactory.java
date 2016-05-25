package org.cellang.webframework.client.lang;

import java.util.ArrayList;
import java.util.List;

import org.cellang.webframework.client.Container;

import com.google.gwt.core.client.GWT;

public class WebModuleFactory {
	private Container container;

	private List<WebModule> spiList = new ArrayList<WebModule>();

	private static WebModuleFactory ME = new WebModuleFactory();

	private WebModuleFactory() {
		this.container = GWT.create(Container.class);
	}

	public static WebModuleFactory create() {
		return new WebModuleFactory();//
	}

	public static WebModuleFactory get() {
		return ME;
	}

	public Container getContainer() {
		return this.container;
	}

	public WebModuleFactory active(WebModule[] spis) {
		for (int i = 0; i < spis.length; i++) {
			this.active(spis[i]);
		}
		return this;
	}

	public WebModuleFactory active(WebModule spi) {
		spiList.add(spi);

		spi.active(this.container);
		return this;
	}

}