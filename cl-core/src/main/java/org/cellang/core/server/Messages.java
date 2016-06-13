package org.cellang.core.server;

import org.cellang.commons.lang.Path;

public class Messages {

	public static final Path MSG_CLIENT_IS_READY = Path.valueOf("control/status/clientIsReady");
	public static final Path MSG_SERVER_IS_READY = Path.valueOf("control/status/serverIsReady");//

	public static final Path REQ_CLIENT_INIT = Path.valueOf("client/init");//

	public static final Path RES_CLIENT_INIT_SUCCESS = Path.valueOf("client/init/success");//

	public static final Path AUTH_REQ = Path.valueOf("auth/submit/request");
	
	public static final Path SIGNUP = Path.valueOf("signup/submit");
	
	public static final Path SIGNUP_REQ = Path.valueOf("signup/submit").getSubPath("request");

}
