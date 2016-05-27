/**
 * Jun 23, 2012
 */
package org.cellang.commons.json;

/**
 * @author wu
 * 
 */
public interface Codec {

	public String getTypeCode();

	public Class<?> getDataClass();

	public Object decode(Object ser);

	public Object encode(Object ud);

}
