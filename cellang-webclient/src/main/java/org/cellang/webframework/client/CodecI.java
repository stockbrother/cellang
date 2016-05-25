/**
 * Jun 23, 2012
 */
package org.cellang.webframework.client;

/**
 * @author wu
 * 
 */
public interface CodecI<T> {

	public static interface FactoryI {
		public <T> CodecI<T> getCodec(Class<T> dataCls);

		public <T> CodecI<T> getCodec(String type);

	}

	public String getTypeCode();

	public Class<T> getDataClass();

	public T decode(Object ser);

	public Object encode(T ud);

}
