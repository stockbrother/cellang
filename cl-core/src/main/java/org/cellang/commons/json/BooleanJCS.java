/**
 * Jun 23, 2012
 */
package org.cellang.commons.json;

/**
 * @author wu
 * 
 */
public class BooleanJCS extends JsonCodecSupport<Boolean, Boolean> implements
		Codec {

	/** */
	public BooleanJCS(Codecs f) {
		super("b", Boolean.class, f);
	}

	/* */
	@Override
	protected Boolean decodeWithOutType(Boolean js) {

		return js;
	}

	/* */
	@Override
	protected Boolean encodeWithOutType(Boolean d) {

		return d;

	}

}
