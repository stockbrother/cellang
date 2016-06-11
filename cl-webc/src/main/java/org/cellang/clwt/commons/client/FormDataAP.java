/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package org.cellang.clwt.commons.client;

import org.cellang.clwt.commons.client.frwk.FormsViewI;
import org.cellang.clwt.commons.client.mvc.ActionEvent;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.message.MsgWrapper;

/**
 * @author wu
 * 
 */
public abstract class FormDataAP extends ActionHandlerSupport {

	private String form;

	public FormDataAP(Container c) {
		this(c, null);
	}

	public FormDataAP(Container c, String form) {
		super(c);
		this.form = form;
	}

	protected void processFormData(ActionEvent ae, MsgWrapper req) {

		FormsViewI fv = (FormsViewI) ae.getProperty(UiCommonsConstants.AK_FORMS_VIEW);
		ObjectPropertiesData dt = (ObjectPropertiesData) this.getFormData(fv);

		req.setPayloads(dt);
	}

	protected ObjectPropertiesData getFormData(FormsViewI sv) {
		if (this.form == null) {
			return sv.getDefaultForm().getData();
		} else {
			return sv.getForm(this.form).getData();
		}
	}

}
