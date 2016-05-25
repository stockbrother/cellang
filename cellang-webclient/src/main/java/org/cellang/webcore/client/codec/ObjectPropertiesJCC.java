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
public class ObjectPropertiesJCC extends
		JsonCodecCSupport<ObjectPropertiesData> implements Codec<ObjectPropertiesData> {

	/** */
	public ObjectPropertiesJCC(CodecFactory f) {
		super("O", ObjectPropertiesData.class, f);
	}

	/* */
	@Override
	public ObjectPropertiesData decodeWithOutType(JSONValue jv) {
		JSONObject jo = (JSONObject) jv;
		ObjectPropertiesData rt = new ObjectPropertiesData();
		for (String key : jo.keySet()) {
			JSONValue jvi = jo.get(key);
			Object value = null;
			if (jvi instanceof JSONNull) {
				// value is null,and not know the type.
			} else {
				JSONArray jvX = (JSONArray) jo.get(key);// must be array for any
														// data
				String type = this.getType(jvX);
				Codec c = this.factory.getCodec(type);

				value = c.decode(jvX);
			}
			rt.setProperty(key, value);
		}

		return rt;

	}

	/* */
	@Override
	public JSONValue encodeWithOutType(ObjectPropertiesData ud) {
		JSONObject rt = new JSONObject();
		for (String key : ud.keyList()) {
			Object da = ud.getProperty(key);
			JSONValue value = null;
			if (da == null) {
				// what to do?
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
