/**
 * Jul 1, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.table;

import org.cellang.clwt.commons.client.widget.TableI;
import org.cellang.clwt.commons.client.widget.TableI.RowI;
import org.cellang.clwt.commons.client.widget.impl.table.support.TableHelper;
import org.cellang.clwt.core.client.Container;

import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class BodyImpl extends TableHelper implements TableI.BodyI {

	/** */
	public BodyImpl(Container c, TableImpl t) {
		super(c, DOM.createTBody(), t);

	}

	/* */
	@Override
	public RowI createRow() {
		RowI rt = new RowImpl(this.container, this);
		this.appendElement(rt);//
		return rt;

	}

}
