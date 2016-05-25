/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 20, 2012
 */
package org.cellang.webcore.client.event;

import org.cellang.webcore.client.lang.WebObject;

/**
 * @author wuzhen
 * 
 */
public class AttachedEvent extends AttachEvent {

	public static final Type<AttachedEvent> TYPE = new Type<AttachedEvent>(
			AttachEvent.TYPE,"attached");

	/** */
	public AttachedEvent(WebObject src) {
		super(TYPE, src);
	}
}
