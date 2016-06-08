/**
 *  Feb 1, 2013
 */
package org.cellang.clwt.commons.client.frwk;

import org.cellang.clwt.commons.client.mvc.ViewI;

/**
 * @author wuzhen
 * 
 */
public interface FormsViewI extends ViewI {

	public FormViewI addForm(String name);

	public FormViewI getForm(String name);

	public FormViewI getDefaultForm();
}
