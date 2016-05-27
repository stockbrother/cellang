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
public abstract class ListJCSSupport<T> extends JsonCodecSupport<T, JSONArray>
		implements Codec {

	/** */
	public ListJCSSupport(String typeCode, Class<T> cls, Codecs f) {
		super(typeCode, cls, f);
	}

	/* */
	@Override
	public T decodeWithOutType(JSONArray jv) {
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

		return this.convert(rt);

	}

	protected abstract T convert(List l);

	protected abstract List convert(T t);

	/* */
	@Override
	public JSONArray encodeWithOutType(T t) {
		List ud = this.convert(t);
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
