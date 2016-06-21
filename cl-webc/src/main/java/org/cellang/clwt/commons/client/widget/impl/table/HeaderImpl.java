/**
 * Jul 1, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.table;

import org.cellang.clwt.commons.client.widget.TableI;
import org.cellang.clwt.commons.client.widget.impl.table.support.TableHelper;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.widget.WebWidget;

import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class HeaderImpl extends TableHelper implements TableI.HeaderI {

	protected String name;

	/** */
	public HeaderImpl(Container c, TableImpl t, String name) {
		super(c, DOM.createTH(), t);
		this.name = name;
	}

	/* */
	@Override
	public String getName() {

		return this.name;

	}

	/* */
	@Override
	public TableI.HeaderI child(WebWidget w) {
		super.child(w);
		return this;

	}

}
