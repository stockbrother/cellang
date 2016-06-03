/**
 *  Nov 28, 2012
 */
package org.cellang.elastictable.support;

import org.cellang.elastictable.TableService;
import org.cellang.elastictable.result.BooleanResultI;

/**
 * @author wuzhen
 * 
 */
public class BooleanResult extends ResultSupport<BooleanResultI, Boolean> implements BooleanResultI {

	/**
	 * @param ds
	 */
	public BooleanResult(TableService ds) {
		super(ds);
	}

}
