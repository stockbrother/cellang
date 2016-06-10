/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package org.cellang.webc.main.client;

import java.util.HashMap;
import java.util.Map;

import org.cellang.clwt.commons.client.frwk.FormViewI;
import org.cellang.clwt.commons.client.frwk.impl.FormsView;
import org.cellang.clwt.commons.client.mvc.widget.ButtonI;
import org.cellang.clwt.commons.client.mvc.widget.StringEditorI;
import org.cellang.clwt.commons.client.mvc.widget.ViewReferenceI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;

/**
 * @author wu
 * 
 */
public class SignupView extends FormsView implements SignupViewI {

	private static WebLogger LOG = WebLoggerFactory.getLogger(SignupView.class);

	public static String HEADER_ITEM_SIGNUP = "signup";//


	private ViewReferenceI managed;

	/**
	 * @param ctn
	 */
	public SignupView(Container ctn) {
		super(ctn, "signup");
		//
		this.addAction(Actions.A_SIGNUP_SUBMIT);

		//ButtonI fb = this.addAction(Actions.A_SIGNUP_FBLOGIN);
		//fb.getElement().addClassName("facebook-login-button");
		// form1
		FormViewI def = this.getDefaultForm();
		// actions for form1
		def.getFormModel().addAction(Actions.A_SIGNUP_SUBMIT);
		//def.getFormModel().addAction(Actions.A_SIGNUP_FBLOGIN);

		// fields1
		def.addField("nick", String.class);
		Map<String,Object> pts= new HashMap<String,Object>();
		pts.put(StringEditorI.PK_ISPASSWORD, Boolean.TRUE);
		def.addField("password", String.class, pts);
		def.addField("email", String.class);

	}
//
//	// show or hidden this view by model value
//	protected void onSignupRequired(ModelValueEvent e) {
//		boolean sr = e.getValue(Boolean.FALSE);
//		this.managed.select(sr);//
//	}

	/*
	 * Mar 28, 2013
	 */
	@Override
	public String getEmail() {
		//
		FormViewI def = this.getDefaultForm();
		return def.getFieldData("email");
	}

	/*
	 * Mar 28, 2013
	 */
	@Override
	public String getPassword() {
		FormViewI def = this.getDefaultForm();
		return def.getFieldData("password");
	}

}
