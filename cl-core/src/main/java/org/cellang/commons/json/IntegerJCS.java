/**
 * Jun 23, 2012
 */
package org.cellang.commons.json;

/**
 * @author wu
 * 
 */
public class IntegerJCS extends JsonCodecSupport<Integer, Number> implements
		Codec {

	/** */
	public IntegerJCS(Codecs f) {
		super("i", Integer.class, f);
	}

	/* */
	@Override
	protected Integer decodeWithOutType(Number js) {

		int v = Integer.valueOf(js.toString());
		Integer rt = Integer.valueOf(v);
		return rt;
	}

	/* */
	@Override
	protected Number encodeWithOutType(Integer d) {

		return d;

	}

}
