/**
 * Jun 23, 2012
 */
package org.cellang.commons.json;

import org.json.simple.JSONArray;

/**
 * @author wu
 *         <p>
 *         org.json.simple.JSONObject, org.json.simple.JSONArray,
 *         java.lang.String, java.lang.Number, java.lang.Boolean, null
 */
public abstract class JsonCodecSupport<T, JV> extends
		CodecSupport<T, JSONArray> {

	/** */
	public JsonCodecSupport(String tc, Class<T> cls, Codecs f) {
		super(tc, cls, f);
	}

	/* */
	@Override
	protected T decodeInternal(JSONArray js) {

		JV jv = (JV) js.get(1);

		return this.decodeWithOutType(jv);

	}

	protected String getType(JSONArray js) {
		String jt = (String) js.get(0);
		String rt = jt;
		return rt;
	}

	/* */
	@Override
	protected JSONArray encodeInternal(T d) {

		JSONArray rt = new JSONArray();

		rt.add(this.typeCode);
		JV jv = this.encodeWithOutType(d);
		rt.add(jv);
		return rt;

	}

	protected abstract T decodeWithOutType(JV jv);

	protected abstract JV encodeWithOutType(T t);

}
