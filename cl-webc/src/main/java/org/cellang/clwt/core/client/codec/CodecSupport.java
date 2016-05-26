/**
 * Jun 23, 2012
 */
package org.cellang.clwt.core.client.codec;

/**
 * @author wu
 * 
 */
public abstract class CodecSupport<T, SER> implements Codec<T> {
	protected CodecFactory factory;

	protected String typeCode;
	protected Class<T> dataClass;

	public CodecSupport(String tc, Class<T> dc, CodecFactory f) {
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
