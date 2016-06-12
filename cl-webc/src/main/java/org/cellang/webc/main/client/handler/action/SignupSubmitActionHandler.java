package org.cellang.webc.main.client.handler.action;

import org.cellang.clwt.commons.client.FormDataAP;
import org.cellang.clwt.commons.client.mvc.ActionEvent;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.webc.main.client.SignupViewI;

/**
 * 
 * @author wuzhen
 *         <p>
 *         Submit the login email and password
 */
public class SignupSubmitActionHandler extends FormDataAP {

	/**
	 * @param c
	 */
	public SignupSubmitActionHandler(Container c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(ActionEvent ae) {
		//

//		SignupViewI lm = this.findView(SignupViewI.class, true);
//		lm.clearErrorInfo();//
//
//		// this submit

		MsgWrapper req = this.newRequest(Path.valueOf("/signup/submit"));
		this.processFormData(ae, req);
		this.sendMessage(req);
		
	}

}
