/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable;

import org.cellang.core.lang.HasProperties;

/**
 * @author wu
 * 
 */
public interface TableOperation<O extends TableOperation<O, T>, T extends ExecuteResult<T, ?>> {

	public void addBeforeInterceptor(Interceptor<O> itr);

	public O beforeInterceptor(Interceptor<O> itr);
	
	public O prepare();

	public O parameter(String key, Object value);

	public O parameters(HasProperties<Object> pts);

	public Object getParameter(String key);

	public Object getParameter(String key, boolean force);

	public <X> X getParameter(Class<X> cls, String key, X def);

	public O execute();

	public T getResult();

	public <X> X cast();

}
