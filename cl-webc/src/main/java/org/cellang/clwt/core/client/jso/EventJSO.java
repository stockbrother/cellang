/**
 *  Jan 15, 2013
 */
package org.cellang.clwt.core.client.jso;

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
