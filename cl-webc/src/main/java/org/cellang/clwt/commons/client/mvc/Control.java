/**
 * Jun 12, 2012
 */
package org.cellang.clwt.commons.client.mvc;

import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wuzhen
 * 
 * 
 */
public interface Control extends WebObject {

	public String getName();

	public ControlManager getManager();

}
