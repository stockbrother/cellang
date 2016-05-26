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
public abstract class AbstractCometListener implements CometListener {

	protected static final Logger LOG = LoggerFactory.getLogger(AbstractCometListener.class);

	@Override
	public void onMessage(Comet ws, Reader reader) {
		String s = StringUtil.readAsString(reader);
		this.onMessage(ws, s);
	}

}
