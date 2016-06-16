/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 3, 2012
 */
package org.cellang.core.lang;

import java.util.UUID;

import org.cellang.commons.lang.NameSpace;

/**
 * @author wuzhen
 * 
 */
public class MessageSupport implements MessageI {

	private HasProperties<String> headers;

	private HasProperties<Object> payloads;

	public MessageSupport() {
		this(null);
	}

	public MessageSupport(String path) {
		this(path, UUID.randomUUID().toString());
	}

	public MessageSupport(String path, String id) {
		this.headers = new MapProperties<String>();
		this.payloads = new MapProperties<Object>();
		if (path != null) {
			this.headers.setProperty(HK_PATH, path);
		}
		if (id != null) {
			this.headers.setProperty(HK_ID, id);
		}

	}

	public static MessageI newMessage(NameSpace p) {
		return new MessageImpl(p.toString());
	}

	public static MessageI newMessage() {
		return new MessageImpl();
	}

	/* */
	@Override
	public ErrorInfos getErrorInfos() {
		return this.getOrCreateErrorInfos();
	}

	public ErrorInfos getOrCreateErrorInfos() {
		ErrorInfos rt = (ErrorInfos) this.getPayload(PK_ERROR_INFO_S);
		if (rt == null) {
			rt = new ErrorInfos();
			this.setPayload(PK_ERROR_INFO_S, rt);
		}
		return rt;

	}

	/*
	 * Nov 3, 2012
	 */
	@Override
	public void assertNoError() {
		ErrorInfos ers = (ErrorInfos) this.getPayload(PK_ERROR_INFO_S);
		if (ers != null && ers.hasError()) {
			throw new RuntimeException(ers.toString());

		}
	}

	/* */
	@Override
	public HasProperties<String> getHeaders() {

		return this.headers;

	}

	/* */
	@Override
	public HasProperties<Object> getPayloads() {

		return this.payloads;

	}

	@Override
	public String getHeader(String key) {
		//
		return this.headers.getProperty(key);
		//
	}

	@Override
	public void setHeader(String key, String value) {
		//
		this.headers.setProperty(key, value);//
	}

	@Override
	public Object getPayload(String key) {
		//
		return this.payloads.getProperty(key);
		//
	}

	@Override
	public void setPayload(String key, Object value) {
		//
		this.payloads.setProperty(key, value);
		//
	}

	/*
	
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof MessageI)) {
			return false;
		}
		MessageI m = (MessageI) obj;
		if (!m.getHeaders().equals(this.headers)) {
			return false;
		}
		if (!this.payloads.equals(m.getPayloads())) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{headers:");
		sb.append(this.headers.toString());
		sb.append(",payloads:");
		sb.append(this.payloads.toString());
		sb.append("}");
		return sb.toString();
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public String getHeader(String key, boolean force) {
		//
		String rt = this.getHeader(key);
		if (force && rt == null) {
			throw new RuntimeException("no header found:" + key);
		}
		return rt;
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public String getHeader(String key, String def) {

		String rt = this.getHeaders().getProperty(key);
		if (rt == null) {
			return def;
		}
		return rt;
	}

	/* */
	@Override
	public Object getPayload() {

		return this.getPayloads().getProperty(PK_DEFAULT);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public Object getPayload(String key, boolean force) {
		//
		Object rt = this.getPayloads().getProperty(key);
		if (force && rt == null) {
			throw new RuntimeException(
					"force payload key:" + key + ",all payload keys:" + this.getPayloads().keyList());
		}
		return rt;
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public <T> T getPayload(Class<T> cls, String key, T def) {
		//
		Object rt = this.getPayload(key);
		if (rt == null) {
			return def;
		}
		return (T) rt;
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void setPayload(Object pl) {
		this.getPayloads().setProperty(PK_DEFAULT, pl);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void setPayloads(HasProperties<Object> pls) {
		this.payloads.setProperties(pls);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void setHeaders(HasProperties<String> hds) {
		this.headers.setProperties(hds);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void setMessage(MessageI msg) {
		this.setHeaders(msg.getHeaders());
		this.setPayloads(msg.getPayloads());
	}

	/**
	 * Dec 15, 2012
	 */
	public static MessageI newMessage(HasProperties<String> hds, HasProperties<Object> pls) {
		MessageI rt = newMessage();
		rt.setHeaders(hds);
		rt.setPayloads(pls);
		return rt;
	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public String getString(String key) {
		//
		return (String) this.getPayload(key);

	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public String getString(String key, boolean force) {
		//
		return (String) this.getPayload(key, force);
	}

	@Override
	public Object getPayload(String key, Object def) {
		Object rt = this.getPayload(key);
		return rt == null ? def : rt;

	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public String getString(String key, String def) {
		//
		return (String) this.getPayload(key, def);
	}

	@Override
	public String getId() {
		return this.getHeader(HK_ID);
	}

	@Override
	public NameSpace getPath() {
		String ps = this.getHeader(HK_PATH);
		return NameSpace.valueOf(ps);
	}

	@Override
	public String getErrorProcessor() {
		// TODO Auto-generated method stub
		return this.getHeader(HK_ERROR_PROCESSOR);
	}

	@Override
	public String getSourceId() {
		return this.getHeader(HK_SOURCE_ID);
	}

	@Override
	public void setChannelId(String cid) {
		this.setHeader(HK_CHANNEL, cid);
	}

	@Override
	public String getChannelId() {
		return this.getHeader(HK_CHANNEL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.message.MessageI#getBoolean(java.lang.String,
	 * boolean)
	 */
	@Override
	public boolean getBoolean(String key, boolean def) {
		Object obj = this.getPayload(key);
		if (obj == null) {
			return def;
		}
		return (Boolean) obj;
	}

	/*
	 * Jan 29, 2013
	 */
	@Override
	public boolean isSilence() {
		//
		String h = this.getHeader(HK_SILENCE, "false");
		return "true".equalsIgnoreCase(h) || "yes".equalsIgnoreCase(h);

	}

	@Override
	public MessageI path(NameSpace path) {
		//
		if (path == null) {
			this.setHeader(HK_PATH, null);//
		} else {
			this.setHeader(HK_PATH, path.toString());
		}

		return this;
	}

}
