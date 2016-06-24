/**
 * All right is from Author of the file,to be explained in comming days.
 * Feb 1, 2013
 */
package org.cellang.webc.main.client.widget;

import org.cellang.clwt.commons.client.mvc.ViewI;

/**
 * @author wu
 * 
 */
public interface SignupViewI extends ViewI {

	public String getEmail();
	
	public String getNick();

	public String getPassword();

	public void setEmail(String email);

	public void setPassword(String pass);
	
	public void setNick(String nick);

}
