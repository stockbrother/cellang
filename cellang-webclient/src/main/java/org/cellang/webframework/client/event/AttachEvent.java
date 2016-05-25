/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 20, 2012
 */
package org.cellang.webframework.client.event;

import org.cellang.webframework.client.lang.WebObject;

/**
 * @author wuzhen
 * 
 */
public abstract class AttachEvent extends Event {

	public static final Type<AttachEvent> TYPE = new Type<AttachEvent>("attach");

	/** */
	public AttachEvent(Type<? extends AttachEvent> type, WebObject src) {
		super(type, src);
	}
}
