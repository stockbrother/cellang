/**
 *  
 */
package org.cellang.webcore.client.lang;

/**
 * @author wu
 * 
 */
public class Address {

	private String protocol;
	private String host;
	private int port;
	private String resource;

	public Address(String pro,  String host, int port, String res) {
		this.port = port;
		this.host = host;
		this.protocol = pro;
		this.resource = res;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUri() {
		return this.protocol + "://" + this.host + ":" + this.port + this.resource;
	}

	/*
	 *May 12, 2013
	 */
	@Override
	public String toString() {
		return this.getUri();
	}
	
	

}
