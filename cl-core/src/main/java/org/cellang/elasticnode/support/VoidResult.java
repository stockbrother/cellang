/**
 *  Nov 28, 2012
 */
package org.cellang.elasticnode.support;

import org.cellang.elasticnode.NodeService;
import org.cellang.elasticnode.result.VoidResultI;

/**
 * @author wuzhen
 * 
 */
public class VoidResult extends ResultSupport<VoidResultI, Object> implements
		VoidResultI {

	/**
	 * @param ds
	 */
	public VoidResult(NodeService ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

}
