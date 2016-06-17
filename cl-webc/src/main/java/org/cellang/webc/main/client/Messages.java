package org.cellang.webc.main.client;

import org.cellang.clwt.core.client.lang.Path;

public class Messages {
	
	
	public static final Path MSG_CLIENT_IS_READY = Path.valueOf("control.status.clientIsReady");
																
	public static final Path MSG_SERVER_IS_READY = Path.valueOf("control.status.serverIsReady");

	public static final Path REQ_CLIENT_INIT = Path.valueOf("client.init");

	public static final Path RES_CLIENT_INIT_SUCCESS = Path.valueOf("client.init.success");

	public static final Path LOGIN_REQ = Path.valueOf("login.submit.request");
	
	public static final Path SHEET_SAVE_REQ = Path.valueOf("sheet.save.request");
	
	public static final Path SHEET_GET_REQ = Path.valueOf("sheet.get.request");
	
	public static final Path SHEET_LIST_REQ = Path.valueOf("sheet.list.request");
	
	public static final Path SIGNUP_REQ = Path.valueOf("signup.submit.request");
	
	public static final Path SIGNUP_RES = Path.valueOf("signup.submit.response");
	
}
