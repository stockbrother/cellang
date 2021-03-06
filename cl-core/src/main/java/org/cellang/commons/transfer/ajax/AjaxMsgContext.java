/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package org.cellang.commons.transfer.ajax;

import org.cellang.commons.lang.NameSpace;

/**
 * @author wu
 * 
 */
public class AjaxMsgContext {
	public AjaxMsg am;
	public AjaxRequestContext arc;
	public int sequenceInRequest;
	public int totalMessageInRequest;

	public AjaxMsgContext(int sir, int tir, AjaxMsg am, AjaxRequestContext arc) {
		this.arc = arc;
		this.am = am;
		this.sequenceInRequest = sir;
		this.totalMessageInRequest = tir;
	}

	/**
	 * May 8, 2013
	 */
	public void writeFailure() {
		NameSpace path = this.am.getPath();
		path = path.getSubPath("failure");
		AjaxMsg am = new AjaxMsg(path);
		this.arc.write(am);
	}


}
