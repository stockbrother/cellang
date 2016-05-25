/**
 * Jun 23, 2012
 */
package org.cellang.webcore.client.codec;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class ObjectListJCC extends JsonCodecCSupport<List> implements Codec<List> {

	/** */
	public ObjectListJCC(CodecFactory f) {
		super("L", List.class, f);
	}

	/* */
	@Override
	public List decodeWithOutType(JSONValue jv) {
		JSONArray jo = (JSONArray) jv;
		List rt = new ArrayList();
		for (int i = 0; i < jo.size(); i++) {
			JSONArray jvX = (JSONArray) jo.get(i);// must be array for any
													// data
			String type = this.getType(jvX);
			Codec c = this.factory.getCodec(type);

			Object value = c.decode(jvX);

			rt.add(value);

		}

		return rt;

	}

	/* */
	@Override
	public JSONValue encodeWithOutType(List ud) {

		JSONArray rt = new JSONArray();

		for (int i = 0; i < ud.size(); i++) {
			Object da = ud.get(i);
			Codec c = this.factory.getCodec(da.getClass());

			JSONArray value = (JSONArray) c.encode(da);
			rt.set(i, value);
		}
		return rt;

	}

}
