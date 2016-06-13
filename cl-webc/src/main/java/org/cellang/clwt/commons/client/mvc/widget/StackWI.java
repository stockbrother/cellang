/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package org.cellang.clwt.commons.client.mvc.widget;

import org.cellang.clwt.commons.client.mvc.CompositeI;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wu
 * 
 */
public interface StackWI extends CompositeI {

	public StackItemI getDefaultItem(boolean force);

	public StackItemI getSelected(boolean force);

	public StackItemI insert(Path path, WebWidget child, boolean select);

	public StackItemI getByPath(Path p, boolean force);

	public void remove(Path path);

	public int getSize();

	public void updateSelect(StackItemI im);

}
