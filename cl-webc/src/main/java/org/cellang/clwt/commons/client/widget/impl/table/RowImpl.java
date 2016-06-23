/**
 * Jul 1, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.table;

import org.cellang.clwt.commons.client.widget.TableI;
import org.cellang.clwt.commons.client.widget.TableI.CellI;
import org.cellang.clwt.commons.client.widget.impl.table.support.TableHelper;
import org.cellang.clwt.core.client.Container;

import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class RowImpl extends TableHelper implements TableI.RowI {

	protected BodyImpl body;

	/** */
	public RowImpl(Container c, BodyImpl t) {
		super(c,DOM.createTR(), t.getTable());
		this.body = t;
	}

	/* */
	@Override
	public CellI createCell() {
		CellI rt = new CellImpl(this.container, this);
		this.appendElement(rt);//
		return rt;

	}

}
