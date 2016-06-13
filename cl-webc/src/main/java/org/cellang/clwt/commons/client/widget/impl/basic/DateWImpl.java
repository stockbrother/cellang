/**
 * Jun 29, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.basic;

import org.cellang.clwt.commons.client.mvc.widget.DateWI;
import org.cellang.clwt.commons.client.mvc.widget.WidgetSupport;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.data.DateData;
import org.cellang.clwt.core.client.util.DateUtil;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class DateWImpl extends WidgetSupport implements DateWI {

	/** */
	public DateWImpl(Container c, String name) {
		super(c, name, DOM.createLabel());
	}

	@Override
	public void setDate(DateData date) {

		Element ele = this.getElement();

		DateData sd = date;
		String str = DateUtil.format(sd, false);
		ele.setInnerText(str);//

		ele.setTitle(str);

	}
}
