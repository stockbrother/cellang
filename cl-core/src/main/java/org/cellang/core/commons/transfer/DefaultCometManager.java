/**
 *  Dec 11, 2012
 */
package org.cellang.core.commons.transfer;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.cellang.core.lang.util.StringUtil;

/**
 * @author wuzhen
 *         <p>
 *         1-1 mapping to Servlet
 */
public class DefaultCometManager implements CometManager, CometListener {

	protected CollectionCometListener listeners;// user listener

	protected String name;

	protected Map<String, Comet> cometMap;

	public DefaultCometManager(String name) {
		this.name = name;
		this.listeners = new CollectionCometListener();
		this.cometMap = Collections.synchronizedMap(new HashMap<String, Comet>());
	}

	@Override
	public void addListener(CometListener ln) {
		this.listeners.add(ln);
	}

	@Override
	public String getName() {

		return name;
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public Comet getComet(String id) {
		//
		return this.getComet(id, false);

	}

	@Override
	public Comet getComet(String id, boolean force) {
		Comet rt = this.cometMap.get(id);
		if (rt == null && force) {
			throw new RuntimeException("no websocket:" + id);
		}
		return rt;
	}

	protected String nextId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public List<Comet> getCometList() {
		//
		return new ArrayList<Comet>(this.cometMap.values());
	}

	/*
	 * Dec 12, 2012
	 */

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(Comet ws, int statusCode, String reason) {
		//
		Comet old = this.cometMap.remove(ws.getId());
		if (old == null) {
			throw new RuntimeException("bug,no this websocket:" + ws.getId());
		}
		this.listeners.onClose(ws, statusCode, reason);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onMessage(Comet ws, String ms) {
		this.listeners.onMessage(ws, ms);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onException(Comet ws, Throwable t) {
		//
		this.listeners.onException(ws, t);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onConnect(Comet ws) {
		this.cometMap.put(ws.getId(), ws);//
		this.listeners.onConnect(ws);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.websocket.api.WsListenerI#onMessage(com.fs.websocket.api.
	 * WebSocketI, java.io.Reader)
	 */
	@Override
	public void onMessage(Comet ws, Reader reader) {
		String s = StringUtil.readAsString(reader);
		this.onMessage(ws, s);
	}

}
