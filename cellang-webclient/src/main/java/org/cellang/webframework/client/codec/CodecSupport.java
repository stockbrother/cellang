/**
 * Jun 23, 2012
 */
package org.cellang.webframework.client.codec;

import org.cellang.webframework.client.CodecI;

/**
 * @author wu
 * 
 */
public abstract class CodecSupport<T, SER> implements CodecI<T> {
	protected CodecI.FactoryI factory;

	protected String typeCode;
	protected Class<T> dataClass;

	public CodecSupport(String tc, Class<T> dc, CodecI.FactoryI f) {
		this.factory = f;
		this.typeCode = tc;
		this.dataClass = dc;
	}

	/* */
	@Override
	public T decode(Object s) {
		SER ser = (SER) s;
		return this.decodeInternal(ser);

	}

	/* */
	@Override
	public SER encode(T ud) {

		return this.encodeInternal((T) ud);

	}

	protected abstract T decodeInternal(SER js);

	protected abstract SER encodeInternal(T d);

	@Override
	public String getTypeCode() {
		return typeCode;
	}

	@Override
	public Class<T> getDataClass() {
		return dataClass;
	}
}
