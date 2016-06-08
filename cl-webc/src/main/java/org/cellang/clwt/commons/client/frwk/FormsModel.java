/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 12, 2012
 */
package org.cellang.clwt.commons.client.frwk;

import org.cellang.clwt.commons.client.mvc.ModelSupport;
import org.cellang.clwt.core.client.UiException;

/**
 * @author wu
 * 
 */
public class FormsModel extends ModelSupport {

	public static final String DEFAULT_FORM = "default";
	
	private FormModel currentForm;

	/**
	 * @param name
	 */
	public FormsModel(String name) {
		super(name);
		this.addForm(DEFAULT_FORM);
		this.setCurrentFormName(DEFAULT_FORM);//
	}

	public FormModel getForm(String name, boolean force) {
		return this.getChild(FormModel.class, name, force);
	}

	public FormModel getDefaultForm() {
		return this.getForm(DEFAULT_FORM, true);
	}

	public void setCurrentFormName(String name) {
		this.currentForm = this.getForm(name, true);
	}

	public FormModel getCurrentForm() {
		return this.currentForm;
	}

	public FormModel addForm(String name) {
		if (getForm(name, false) != null) {
			throw new UiException("existed form:" + name + ",in:" + this);
		}
		FormModel rt = new FormModel(name);
		rt.parent(this);//
		// this will cause the view updated into the right screen.
		return rt;
	}

}
