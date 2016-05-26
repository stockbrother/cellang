/**
 * Jun 12, 2012
 */
package org.cellang.clwt.commons.client.mvc;

import org.cellang.clwt.core.client.data.ErrorInfosData;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wuzhen
 * 
 */
public interface ViewI extends WebWidget {

	public void clickAction(Path a);
	
	public void addErrorInfo(ErrorInfosData eis);
	
	public void clearErrorInfo();

}
