/**
 *  Nov 28, 2012
 */
package org.cellang.elasticnode.support;

import org.cellang.elasticnode.NodeService;
import org.cellang.elasticnode.result.LongResultI;

/**
 * @author wuzhen
 * 
 */
public class LongResult extends ResultSupport<LongResultI, Long> implements LongResultI {

	/**
	 * @param ds
	 */
	public LongResult(NodeService ds) {
		super(ds);
	}

}
