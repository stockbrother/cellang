/**
 *  Nov 28, 2012
 */
package org.cellang.elasticnode.support;

import org.cellang.elasticnode.NodeService;
import org.cellang.elasticnode.result.BooleanResultI;

/**
 * @author wuzhen
 * 
 */
public class BooleanResult extends ResultSupport<BooleanResultI, Boolean> implements BooleanResultI {

	/**
	 * @param ds
	 */
	public BooleanResult(NodeService ds) {
		super(ds);
	}

}
