/**
 *  Jan 30, 2013
 */
package org.cellang.clwt.commons.client.mvc.widget;

import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wuzhen
 * @deprecated remove this class
 */
public interface ViewReferenceI {

	public static interface AwareI {
		public void setViewReference(ViewReferenceI vr);
	}

	public Path getPath();
	
	public WebWidget getManagedWidget();

	public void select(boolean sel);

	public boolean isSelect();
	

}
