/**
 *  Dec 24, 2012
 */
package org.cellang.clwt.core.client.lang;

/**
 * @author wuzhen
 * 
 */
public interface DispatchListener extends Handler<DispatchingException> {

	public void handle(DispatchingException me);

}
