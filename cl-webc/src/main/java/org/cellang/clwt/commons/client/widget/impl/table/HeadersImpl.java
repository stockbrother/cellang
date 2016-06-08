/**
 * Jul 1, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.clwt.commons.client.mvc.widget.TableI;
import org.cellang.clwt.commons.client.widget.impl.table.support.TableHelper;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;

import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class HeadersImpl extends TableHelper implements TableI.HeadersI {

	protected Map<String, TableI.HeaderI> headerMap = new HashMap<String, TableI.HeaderI>();

	/** */
	public HeadersImpl(Container c, TableImpl t) {
		super(c, DOM.createTHead(), t);
	}

	public TableI.HeaderI getHeader(String name) {
		return this.headerMap.get(name);
	}

	/* */
	@Override
	public TableI.HeaderI createHeader(String name) {
		TableI.HeaderI old = this.getHeader(name);
		if (old != null) {
			throw new UiException("duplicated header:" + name);
		}
		TableI.HeaderI rt = new HeaderImpl(this.container, this.table, name);
		this.headerMap.put(name, rt);
		return rt;

	}

	/* */
	@Override
	public List<TableI.HeaderI> createHeader(String[] names) {
		List<TableI.HeaderI> rt = new ArrayList<TableI.HeaderI>();
		for (String name : names) {
			rt.add(this.createHeader(name));
		}
		return rt;

	}

}
