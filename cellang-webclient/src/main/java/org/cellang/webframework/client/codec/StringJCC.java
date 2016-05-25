/**
 * Jun 23, 2012
 */
package org.cellang.webframework.client.codec;

import org.cellang.webframework.client.CodecI;

import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class StringJCC extends JsonCodecCSupport<String> implements CodecI<String> {

	/** */
	public StringJCC(FactoryI f) {
		super("s", String.class, f);

	}

	/* */
	@Override
	protected String decodeWithOutType(JSONValue jv) {
		JSONString js = (JSONString) jv;
		String rt = (js.stringValue());
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(String d) {

		return new JSONString(d);

	}

}
