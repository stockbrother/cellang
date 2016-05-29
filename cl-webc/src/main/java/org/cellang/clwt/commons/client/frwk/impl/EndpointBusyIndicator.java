/**
 * All right is from Author of the file,to be explained in comming days.
 * Apr 4, 2013
 */
package org.cellang.clwt.commons.client.frwk.impl;

import org.cellang.clwt.commons.client.mvc.simple.LightWeightView;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.EndpointBusyEvent;
import org.cellang.clwt.core.client.event.EndpointFreeEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;

/**
 * @author wu
 * 
 */
public class EndpointBusyIndicator extends LightWeightView {

	/**
	 * @param ctn
	 */
	public EndpointBusyIndicator(Container ctn) {
		super(ctn);
		this.getElement().addClassName("endpoint-busy-indicator");
		this.element.setInnerText("please wait...!");
	}

	/*
	 * Apr 4, 2013
	 */
	@Override
	public void attach() {
		super.attach();
		this.getEndpoint().addHandler(EndpointFreeEvent.TYPE, new EventHandlerI<EndpointFreeEvent>() {

			@Override
			public void handle(EndpointFreeEvent t) {
				EndpointBusyIndicator.this.onEndpointBusy(false);
			}
		});
		this.getEndpoint().addHandler(EndpointBusyEvent.TYPE, new EventHandlerI<EndpointBusyEvent>() {

			@Override
			public void handle(EndpointBusyEvent t) {
				EndpointBusyIndicator.this.onEndpointBusy(true);
			}
		});
	}

	public void onEndpointBusy(boolean busy) {
		this.setVisible(busy);

	}

}
