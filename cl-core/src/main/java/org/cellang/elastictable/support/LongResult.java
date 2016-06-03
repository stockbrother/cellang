/**
 *  Nov 28, 2012
 */
package org.cellang.elastictable.support;

import org.cellang.elastictable.TableService;
import org.cellang.elastictable.result.LongResultI;

/**
 * @author wuzhen
 * 
 */
public class LongResult extends ResultSupport<LongResultI, Long> implements LongResultI {

	/**
	 * @param ds
	 */
	public LongResult(TableService ds) {
		super(ds);
	}

}
