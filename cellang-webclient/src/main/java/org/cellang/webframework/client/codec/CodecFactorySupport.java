/**
 * Jun 23, 2012
 */
package org.cellang.webframework.client.codec;

import java.util.HashMap;
import java.util.Map;

import org.cellang.webframework.client.CodecI;
import org.cellang.webframework.client.WebException;

/**
 * @author wu
 * 
 */
public class CodecFactorySupport implements CodecI.FactoryI {

	private Map<String, CodecI> jcMap = new HashMap<String, CodecI>();

	private Map<Class, String> classMap = new HashMap<Class, String>();

	public CodecFactorySupport() {

	}

	protected void add(CodecI cd) {
		String tc = cd.getTypeCode();
		Class cls = cd.getDataClass();
		String oldx = this.classMap.put(cls, tc);
		if (oldx != null) {
			throw new WebException("duplicated:" + cls + ",oldx:" + oldx);
		}
		CodecI old = this.jcMap.put(tc, cd);
		if (old != null) {
			throw new WebException("duplicated:" + tc);
		}

	}

	/* */
	@Override
	public <T> CodecI<T> getCodec(Class<T> dataCls) {
		String tc = this.classMap.get(dataCls);
		if (tc == null) {
			throw new WebException("no codec found for data class:" + dataCls);
		}
		return this.getCodec(tc);

	}

	/* */
	@Override
	public <T> CodecI<T> getCodec(String type) {

		CodecI rt = this.jcMap.get(type);
		if (rt == null) {
			throw new WebException("no codec found for type code:" + type + ",all:" + this.jcMap.keySet());
		}
		return rt;
	}

}
