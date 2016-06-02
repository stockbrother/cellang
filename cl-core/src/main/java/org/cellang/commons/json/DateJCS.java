/**
 * Jun 23, 2012
 */
package org.cellang.commons.json;

import java.util.Date;

import org.cellang.core.util.DateUtil;

/**
 * @author wu
 * 
 */
public class DateJCS extends JsonCodecSupport<Date, String> implements Codec {

	/** */
	public DateJCS(Codecs f) {
		super("d", Date.class, f);
	}

	/* */
	@Override
	protected Date decodeWithOutType(String js) {

		return DateUtil.parse(js);
	}

	/* */
	@Override
	protected String encodeWithOutType(Date d) {

		return DateUtil.format(d);

	}

}
