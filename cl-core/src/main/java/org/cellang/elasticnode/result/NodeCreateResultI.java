/**
 *  Nov 28, 2012
 */
package org.cellang.elasticnode.result;

import org.cellang.elasticnode.ExecuteResult;

public interface NodeCreateResultI extends ExecuteResult<NodeCreateResultI,String> {

	public String getUid(boolean force);

}