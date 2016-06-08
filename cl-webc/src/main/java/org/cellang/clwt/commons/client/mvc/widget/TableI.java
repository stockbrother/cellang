/**
 * Jul 1, 2012
 */
package org.cellang.clwt.commons.client.mvc.widget;

import java.util.List;

import org.cellang.clwt.commons.client.mvc.CompositeI;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wu
 * 
 */
public interface TableI extends CompositeI {

	public static interface HeaderI extends CompositeI {
		public String getName();

		public HeaderI child(WebWidget I);
	}

	public static interface HeadersI extends CompositeI {

		public HeaderI createHeader(String name);

		public List<HeaderI> createHeader(String[] names);

	}

	public static interface CellI extends CompositeI {
		public CellI colspan(int cs);
		public CellI rowspan(int rs);
		public CellI child(WebWidget w);
	}

	public static interface RowI extends CompositeI {
		public CellI createCell();
	}

	public static interface BodyI extends CompositeI {
		public RowI createRow();
	}

	public HeadersI getHeaders();

	public BodyI getBody();
}
