/**
 * Jun 23, 2012
 */
package org.cellang.commons.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.core.lang.util.ClassWrapper;

/**
 * @author wu
 * 
 */
public class AbstractCodecs implements Codecs {

	private Map<String, Codec> jcMap = new HashMap<String, Codec>();

	private Map<Class, String> classMap = new HashMap<Class, String>();

	public AbstractCodecs() {

	}

	protected void add(Codec cd) {
		String tc = cd.getTypeCode();
		Class cls = cd.getDataClass();
		String oldx = this.classMap.put(cls, tc);
		if (oldx != null) {
			throw new RuntimeException("duplicated:" + cls + ",oldx:" + oldx);
		}
		Codec old = this.jcMap.put(tc, cd);
		if (old != null) {
			throw new RuntimeException("duplicated:" + tc);
		}

	}

	/* */
	@Override
	public Codec getCodec(Class<?> dataCls) {
		return this.getCodec(dataCls, false);
	}

	@Override
	public Codec getCodec(Class<?> dataCls, boolean force) {
		String tc = this.getTypeCode(dataCls, true);
		Codec rt = this.getCodec(tc);
		if (rt == null && force) {
			throw new RuntimeException("no codec for type:" + dataCls);
		}
		return rt;

	}

	protected String getTypeCode(Class<?> dataCls, boolean force) {

		String tc = this.classMap.get(dataCls);
		if (tc != null) {
			return tc;
		}

		List<Class> superL = new ClassWrapper(dataCls).getSuperTypeList();//
		List<String> codeL = new ArrayList<String>();
		for (Class sc : superL) {
			String stc = this.classMap.get(sc);
			if (stc != null) {
				codeL.add(stc);
			}
		}

		if (codeL.isEmpty()) {
			if (force) {
				throw new RuntimeException("no type code for:" + dataCls);
			}
			return null;
		} else if (codeL.size() == 1) {
			return codeL.get(0);
		} else {
			throw new RuntimeException("too much type code:" + codeL + ", for class:" + dataCls);
		}

	}

	/* */
	@Override
	public Codec getCodec(String type) {

		Codec rt = this.jcMap.get(type);
		if (rt == null) {
			throw new RuntimeException("no codec found for type code:" + type + ",all:" + this.jcMap.keySet());
		}
		return rt;
	}

}
