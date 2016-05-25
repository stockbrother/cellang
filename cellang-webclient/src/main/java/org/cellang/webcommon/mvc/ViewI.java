/**
 * Jun 12, 2012
 */
package org.cellang.webcommon.mvc;

import org.cellang.webcore.client.data.ErrorInfosData;
import org.cellang.webcore.client.lang.Path;
import org.cellang.webcore.client.widget.WebWidget;

/**
 * @author wuzhen
 * 
 */
public interface ViewI extends WebWidget {

	public void clickAction(Path a);
	
	public void addErrorInfo(ErrorInfosData eis);
	
	public void clearErrorInfo();

}
