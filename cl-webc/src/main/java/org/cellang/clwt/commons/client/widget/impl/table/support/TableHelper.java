/**
 * Jul 1, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.table.support;

import org.cellang.clwt.commons.client.mvc.widget.LayoutSupport;
import org.cellang.clwt.commons.client.widget.impl.table.TableImpl;
import org.cellang.clwt.core.client.Container;

import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class TableHelper extends LayoutSupport {

	protected TableImpl table;

	public TableHelper(Container c, Element ele, TableImpl t) {
		super(c, ele);
		this.table = t;
	}

	/**
	 * @return the table
	 */
	public TableImpl getTable() {
		return table;
	}
}
