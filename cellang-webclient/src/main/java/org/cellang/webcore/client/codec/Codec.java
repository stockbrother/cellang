/**
 * Jun 23, 2012
 */
package org.cellang.webcore.client.codec;

/**
 * @author wu
 * 
 */
public interface Codec<T> {

	public String getTypeCode();

	public Class<T> getDataClass();

	public T decode(Object ser);

	public Object encode(T ud);

}
