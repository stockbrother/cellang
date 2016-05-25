/**
 * Jun 23, 2012
 */
package org.cellang.webcore.client.codec;

import org.cellang.webcore.client.data.ObjectPropertiesData;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public abstract class PropertiesJCCSupport<T> extends JsonCodecCSupport<T> implements Codec<T> {

	/** */
	public PropertiesJCCSupport(String type, Class<T> cls, CodecFactory f) {
		super(type, cls, f);
	}

	/* */
	@Override
	public T decodeWithOutType(JSONValue jv) {
		JSONObject jo = (JSONObject) jv;
		ObjectPropertiesData rt = new ObjectPropertiesData();
		for (String key : jo.keySet()) {
			JSONValue jsv = (JSONValue) jo.get(key);
			Object value = null;
			if (jsv instanceof JSONNull) {

			} else {
				JSONArray jvX = (JSONArray) jo.get(key);// must be array for any
														// data
				String type = this.getType(jvX);
				Codec c = this.factory.getCodec(type);

				value = c.decode(jvX);
			}
			rt.setProperty(key, value);
		}

		return convert(rt);

	}

	protected abstract T convert(ObjectPropertiesData od);

	protected abstract ObjectPropertiesData convert(T t);

	/* */
	@Override
	public JSONValue encodeWithOutType(T t) {
		ObjectPropertiesData ud = this.convert(t);
		JSONObject rt = new JSONObject();
		for (String key : ud.keyList()) {
			Object da = ud.getProperty(key);
			JSONValue value = null;
			if (da == null) {
				value = JSONNull.getInstance();
			} else {
				Codec c = this.factory.getCodec(da.getClass());

				value = (JSONArray) c.encode(da);
			}
			rt.put(key, value);
		}
		return rt;

	}

}
