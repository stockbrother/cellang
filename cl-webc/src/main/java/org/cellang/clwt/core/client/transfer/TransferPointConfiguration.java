/**
 *  
 */
package org.cellang.clwt.core.client.transfer;

import java.util.ArrayList;
import java.util.List;

import org.cellang.clwt.core.client.UiCoreConstants;
import org.cellang.clwt.core.client.WebException;

import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class TransferPointConfiguration {

	public static class ProtocolPort {

		/**
		 * @param pro
		 * @param port2
		 */
		public ProtocolPort(String pro, int port2) {
			this.protocol = pro;
			this.port = port2;
		}

		public String protocol;

		public int port;

	}

	private List<ProtocolPort> configuredL = new ArrayList<ProtocolPort>();

	private TransferPointConfiguration(List<ProtocolPort> ppL) {
		this.configuredL.addAll(ppL);
	}

	public static TransferPointConfiguration getInstance() {
		List<ProtocolPort> ppL = new ArrayList<ProtocolPort>();

		String config = Window.Location.getParameter(UiCoreConstants.PK_WS_PROTOCOL_PORT_S);

		if (config != null) {

			String[] ppSs = config.split(",");
			for (int i = 0; i < ppSs.length; i++) {
				String ppS = ppSs[i];
				String[] ppI = ppS.split(":");
				String pro = null;
				String portS = null;
				if (ppI.length == 1) {
					pro = "ws";
					portS = ppI[0];
				} else {//length is 2
					pro = ppI[0];
					portS = ppI[1];
				}
				int port = Integer.parseInt(portS);
				ProtocolPort pp = new ProtocolPort(pro, port);
				ppL.add(pp);
			}
		}


		return new TransferPointConfiguration(ppL);
	}

	public List<ProtocolPort> getConfiguredList() {
		return configuredL;
	}

	public ProtocolPort getFirst(boolean force) {
		if (this.configuredL.isEmpty()) {
			if (force) {
				throw new WebException("no any configured");
			}
			return null;
		}
		return this.configuredL.get(0);
	}

	/**
	 * @return
	 */
	public String getAsParameter() {
		String rt = "";

		for (int i = 0; i < this.configuredL.size(); i++) {
			ProtocolPort pp = this.configuredL.get(i);
			if (!pp.protocol.equals("ws")) {// not default protocol
				rt += pp.protocol + ":";
			}
			rt += pp.port;
			if (i < this.configuredL.size() - 1) {
				rt += ",";// CSV
			}
		}

		return rt;
	}

}
