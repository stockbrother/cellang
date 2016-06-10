/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.mvc.Control;

/**
 * @author wu
 * 
 */
public interface MainControlI extends Control {

	public SignupViewI openSignup();

	public void closeSignup();
	
	public LoginViewI openLoginView(boolean show);
	
	public void closeLoginView();
	
	public void closeAll();
	/**
	public ExpSearchViewI openExpSearch(boolean show);

	public UserExpListViewI openUeList(boolean show);

	public UserInfoViewI openUserInfo(boolean show);
	
	public MyExpViewI openMyExp(Cause cause, String expId, boolean show);


	public ExpEditViewI openExpEditView();

	public ProfileViewI openProfile();

	public void refreshExpConnect(String expId);

	public void refreshExpMessage(Cause cause, String expId);

	public void expDeleted(String expId);

	public void expClosed(String expId);
	
	public void setExpDetail(MyExp me);

	public void refreshUeList();
	
	public void refreshUeList(String expId);


	public HtmlElementWidgetI openHtmlResource(Path path, boolean refresh);

	
	
	public ContactUsViewI openContactUsView(boolean show);
**/
}
