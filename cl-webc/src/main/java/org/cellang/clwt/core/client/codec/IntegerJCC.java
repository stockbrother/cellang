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
public class IntegerJCC extends JsonCodecCSupport<Integer> implements Codec<Integer> {

	/** */
	public IntegerJCC(CodecFactory f) {
		super("i", Integer.class, f);
	}

	/* */
	@Override
	protected Integer decodeWithOutType(JSONValue js) {

		int v = Integer.valueOf(js.toString());
		Integer rt = Integer.valueOf(v);
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(Integer d) {

		return new JSONNumber(d);

	}

}
