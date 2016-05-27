/**
 * Jun 23, 2012
 */
package org.cellang.commons.json;

import java.util.List;

import org.cellang.core.lang.HasProperties;
import org.cellang.core.lang.MapProperties;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author wu
 * 
 */
public abstract class PropertiesJCSSupport<T> extends
		JsonCodecSupport<T, JSONObject> implements Codec {

	/** */
	public PropertiesJCSSupport(String typeCode, Class<T> cls, Codecs f) {
		super(typeCode, cls, f);
	}

	/* */
	@Override
	public T decodeWithOutType(JSONObject jv) {
		JSONObject jo = (JSONObject) jv;
		HasProperties<Object> rt = new MapProperties<Object>();
		for (Object ks : jo.keySet()) {
			String key = (String) ks;
			JSONArray jvX = (JSONArray) jo.get(key);// must be array for any

			Object value = null;
			// data
			if (jvX != null) {

				String type = this.getType(jvX);
				Codec c = this.factory.getCodec(type);

				value = c.decode(jvX);
			}
			rt.setProperty(key, value);
		}

		return this.convert(rt);

	}

	protected abstract T convert(HasProperties<Object> pts);

	protected abstract HasProperties<Object> convert(T t);

	/* */
	@Override
	public JSONObject encodeWithOutType(T ud) {
		HasProperties<Object> pts = this.convert(ud);
		JSONObject rt = new JSONObject();
		List<String> kL = pts.keyList();

		for (String key : kL) {
			Object da = pts.getProperty(key);// TODO
			JSONArray value = null;
			if (da != null) {
				Codec c = this.factory.getCodec(da.getClass());

				value = (JSONArray) c.encode(da);
			}
			rt.put(key, value);

		}
		return rt;

	}

}
