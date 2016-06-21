package org.cellang.webc.test.client;

import org.cellang.clwt.core.client.event.ClientStartedEvent;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.transfer.LogicalChannel;

public class ClientStartGwtTest extends AbstractGwtTestBase3 {

	
	protected LogicalChannel endpoint;

	public void testClientStart() {
		this.loadClient();
		this.client.start();
		System.out.println("testClientStart");

		this.finishing.add("start");
		// this.finishing.add("signup");

		this.delayTestFinish(10 * 1000);
	}

	@Override
	protected void onEvent(Event e) {
		System.out.println("onEvent(),evt:" + e);
		if (e instanceof ClientStartedEvent) {
			ClientStartedEvent afe = (ClientStartedEvent) e;
			this.onClientStart(afe);
		}
	}

	private void onClientStart(ClientStartedEvent afe) {
		this.tryFinish("start");//
		this.endpoint = this.client.getLogicalChannel(true);

	}

}
