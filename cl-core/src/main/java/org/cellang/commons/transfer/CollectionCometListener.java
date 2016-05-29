/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 12, 2012
 */
package org.cellang.commons.transfer;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.cellang.core.lang.util.StringUtil;

/**
 * @author wu
 * 
 */
public class CollectionCometListener implements CometListener {

	protected List<CometListener> listeners;

	public CollectionCometListener() {
		this.listeners = new ArrayList<CometListener>();
	}

	@Override
	public void onMessage(Comet ws, String msg) {
		//
		for (CometListener l : this.listeners) {
			l.onMessage(ws, msg);
		}
	}

	@Override
	public void onException(Comet ws, Throwable t) {
		//
		//
		for (CometListener l : this.listeners) {
			l.onException(ws, t);
		}
	}

	@Override
	public void onConnect(Comet ws) {
		//
		//
		for (CometListener l : this.listeners) {
			l.onConnect(ws);
		}
	}

	@Override
	public void onClose(Comet ws, int statusCode, String reason) {
		//
		//
		for (CometListener l : this.listeners) {
			l.onClose(ws, statusCode, reason);
		}
	}

	/**
	 * Dec 12, 2012
	 */
	public void add(CometListener ln) {
		this.listeners.add(ln);
	}

}
