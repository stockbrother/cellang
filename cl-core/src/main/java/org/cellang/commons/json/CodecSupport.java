/**
 * Jun 23, 2012
 */
package org.cellang.commons.json;

/**
 * @author wu
 * 
 */
public abstract class CodecSupport<T, SER> implements Codec {
	protected Codecs factory;

	protected String typeCode;

	protected Class<?> dataClass;

	public CodecSupport(String tc, Class<?> dc, Codecs f) {
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

	/*
	
	 */
	@Override
	public Object encode(Object ud) {

		return this.encodeInternal((T) ud);
	}

	protected abstract T decodeInternal(SER js);

	protected abstract SER encodeInternal(T d);

	@Override
	public String getTypeCode() {
		return typeCode;
	}

	@Override
	public Class<?> getDataClass() {
		return dataClass;
	}

}
