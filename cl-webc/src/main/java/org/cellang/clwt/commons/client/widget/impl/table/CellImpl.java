/**
 * Jul 1, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.table;

import org.cellang.clwt.commons.client.widget.TableI;
import org.cellang.clwt.commons.client.widget.TableI.CellI;
import org.cellang.clwt.commons.client.widget.TableI.RowI;
import org.cellang.clwt.commons.client.widget.impl.table.support.TableHelper;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.widget.WebWidget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class CellImpl extends TableHelper implements TableI.CellI {

	protected RowI row;

	/** */
	public CellImpl(Container c, RowImpl r) {
		super(c, DOM.createTD(), r.getTable());
		this.row = r;
	}

	/* */
	@Override
	public CellI child(WebWidget w) {
		super.child(w);
		return this;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.widget.table.TableI.CellI#colspan(int)
	 */
	@Override
	public CellI colspan(int cs) {
		Element ele = this.element;
		setAttribute(ele, "colspan", cs);
		return this;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.widget.table.TableI.CellI#rowspan(int)
	 */
	@Override
	public CellI rowspan(int rs) {
		//this.setAttribuate not support non-string type,and will throw a TypeError.
		Element ele = this.element;
		setAttribute(ele, "rowspan", rs);
		return this;
	}

	public static final native void setAttribute(Element ele, String name, Object value)
	/*-{
		ele.setAttribute(name, value);
	}-*/;

}
