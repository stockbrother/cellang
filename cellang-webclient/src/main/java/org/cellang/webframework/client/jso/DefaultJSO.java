/**
 *  Jan 16, 2013
 */
package org.cellang.webframework.client.jso;

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
