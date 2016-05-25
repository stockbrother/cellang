/**
 * 
 */
package org.cellang.webcommon.mvc.widget;

import org.cellang.webcore.client.data.ErrorInfosData;
import org.cellang.webcore.client.widget.WebWidget;

/**
 * @author wuzhen
 * 
 */
public interface ErrorInfosWidgetI extends WebWidget {

	public void addErrorInfos(ErrorInfosData errorInfos);

	public void clear();

}
