/**
 * Jun 23, 2012
 */
package org.cellang.clwt.core.client.codec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.clwt.core.client.UiException;

/**
 * @author wu
 * 
 */
public class AbstractCodecFactory implements CodecFactory {

	private Map<String, Codec> jcMap = new HashMap<String, Codec>();

	private Map<Class, String> classMap = new HashMap<Class, String>();

	public AbstractCodecFactory() {

	}

	protected void add(Codec cd) {
		String tc = cd.getTypeCode();
		Class cls = cd.getDataClass();
		String oldx = this.classMap.put(cls, tc);
		if (oldx != null) {
			throw new UiException("duplicated:" + cls + ",oldx:" + oldx);
		}
		if(cls.equals(List.class)){
			this.classMap.put(ArrayList.class, tc);
		}
		Codec old = this.jcMap.put(tc, cd);
		if (old != null) {
			throw new UiException("duplicated:" + tc);
		}

	}

	/* */
	@Override
	public <T> Codec<T> getCodec(Class<T> dataCls) {

		String tc = this.classMap.get(dataCls);
		if (tc == null) {
			throw new UiException(
					"no codec found for data class:" + dataCls + ",all supported cls:" + this.classMap.keySet());
		}
		return this.getCodec(tc);

	}

	/* */
	@Override
	public <T> Codec<T> getCodec(String type) {

		Codec rt = this.jcMap.get(type);
		if (rt == null) {
			throw new UiException("no codec found for type code:" + type + ",all:" + this.jcMap.keySet());
		}
		return rt;
	}

}
