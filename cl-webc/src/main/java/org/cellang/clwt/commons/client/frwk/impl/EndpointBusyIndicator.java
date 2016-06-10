/**
 * All right is from Author of the file,to be explained in comming days.
 * Apr 4, 2013
 */
package org.cellang.clwt.commons.client.frwk.impl;

import org.cellang.clwt.commons.client.mvc.simple.LightWeightView;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.event.LogicalChannelBusyEvent;
import org.cellang.clwt.core.client.event.LogicalChannelFreeEvent;
import org.cellang.clwt.core.client.transfer.LogicalChannel;

/**
 * @author wu
 * 
 */
public class EndpointBusyIndicator extends LightWeightView {

	private LogicalChannel lc;
	/**
	 * @param ctn
	 */
	public EndpointBusyIndicator(Container ctn,LogicalChannel lc) {
		super(ctn);
		this.lc = lc;
		this.getElement().addClassName("endpoint-busy-indicator");
		this.element.setInnerText("please wait...!");

		this.lc.addHandler(LogicalChannelFreeEvent.TYPE, new EventHandlerI<LogicalChannelFreeEvent>() {

			@Override
			public void handle(LogicalChannelFreeEvent t) {
				EndpointBusyIndicator.this.onEndpointBusy(false);
			}
		});
		this.lc.addHandler(LogicalChannelBusyEvent.TYPE, new EventHandlerI<LogicalChannelBusyEvent>() {

			@Override
			public void handle(LogicalChannelBusyEvent t) {
				EndpointBusyIndicator.this.onEndpointBusy(true);
			}
		});
	}
	
	public void onEndpointBusy(boolean busy) {
		this.setVisible(busy);
	}

}
