/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package org.cellang.webcore.client.event;

import org.cellang.webcore.client.lang.Size;
import org.cellang.webcore.client.lang.WebObject;

/**
 * @author wu
 * 
 */
public class SizeChangeEvent extends Event {

	public static final Type<SizeChangeEvent> TYPE = new Type<SizeChangeEvent>("size-change");

	private Size size;

	/**
	 * @param type
	 */
	public SizeChangeEvent(Size size, WebObject src) {
		super(TYPE);
	}

	/**
	 * @return the size
	 */
	public Size getSize() {
		return size;
	}

}
