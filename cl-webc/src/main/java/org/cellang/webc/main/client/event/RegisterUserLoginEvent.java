/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 26, 2012
 */
package org.cellang.webc.main.client.event;

import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * @deprecated
 */
public class RegisterUserLoginEvent extends UserLoginEvent {

	public static final Type<RegisterUserLoginEvent> TYPE = new Type<RegisterUserLoginEvent>(UserLoginEvent.TYPE,"registered");

	/**
	 * @param type
	 */
	public RegisterUserLoginEvent(WebObject src, ObjectPropertiesData ui) {
		super(TYPE, src, ui);
	}

}
