/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 3, 2012
 */
package org.cellang.core.lang;

import org.cellang.commons.lang.NameSpace;

/**
 * @author wuzhen
 * 
 */
public interface MessageI {

	/**
	 * Header key, its value is The Type of the message.
	 */
	public static final String HK_PATH = "_path";

	/**
	 * Header key, its value is The identifier of the message.
	 */
	public static final String HK_ID = "_id";

	/**
	 * Header key, its value is The source message's identifier.<br>
	 * For e.g. if the message is a Response message,the source id the Request
	 * message.
	 */
	public static final String HK_SOURCE_ID = "_source_id";

	public static final String HK_SILENCE = "_silence";

	public static final String HK_ERROR_PROCESSOR = "_eprocessor";

	// when this message is processed, the result send to this address.
	public static final String HK_CHANNEL = "_channel";

	public static final String PK_DEFAULT = "_default";

	public static final String PK_ERROR_INFO_S = "_ERROR_INFO_S";

	public ErrorInfos getErrorInfos();

	public void assertNoError();

	public String getErrorProcessor();

	public String getSourceId();

	public NameSpace getPath();

	public String getId();
	
	public void setChannelId(String cid);

	public String getChannelId();

	public boolean isSilence();

	public HasProperties<String> getHeaders();

	public HasProperties<Object> getPayloads();

	public String getHeader(String key);

	public String getHeader(String key, boolean force);

	public String getHeader(String key, String def);

	public void setHeader(String key, String value);

	public void setHeaders(HasProperties<String> hds);

	public Object getPayload();

	public Object getPayload(String key);

	public Object getPayload(String key, boolean force);

	public Object getPayload(String key, Object def);

	public <T> T getPayload(Class<T> cls, String key, T def);

	public String getString(String key);

	public String getString(String key, boolean force);

	public String getString(String key, String def);

	public boolean getBoolean(String key, boolean def);

	public void setPayload(String key, Object value);

	public void setPayload(Object pl);

	public void setPayloads(HasProperties<Object> pls);

	public void setMessage(MessageI msg);

	public MessageI path(NameSpace path);

}
