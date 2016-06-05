package org.cellang.core.server;

import org.cellang.commons.lang.Path;

public class Messages {

	public static final Path MSG_CLIENT_IS_READY = Path.valueOf("/control/status/clientIsReady");
	public static final Path MSG_SERVER_IS_READY = Path.valueOf("control/status/serverIsReady");//
	public static final Path MSG_AUTH = Path.valueOf("msg/request/auth");
	public static final Path MSG_SIGNUP = Path.valueOf("msg/request/signup");

}
