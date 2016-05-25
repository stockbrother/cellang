/**
 * Jun 23, 2012
 */
package org.cellang.webcore.client.codec;

import org.cellang.webcore.client.data.DateData;
import org.cellang.webcore.client.util.DateUtil;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class DateJCC extends JsonCodecCSupport<DateData> implements Codec<DateData> {

	/** */
	public DateJCC(CodecFactory f) {
		super("d", DateData.class, f);
	}

	/* */
	@Override
	protected DateData decodeWithOutType(JSONValue js) {
		if (js instanceof JSONNull) {
			return null;//
		}
		JSONString jsS = (JSONString) js;
		DateData rt = DateUtil.parse(jsS.stringValue());
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(DateData d) {

		String ds = DateUtil.format(d, false);
		return ds == null ? JSONNull.getInstance() : new JSONString(ds);

	}

}
