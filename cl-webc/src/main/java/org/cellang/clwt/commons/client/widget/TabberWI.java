/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package org.cellang.clwt.commons.client.widget;

import java.util.List;

import org.cellang.clwt.commons.client.mvc.CompositeI;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wu
 * 
 */
public interface TabberWI extends CompositeI {

	public static final String PK_IS_VERTICAL = "isVertical";
	
	public static final String PK_IS_CLOSABLE = "isClosable";
	
	public static final String PK_IS_REVERSE = "isReverse";

	public TabWI getSelected(boolean force);
	
	public List<TabWI> getTabList() ;

	public TabWI addTab(Path name);

	public TabWI addTab(Path name, WebWidget content);

	public TabWI addTab(Path name, WebWidget content, boolean sel);

	public TabWI getTab(Path name, boolean force);

	public boolean remove(Path path);
	
	public void removeAll();
	
}
