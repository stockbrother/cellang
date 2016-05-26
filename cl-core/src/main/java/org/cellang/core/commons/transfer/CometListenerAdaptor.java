/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 12, 2012
 */
package org.cellang.core.commons.transfer;

import java.io.Reader;

import org.cellang.core.lang.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wu
 * 
 */
public class CometListenerAdaptor implements CometListener {

	protected static final Logger LOG = LoggerFactory.getLogger(CometListenerAdaptor.class);

	@Override
	public void onMessage(Comet ws, Reader reader) {
		String s = StringUtil.readAsString(reader);
		this.onMessage(ws, s);
	}

	@Override
	public void onConnect(Comet ws) {

	}

	@Override
	public void onMessage(Comet ws, String ms) {

	}

	@Override
	public void onException(Comet ws, Throwable t) {

	}

	@Override
	public void onClose(Comet ws, int statusCode, String reason) {

	}

}
