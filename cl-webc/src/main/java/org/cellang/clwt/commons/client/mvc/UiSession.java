/**
 * Jul 18, 2012
 */
package org.cellang.clwt.commons.client.mvc;

/**
 * @author wu
 * 
 */
public class UiSession {

	private String id;

	private String user;

	public UiSession(String id,String user){
		this.id = id;
		this.user = user;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

}
