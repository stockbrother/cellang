/**
 *  Nov 28, 2012
 */
package org.cellang.elastictable;

import org.cellang.core.lang.ErrorInfos;
import org.cellang.core.lang.HasProperties;

public interface ExecuteResult<R extends ExecuteResult<R, T>, T> extends
		HasProperties<Object> {

	public static final String PK_DEFAULT = "_default";

	public static final String PK_ERRORINFOS = "_errorInfos";

	public T get(String key, boolean force);

	public T get(boolean force);

	public void set(T value);

	public ErrorInfos getErrorInfo();

	public boolean hasError();

	public TableService getDataService();

	public R assertNoError();

	public <X> X cast();
}