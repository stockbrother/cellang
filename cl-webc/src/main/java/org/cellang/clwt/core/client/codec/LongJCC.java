/**
 * Jun 23, 2012
 */
package org.cellang.clwt.core.client.codec;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class LongJCC extends JsonCodecCSupport<Long> implements Codec<Long> {

	/** */
	public LongJCC(CodecFactory f) {
		super("l", Long.class, f);
	}

	/* */
	@Override
	protected Long decodeWithOutType(JSONValue js) {
		long v = Long.valueOf(js.toString());
		Long rt = Long.valueOf(v);
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(Long d) {

		return new JSONNumber(d);

	}

}
