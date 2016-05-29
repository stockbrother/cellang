/**
 *  Jan 31, 2013
 */
package org.cellang.clwt.commons.client.frwk;

import org.cellang.clwt.commons.client.mvc.ViewI;

/**
 * @author wuzhen
 * 
 */
public interface FrwkViewI extends ViewI {

	public BodyViewI getBodyView();

	public HeaderViewI getHeader();
	
	public BottomViewI getBottom();

}
