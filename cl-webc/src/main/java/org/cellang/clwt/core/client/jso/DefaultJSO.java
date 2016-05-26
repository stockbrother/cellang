/**
 *  Jan 16, 2013
 */
package org.cellang.clwt.core.client.jso;

/**
 * @author wuzhen
 * 
 */
public final class DefaultJSO extends AbstractJSO {

	protected DefaultJSO() {

	}

	public static native final DefaultJSO newInstance()
	/*-{
		return {};
	}-*/;

}
