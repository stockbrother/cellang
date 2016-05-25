/**
 * Jun 12, 2012
 */
package org.cellang.webcommon.mvc;

import org.cellang.webcore.client.lang.WebObject;

/**
 * @author wuzhen
 * 
 * 
 */
public interface ControlI extends WebObject {

	public String getName();

	public ControlManagerI getManager();

}
