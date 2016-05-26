/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package org.cellang.core.commons.session;

import org.cellang.core.lang.HasProperties;

/**
 * @author wu
 * 
 */
public interface Session extends HasProperties<Object> {

	public String getGroup();

	public String getId();

	public long getIdleTimeoutMs();

	public void destroy();

	public long getCreated();

	public long getTouched();

	public void touch();

	public boolean isTimeout();
	
}
