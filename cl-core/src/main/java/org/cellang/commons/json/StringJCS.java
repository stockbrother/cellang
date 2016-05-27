/**
 * Jun 23, 2012
 */
package org.cellang.commons.json;

/**
 * @author wu
 * 
 */
public class StringJCS extends JsonCodecSupport<String, String> implements
		Codec {

	/** */
	public StringJCS(Codecs f) {
		super("s", String.class, f);

	}

	/* */
	@Override
	protected String decodeWithOutType(String jv) {
		return jv;
	}

	/* */
	@Override
	protected String encodeWithOutType(String d) {

		return d;

	}

}
