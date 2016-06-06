package org.cellang.core.server;

import java.util.HashMap;
import java.util.Map;

public class ClientManager {

	public static ClientManager ME = new ClientManager();

	protected Map<String, Object> clientMap = new HashMap<String, Object>();

	public Object getClient(String id) {
		return clientMap.get(id);
	}

	public void putClient(String id, Object client) {
		this.clientMap.put(id, client);
	}
}
