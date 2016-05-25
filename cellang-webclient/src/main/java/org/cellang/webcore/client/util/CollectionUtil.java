/**
 * Jul 1, 2012
 */
package org.cellang.webcore.client.util;

import java.util.Collection;

import org.cellang.webcore.client.WebException;

/**
 * @author wu
 * 
 */
public class CollectionUtil {

	public static <T> T single(Collection<T> cs, boolean force) {
		if (cs.isEmpty()) {
			if (force) {
				throw new WebException("force:");
			} else {
				return null;
			}
		} else if (cs.size() > 1) {
			throw new WebException("duplicated:" + cs);
		} else {
			return cs.iterator().next();
		}
	}
}
