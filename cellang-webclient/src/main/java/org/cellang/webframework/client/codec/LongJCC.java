/**
 * Jun 23, 2012
 */
package org.cellang.webframework.client.codec;

import org.cellang.webframework.client.CodecI;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class LongJCC extends JsonCodecCSupport<Long> implements CodecI<Long> {

	/** */
	public LongJCC(FactoryI f) {
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
