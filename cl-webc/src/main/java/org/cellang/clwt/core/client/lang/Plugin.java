/**
 * Jul 1, 2012
 */
package org.cellang.clwt.core.client.lang;

import org.cellang.clwt.core.client.Container;

/**
 * @author wu This interface for module/plugin provider to write the service/code.
 */
public interface Plugin {
	public void active(Container c);

}
