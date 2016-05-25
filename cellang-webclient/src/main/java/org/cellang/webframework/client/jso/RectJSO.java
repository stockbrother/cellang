/**
 *  
 */
package org.cellang.webframework.client.jso;

/**
 * @author wu
 * 
 */
public final class RectJSO extends AbstractJSO {

	protected RectJSO() {

	}

	public native int getTop()
	/*-{
		return this.top;
	}-*/;

	public native int getLeft()
	/*-{
		return this.left;
	}-*/;

	public native int getBottom()
	/*-{
	return this.bottom;
	}-*/;

	public native int getRight()
	/*-{
	 return this.right;
	 }-*/;

	public native int getWidth()
	/*-{
		return this.width;
	}-*/;

	public native int getHeight()
	/*-{
		return this.height;
	}-*/;

}
