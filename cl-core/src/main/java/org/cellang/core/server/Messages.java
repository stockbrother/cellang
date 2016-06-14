package org.cellang.core.server;

import org.cellang.commons.lang.NameSpace;

public class Messages {

	public static final NameSpace MSG_CLIENT_IS_READY = NameSpace.valueOf("control.status.clientIsReady");
	public static final NameSpace MSG_SERVER_IS_READY = NameSpace.valueOf("control.status.serverIsReady");

	public static final NameSpace REQ_CLIENT_INIT = NameSpace.valueOf("client.init");

	public static final NameSpace RES_CLIENT_INIT_SUCCESS = NameSpace.valueOf("client.init.success");

	public static final NameSpace AUTH_REQ = NameSpace.valueOf("auth.submit.request");
	
	public static final NameSpace LOGIN_REQ = NameSpace.valueOf("login.submit.request");
	
	public static final NameSpace PROPERTY_SAVE_REQ = NameSpace.valueOf("property.save.request");
	
	public static final NameSpace PROPERTY_GET_REQ = NameSpace.valueOf("property.get.request");
	
	public static final NameSpace SIGNUP_REQ = NameSpace.valueOf("signup.submit").getSubPath("request");

}
