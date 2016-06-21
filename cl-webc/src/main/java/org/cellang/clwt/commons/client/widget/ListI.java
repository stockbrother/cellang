/**
 * Jun 13, 2012
 */
package org.cellang.clwt.commons.client.widget;

import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wu
 * 
 */
public interface ListI extends WebWidget {
	
	public static final String PK_IS_VERTICAL = "isVertical";
	
	public static final String PK_COMPARATOR = "comparator";
	
	public static final String PK_LIST_ITEM_CLASSNAME = "itemClassName"; 

	public int getSize();
}
