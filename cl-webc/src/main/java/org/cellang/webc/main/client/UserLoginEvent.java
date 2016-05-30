/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 26, 2012
 */
package org.cellang.webc.main.client;

import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * @deprecated
 */
public class UserLoginEvent extends Event {

	public static final Type<UserLoginEvent> TYPE = new Type<UserLoginEvent>("user-login");

	private ObjectPropertiesData userInfo;

	/**
	 * @param type
	 */
	public UserLoginEvent(WebObject src, ObjectPropertiesData ui) {
		this(TYPE, src, ui);
	}

	public UserLoginEvent(Type<? extends UserLoginEvent> type, WebObject src, ObjectPropertiesData ui) {
		super(type, src);
		this.userInfo = ui;
	}

	/**
	 * @return the userInfo
	 */
	public ObjectPropertiesData getUserInfo() {
		return userInfo;
	}

}
