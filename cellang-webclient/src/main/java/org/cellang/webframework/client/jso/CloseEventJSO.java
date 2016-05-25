/**
 *  Jan 15, 2013
 */
package org.cellang.webframework.client.jso;

/**
 * @author wuzhen <code>
 * http://www.w3.org/TR/2011/WD-websockets-20110419/#handler-websocket-onclose)
 * 
 * interface CloseEvent : Event {
  readonly attribute boolean wasClean;
  readonly attribute unsigned long code;
  readonly attribute DOMString reason;
  void initCloseEvent(in DOMString typeArg, in boolean canBubbleArg, in boolean cancelableArg, in boolean wasCleanArg, in unsigned long codeArg, in unsigned long reasonArg);
 * </code>
 * 
 */
public final class CloseEventJSO extends AbstractJSO {

	protected CloseEventJSO() {

	}
	
	
	public native boolean getWasClean()
	/*-{
		return this.wasClean;
	}-*/;
	
	public native int getCode()
	/*-{
		return this.code;
	}-*/;
	
	public native String getReason()
	/*-{
		return this.reason;
	}-*/;

	
}
