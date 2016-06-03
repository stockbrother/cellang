/**
 *  Nov 28, 2012
 */
package org.cellang.elastictable.result;

import org.cellang.elastictable.ExecuteResult;

public interface NodeCreateResultI extends ExecuteResult<NodeCreateResultI,String> {

	public String getUid(boolean force);

}