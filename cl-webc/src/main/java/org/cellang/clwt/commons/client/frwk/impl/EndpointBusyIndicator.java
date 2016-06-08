/**
 * All right is from Author of the file,to be explained in comming days.
 * Apr 4, 2013
 */
package org.cellang.clwt.commons.client.frwk.impl;

import org.cellang.clwt.commons.client.mvc.simple.LightWeightView;
import org.cellang.clwt.core.client.Container;

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
	
	/**TODO listener endpoint ready event.
	 <code>
	 this.getEndpoint().addHandler(LogicalChannelFreeEvent.TYPE, new EventHandlerI<LogicalChannelFreeEvent>() {

			@Override
			public void handle(LogicalChannelFreeEvent t) {
				EndpointBusyIndicator.this.onEndpointBusy(false);
			}
		});
		this.getEndpoint().addHandler(LogicalChannelBusyEvent.TYPE, new EventHandlerI<LogicalChannelBusyEvent>() {

			@Override
			public void handle(LogicalChannelBusyEvent t) {
				EndpointBusyIndicator.this.onEndpointBusy(true);
			}
		});
	 </code>
	 
	 */
	public void onEndpointBusy(boolean busy) {
		this.setVisible(busy);
	}

}
