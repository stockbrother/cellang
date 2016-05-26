/**
 * 
 */
package org.cellang.clwt.commons.client.mvc.widget;

import org.cellang.clwt.core.client.data.ErrorInfosData;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wuzhen
 * 
 */
public interface ErrorInfosWidgetI extends WebWidget {

	public void addErrorInfos(ErrorInfosData errorInfos);

	public void clear();

}
