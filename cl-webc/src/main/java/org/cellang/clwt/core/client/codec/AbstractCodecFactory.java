/**
 * Jun 23, 2012
 */
package org.cellang.clwt.core.client.codec;

import java.util.HashMap;
import java.util.Map;

import org.cellang.clwt.core.client.WebException;

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
			throw new WebException("duplicated:" + cls + ",oldx:" + oldx);
		}
		Codec old = this.jcMap.put(tc, cd);
		if (old != null) {
			throw new WebException("duplicated:" + tc);
		}

	}

	/* */
	@Override
	public <T> Codec<T> getCodec(Class<T> dataCls) {
		String tc = this.classMap.get(dataCls);
		if (tc == null) {
			throw new WebException("no codec found for data class:" + dataCls);
		}
		return this.getCodec(tc);

	}

	/* */
	@Override
	public <T> Codec<T> getCodec(String type) {

		Codec rt = this.jcMap.get(type);
		if (rt == null) {
			throw new WebException("no codec found for type code:" + type + ",all:" + this.jcMap.keySet());
		}
		return rt;
	}

}
