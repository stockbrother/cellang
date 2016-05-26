/**
 * Jul 14, 2012
 */
package org.cellang.clwt.core.client.lang;

import java.util.List;
import java.util.Map;

/**
 * @author wu
 * 
 */
public interface HasProperties<T> {

	public List<String> keyList();

	public void setProperties(HasProperties<T> pts);

	public T getProperty(String pname);

	public void setProperty(String key, T value);

	public T getProperty(String key, boolean force);

	public T getProperty(String key, T def);

	public Map<String, T> getAsMap();
	
	public void setProperties(Map<String,T> map);

}
