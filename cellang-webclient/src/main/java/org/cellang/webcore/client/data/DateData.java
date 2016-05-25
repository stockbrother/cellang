/**
 * Jul 20, 2012
 */
package org.cellang.webcore.client.data;

import java.util.Date;

import org.cellang.webcore.client.util.DateUtil;

/**
 * @author wu
 * 
 */
public class DateData extends BasicData<Long> {

	/** */
	protected DateData(Long t) {
		super(t);
	}

	public static DateData valueOf(Date date) {
		return date == null ? null : valueOf(date.getTime());
	}

	public static DateData valueOf(Long v) {

		return v == null ? null : new DateData(v);
	}

	public String getFormated() {
		return DateUtil.format(this);
	}
}
