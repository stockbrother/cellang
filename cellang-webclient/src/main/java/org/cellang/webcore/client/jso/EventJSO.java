/**
 *  Jan 15, 2013
 */
package org.cellang.webcore.client.jso;

/**
 * @author wuzhen
 * 
 */
public final class EventJSO extends AbstractJSO {

	protected EventJSO() {

	}

	public native String getData()
	/*-{
		return this.data;
	}-*/;
}
