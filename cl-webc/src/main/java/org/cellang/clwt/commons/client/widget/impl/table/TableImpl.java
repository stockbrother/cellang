/**
 * Jul 1, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.table;

import org.cellang.clwt.commons.client.mvc.widget.LayoutSupport;
import org.cellang.clwt.commons.client.mvc.widget.TableI;
import org.cellang.clwt.core.client.Container;

import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class TableImpl extends LayoutSupport implements TableI {

	protected TableI.HeadersI headers;

	protected TableI.BodyI body;

	/** */
	public TableImpl(Container c, String name) {
		super(c,name, DOM.createTable());
		this.element.setAttribute("cellspacing", "0");
		
		this.element.setAttribute("cellspading", "0");
		
		this.headers = new HeadersImpl(this.container,this);
		this.child(this.headers);
		this.body = new BodyImpl(this.container,this);
		this.child(this.body);//
	}


	/* */
	@Override
	public HeadersI getHeaders() {

		return this.headers;

	}

	/* */
	@Override
	public TableI.BodyI getBody() {

		return this.body;

	}

}
