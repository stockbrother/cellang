/**
 * Jun 23, 2012
 */
package org.cellang.commons.json;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;

/**
 * @author wu
 * 
 */
public class ObjectListJCS extends JsonCodecSupport<List, JSONArray> implements
		Codec {

	/** */
	public ObjectListJCS(Codecs f) {
		super("L", List.class, f);
	}

	/* */
	@Override
	public List decodeWithOutType(JSONArray jv) {
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
	public JSONArray encodeWithOutType(List ud) {
		JSONArray rt = new JSONArray();
		for (int i = 0; i < ud.size(); i++) {
			Object da = ud.get(i);
			Codec c = this.factory.getCodec(da.getClass());

			JSONArray value = (JSONArray) c.encode(da);
			rt.add(value);
		}
		return rt;

	}

}
