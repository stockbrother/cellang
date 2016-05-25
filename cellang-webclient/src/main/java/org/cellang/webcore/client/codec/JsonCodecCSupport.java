/**
 * Jun 23, 2012
 */
package org.cellang.webcore.client.codec;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 *         <p>
 *         Cannot encode a null value,since we not known it's type.
 *         <p>
 *         So, cannot decode a none-type json,such as {a:null},must be:
 *         {a:["_X",null]}
 *         <p>
 *         {a:null} is invalid.
 */
public abstract class JsonCodecCSupport<T> extends CodecSupport<T, JSONArray> {

	/** */
	public JsonCodecCSupport(String tc, Class<T> cls, CodecFactory f) {
		super(tc, cls, f);
	}

	/* */
	@Override
	protected T decodeInternal(JSONArray js) {

		JSONValue jv = js.get(1);

		return this.decodeWithOutType(jv);

	}

	protected String getType(JSONArray js) {
		JSONString jt = (JSONString) js.get(0);
		String rt = jt.stringValue();
		return rt;
	}

	/* */
	@Override
	protected JSONArray encodeInternal(T d) {

		JSONArray rt = new JSONArray();
		rt.set(0, new JSONString(this.typeCode));
		JSONValue jv = this.encodeWithOutType(d);
		rt.set(1, jv);
		return rt;

	}

	protected abstract T decodeWithOutType(JSONValue jv);

	protected abstract JSONValue encodeWithOutType(T t);

}
