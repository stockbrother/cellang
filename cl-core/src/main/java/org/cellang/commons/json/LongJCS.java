/**
 * Jun 23, 2012
 */
package org.cellang.commons.json;

/**
 * @author wu
 * 
 */
public class LongJCS extends JsonCodecSupport<Long, Number> implements Codec {

	/** */
	public LongJCS(Codecs f) {
		super("l", Long.class, f);
	}

	/* */
	@Override
	protected Long decodeWithOutType(Number js) {

		long v = Long.valueOf(js.toString());
		Long rt = Long.valueOf(v);
		return rt;
	}

	/* */
	@Override
	protected Number encodeWithOutType(Long d) {

		return d;

	}

}
