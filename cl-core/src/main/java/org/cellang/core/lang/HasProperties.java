/**
 * Jun 22, 2012
 */
package org.cellang.core.lang;

import java.util.List;
import java.util.Map;

/**
 * @author wu
 * 
 */
public interface HasProperties<T> {

	public T removeProperty(String key);

	public void setProperty(String key, T value);

	public void setProperty(Map.Entry<String, T> entry);

	public T getProperty(String key);
	
	public T getPropertyWithDefault(String key, T def);

	public T getProperty(String key, boolean force);

	public boolean getPropertyAsBoolean(String key, boolean def);

	public List<String> getPropertyAsCsv(String key);

	public List<String> keyList();

	public void setProperties(Map<String, T> map);

	public void setPropertiesByArray(Object... keyValues);

	public void setProperties(HasProperties<T> pts);

	public Map<String, T> getAsMap();

	public HasProperties<T> convert(String[] from, boolean[] force, String[] to);

	public boolean isContainsSameProperties(Object... kvs);

	public boolean isContainsSameProperties(HasProperties<T> kvs);

	public HasProperties<T> mergeFrom(HasProperties<T> pts);
}
